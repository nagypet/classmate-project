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

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBaseComponent} from '../../../../../../ngface/src/lib/form/form-base.component';
import {NgfaceTextInputComponent} from '../../../../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component';
import {ResponsiveClassDirective} from '../../../../../../ngface/src/lib/directives/responsive-class-directive';
import {Ngface} from '../../../../../../ngface/src/lib/ngface-models';
import {NgfaceWidgetFactory} from '../../../../../../ngface/src/lib/widgets/ngface-widget-factory';
import {NgfaceFormComponent} from '../../../../../../ngface/src/lib/form/ngface-form/ngface-form.component';
import {NgfaceButtonComponent} from '../../../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component';
import {environment} from '../../../../environments/environment';
import {MatButton} from '@angular/material/button';
import {SpvitaminSecurity} from '../../../core/model/spvitamin-security-models';
import {AuthService} from '../../../../../../ngface/src/lib/services/auth/auth.service';
import {AuthenticationRepositoryService} from '../../../../../../ngface/src/lib/services/auth/authentication-repository.service';
import {routes} from "../../constants/constants";
import {UserAccountService} from "../../../core/services/useraccount.service";
import {ClassMateService} from "../../../classmate-service-models";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [
    NgfaceFormComponent,
    NgfaceTextInputComponent,
    NgfaceButtonComponent,
    ResponsiveClassDirective,
    MatButton,
  ],
  standalone: true
})
export class LoginComponent extends FormBaseComponent implements OnInit
{
  protected errorText?: string;
  protected selectedAuthenticationType?: SpvitaminSecurity.AuthenticationType;

  private returnUrl = '';
  protected authenticationTypes: Array<SpvitaminSecurity.AuthenticationType> = [];


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private authenticationRepositoryService: AuthenticationRepositoryService,
    private userAccountService: UserAccountService,
  )
  {
    super();

    // Initializing ngface widgets
    let form = {widgets: {}} as Ngface.Form;
    form.widgets['username'] = this.createTextInputWidget('username', 'Username');
    form.widgets['password'] = this.createTextInputWidget('password', 'Password', true);
    form.widgets['button-login'] = NgfaceWidgetFactory.createButton({id: 'button-login', label: 'Login'});
    form.widgets['button-back'] = NgfaceWidgetFactory.createButton({id: 'button-back', label: 'Back', style: 'NONE'});
    form.widgets['button-cancel'] = NgfaceWidgetFactory.createButton({id: 'button-cancel', label: 'Cancel', style: 'NONE'});
    this.formData = form;
  }


  private createTextInputWidget(id: string, label: string, password = false): Ngface.TextInput
  {
    return NgfaceWidgetFactory.createTextInput({id, label, validators: [{type: 'Required', message: `Enter ${label}`}], password});
  }


  ngOnInit(): void
  {
    this.authenticationRepositoryService.getAuthenticationRepository().subscribe((result: SpvitaminSecurity.AuthenticationRepository) =>
    {
      this.authenticationTypes = result.authenticationTypes;
      if (result.authenticationTypes && result.authenticationTypes.length == 1)
      {
        this.selectedAuthenticationType = result.authenticationTypes[0];
      }
    });
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }


  onLogin()
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
    }
    else
    {
      const submitData = this.getSubmitData();
      const userName = (submitData['username'] as Ngface.TextInput.Data).value!;
      const password = (submitData['password'] as Ngface.TextInput.Data).value!;

      this.authService.login(userName, password).subscribe({
        next: () =>
        {
          // Is user already registered?
          this.userAccountService.getMyProfile().subscribe({
            next: (profile: ClassMateService.UserProfile) =>
            {
              console.log(profile);
              if (profile.registrationNeeded)
              {
                this.router.navigate(['register']);
              }
              else
              {
                this.router.navigate([routes.public]);
              }
            }
          });
        },
        error: () =>
        {
          this.errorText = 'Invalid username or password!';
        }
      });
    }
  }


  onBack()
  {
    this.selectedAuthenticationType = undefined;
    this.router.navigateByUrl(this.returnUrl);
  }


  onKeyPress(event: KeyboardEvent)
  {
    //console.log('onKeyPress ' + event.key);
    this.errorText = undefined;
    if (event.key === 'Enter')
    {
      this.onLogin();
    }
    else if (event.key === 'Escape')
    {
      this.onBack();
    }
  }


  getLogoUrl(): string
  {
    return `themes/${environment.theme}/company_logo.png`;
  }


  onAuthenticationSelect(type: SpvitaminSecurity.AuthenticationType)
  {
    console.log(`Selected authentication type: ${type.label}`);
    this.selectedAuthenticationType = type;

    if (type.type === 'oauth2')
    {
      window.location.href = `${environment.baseURL}/api/spvitamin/oauth2/authorization?provider=${type.provider}`;
    }
  }


  onCancel()
  {
    console.log('onCancel');
    this.router.navigate(['public']);
  }
}
