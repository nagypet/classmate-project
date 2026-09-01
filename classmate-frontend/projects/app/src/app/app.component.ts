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

import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {DeviceTypeService} from '../../../ngface/src/lib/services/device-type.service';
import {LoginComponent} from './ui/pages/login/login.component';
import {LoadService} from '../../../ngface/src/lib/services/load.service';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {Subscription} from 'rxjs';
import {filter, switchMap} from 'rxjs/operators';
import {distinctUntilChanged} from 'rxjs';
import {TitlebarComponent} from './ui/page-elements/titlebar/titlebar.component';
import {environment} from '../environments/environment';
import {ResponsiveClassDirective} from '../../../ngface/src/lib/directives/responsive-class-directive';
import {routes} from './ui/constants/constants';
import {AuthService} from '../../../ngface/src/lib/services/auth/auth.service';
import {UserAccountService} from './core/services/useraccount.service';
import {ClassMateService} from './classmate-service-models';
import {RegistrationComponent} from "./ui/pages/registration/registration.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    LoginComponent,
    RouterOutlet,
    MatProgressSpinner,
    TitlebarComponent,
    ResponsiveClassDirective,
    RegistrationComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy
{
  title = 'fe-app';
  isLoading = false;

  private loadingSubscription: Subscription = new Subscription();
  private registrationCheckSubscription: Subscription = new Subscription();


  constructor(
    private loadService: LoadService,
    public deviceTypeService: DeviceTypeService,
    private router: Router,
    private authService: AuthService,
    private userAccountService: UserAccountService,
  )
  {
    this.loadingSubscription = this.loadService.loadingSubject.subscribe((loading: boolean) =>
    {
      setTimeout(() =>
      {
        this.isLoading = loading;
      });
    });

    this.registrationCheckSubscription = this.authService.token$.pipe(
      filter(token => !!token),
      distinctUntilChanged((a, b) => a?.jwt === b?.jwt),
      switchMap(() => this.userAccountService.getMyProfile()),
      filter((profile: ClassMateService.UserProfile) => profile.registrationNeeded),
    ).subscribe(() =>
    {
      this.router.navigate([routes.register]);
    });
  }


  ngOnInit(): void
  {
    this.onWindowResize();
    this.loadStylesheet(`themes/${environment.theme}/microdms-theme.css`);
  }


  ngOnDestroy(): void
  {
    this.loadingSubscription.unsubscribe();
    this.registrationCheckSubscription.unsubscribe();
  }


  @HostListener('window:resize', ['$event'])
  onWindowResize(): void
  {
    this.deviceTypeService.calculateDeviceType();
  }


  loadStylesheet(path: string): void
  {
    const linkEl = document.createElement('link');
    linkEl.rel = 'stylesheet';
    linkEl.href = path;
    document.head.appendChild(linkEl);
  }


  public isLoginUrl(): boolean
  {
    return this.router.url === '/' + routes.login;
  }

  public isRegisterUrl(): boolean
  {
    return this.router.url === '/' + routes.register;
  }
}
