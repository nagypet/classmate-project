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

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ResponsiveClassDirective} from '../../../../../../ngface/src/lib/directives/responsive-class-directive';
import {FormBaseComponent} from '../../../../../../ngface/src/lib/form/form-base.component';
import {ChangeService} from '../../../core/services/change.service';
import {AuthService} from "../../../../../../ngface/src/lib/services/auth/auth.service";
import {Subscription} from "rxjs";
import {UserAccountService} from "../../../core/services/useraccount.service";
import {ClassMateService} from "../../../classmate-service-models";


@Component({
  selector: 'app-public',
  standalone: true,
  imports: [
    ResponsiveClassDirective,
  ],
  templateUrl: './public.component.html',
  styleUrl: './public.component.scss',
})
export class PublicComponent extends FormBaseComponent implements OnInit, OnDestroy
{
  userName? = '';
  userEmail? = '';
  userRoles?: ClassMateService.Role[];

  private subscriptions = new Array<Subscription | undefined>();

  constructor(
    private changeService: ChangeService,
    private authService: AuthService,
    private userAccountService: UserAccountService,
  )
  {
    super();

    this.subscriptions.push(this.authService.token$.subscribe(authorizationToken =>
    {
      if (authorizationToken)
      {
        this.userAccountService.getMyProfile().subscribe({
          next: (profile: ClassMateService.UserProfile) =>
          {
            this.userName = profile.displayName;
            this.userEmail = profile.email;
            this.userRoles = profile.roles;
          }
        });
      }
      else
      {
        this.userName = undefined;
        this.userEmail = undefined;
        this.userRoles = undefined;
      }
    }));
  }

  ngOnInit(): void
  {
    this.changeService.triggerReload(this);
  }


  ngOnDestroy(): void
  {
    this.subscriptions.forEach(subscription => subscription?.unsubscribe());
  }
}
