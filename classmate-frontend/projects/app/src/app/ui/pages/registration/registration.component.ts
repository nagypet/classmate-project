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
import {Router} from '@angular/router';
import {FormBaseComponent} from '../../../../../../ngface/src/lib/form/form-base.component';
import {ResponsiveClassDirective} from '../../../../../../ngface/src/lib/directives/responsive-class-directive';
import {UserAccountService} from '../../../core/services/useraccount.service';
import {RegistrationFormService} from "../../../core/services/registration-form.service";
import {NgfaceFormComponent} from "../../../../../../ngface/src/lib/form/ngface-form/ngface-form.component";
import {
  NgfaceTextInputComponent
} from "../../../../../../ngface/src/lib/widgets/ngface-text-input/ngface-text-input.component";
import {NgfaceSelectComponent} from "../../../../../../ngface/src/lib/widgets/ngface-select/ngface-select.component";
import {
  NgfaceDateInputComponent
} from "../../../../../../ngface/src/lib/widgets/ngface-date-input/ngface-date-input.component";
import {NgfaceButtonComponent} from "../../../../../../ngface/src/lib/widgets/ngface-button/ngface-button.component";
import {routes} from "../../constants/constants";
import {AuthService} from "../../../../../../ngface/src/lib/services/auth/auth.service";
import {Ngface} from "../../../../../../ngface/src/lib/ngface-models";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
  standalone: true,
  imports: [
    NgfaceFormComponent,
    NgfaceTextInputComponent,
    NgfaceSelectComponent,
    NgfaceDateInputComponent,
    NgfaceButtonComponent,
    ResponsiveClassDirective,
  ]
})
export class RegistrationComponent extends FormBaseComponent implements OnInit
{
  protected errorText?: string;

  constructor(
    private router: Router,
    private userAccountService: UserAccountService,
    private formService: RegistrationFormService,
    private authService: AuthService
  )
  {
    super();
  }


  ngOnInit(): void
  {
    this.formService.getForm().subscribe(form =>
    {
      console.log(form);
      this.formData = form;
    });
  }


  onRegister(): void
  {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid)
    {
      console.warn('Data is invalid!');
      return;
    }

    const submitData = this.getSubmitData();
    this.formService.submitForm({id: '', widgetDataMap: submitData}).subscribe({
      next: () =>
      {
        console.log('sumbitted');
        if (this.isWidgetAvailable('username') && this.isWidgetAvailable('password'))
        {
          const username = (submitData['username'] as Ngface.TextInput.Data).value!;
          const password = (submitData['password'] as Ngface.TextInput.Data).value!;
          this.authService.login(username, password).subscribe(() => this.router.navigate([routes.public]));
        }
        else
        {
          this.router.navigate([routes.public]);
        }
      },
      error: (error) =>
      {
        this.errorText = 'A regisztráció sikertelen. Próbáld újra!';
        console.error(error);
      }
    });
  }
}
