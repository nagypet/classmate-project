/*
 * Copyright 2020-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {finalize, catchError} from 'rxjs/operators';
import {LoadService} from '../../../../../ngface/src/lib/services/load.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor
{

  constructor(private loadService: LoadService)
  {
  }

  loadPool = 0;

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    this.loadService.startLoading();
    this.loadPool++;
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) =>
      {
        // Check if the error is a network error (connection lost), timeout error, or server unreachable
        if ((error.error instanceof ProgressEvent && error.error.type === 'error') ||
          (error.status === 408) || // Request Timeout
          (error.status === 0)   // Server unreachable
        )
        {
          // Connection issue detected, hide loading indicator
          this.loadPool = 0; // Reset the load pool
          this.loadService.endLoading();
        }
        return throwError(() => error);
      }),
      finalize(() =>
      {
        this.loadPool--;
        if (this.loadPool == 0)
        {
          this.loadService.endLoading();
        }
      })
    );
  }
}
