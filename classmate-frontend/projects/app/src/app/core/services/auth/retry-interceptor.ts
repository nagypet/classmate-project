import {HttpContextToken, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {catchError, switchMap, tap} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {routes} from '../../../ui/constants/constants';
import {AuthService} from '../../../../../../ngface/src/lib/services/auth/auth.service';

const RETRIED = new HttpContextToken<boolean>(() => false);

@Injectable()
export class RetryInterceptor implements HttpInterceptor
{
  constructor(
    private authService: AuthService,
    private router: Router
  )
  {
  }


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    if (!this.authService.isConfigured || this.authService.ignoreInTokenInterceptor(request.url))
    {
      // Skip auth endpoints
      return next.handle(request);
    }

    const alreadyRetried = request.context.get(RETRIED);

    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) =>
      {
        if (err.status !== 401)
        {
          return throwError(() => err);
        }

        if (!alreadyRetried)
        {
          // First 401: refreshing token, then retry once
          console.log('Refreshing token');
          return this.retryOnceWithRefresh(request, next);
        }

        // Second 401: anonym login + navigate to /public
        console.log('Wrapping up');
        return this.anonymizeAndRedirect(err);
      })
    );
  }


  private retryOnceWithRefresh(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    return this.authService.refreshToken().pipe(
      switchMap(() =>
      {
        const retriedReq = request.clone({
          context: request.context.set(RETRIED, true)
        });
        console.log(`Retrying ${retriedReq.url}`);
        return next.handle(retriedReq);
      }),
      // If the refreshToken fails: anonym login + navigate to /public
      catchError(refreshErr => this.anonymizeAndRedirect(refreshErr))
    );
  }


  private anonymizeAndRedirect<T>(error: any): Observable<never>
  {
    return this.authService.logout().pipe(
      tap(() => this.router.navigateByUrl(routes.public)),
      switchMap(() => throwError(() => error)),
      catchError(() =>
      {
        // If the anonym login fails
        this.router.navigateByUrl(routes.public);
        return throwError(() => error);
      })
    );
  }
}
