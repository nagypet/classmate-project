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
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {timeout, catchError} from 'rxjs/operators';

@Injectable()
export class TimeoutInterceptor implements HttpInterceptor
{
  private readonly TIMEOUT_MS = 15_000;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    return next.handle(req).pipe(
      timeout(this.TIMEOUT_MS), // Timeout beállítása
      catchError((error) =>
      {
        if (error.name === 'TimeoutError')
        {
          // Kérés megszakadt időtúllépés miatt
          console.error(`Request timed out after ${this.TIMEOUT_MS / 1000} seconds`, req.url);
          return throwError(() => new HttpErrorResponse({
            error: `Request timed out after ${this.TIMEOUT_MS / 1000} seconds`,
            status: 408, // HTTP 408: Request Timeout
            statusText: 'Request Timeout',
            url: req.url,
          }));
        }
        // Egyéb hibák kezelése
        return throwError(() => error);
      })
    );
  }
}
