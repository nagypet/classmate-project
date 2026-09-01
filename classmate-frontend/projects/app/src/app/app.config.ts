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

import {APP_INITIALIZER, ApplicationConfig, LOCALE_ID, provideZoneChangeDetection} from '@angular/core';
import {provideRouter, withHashLocation} from '@angular/router';

import {APP_ROUTES} from './app.routes';
import {provideAnimations} from '@angular/platform-browser/animations';
import {provideToastr} from 'ngx-toastr';
import {AuthGuard} from './core/services/auth/auth.guard';
import {MAT_SNACK_BAR_DEFAULT_OPTIONS} from '@angular/material/snack-bar';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {MAT_DATE_LOCALE, provideNativeDateAdapter} from '@angular/material/core';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {LoadingInterceptor} from './core/interceptors/loading-interceptor';
import {TimeoutInterceptor} from './core/interceptors/timeout-interceptor';
import {environment} from '../environments/environment';
import {RetryInterceptor} from './core/services/auth/retry-interceptor';
import {ErrorInterceptor} from "../../../ngface/src/lib/interceptors/error-interceptor.service";
import {AuthService, configureAuthService} from "../../../ngface/src/lib/services/auth/auth.service";


function initAuth(auth: AuthService)
{
  return () => configureAuthService(auth, {baseUrl: environment.baseURL, autoRenew: false});
}


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(APP_ROUTES, withHashLocation()),
    provideAnimations(),
    provideNativeDateAdapter(),
    provideToastr(),
    AuthGuard,
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {horizontalPosition: 'end', verticalPosition: 'bottom'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    {provide: MAT_DATE_LOCALE, useValue: 'hu'},
    {provide: LOCALE_ID, useValue: 'de-DE'},
    {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: RetryInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: TimeoutInterceptor, multi: true,},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true,},
    provideHttpClient(withInterceptorsFromDi()),
    {provide: APP_INITIALIZER, useFactory: initAuth, deps: [AuthService], multi: true}
  ]
};
