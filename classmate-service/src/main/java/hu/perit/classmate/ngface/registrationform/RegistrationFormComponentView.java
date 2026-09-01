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

package hu.perit.classmate.ngface.registrationform;

import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.input.DateInput;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.ngface.core.widget.input.TextInput;
import hu.perit.ngface.core.widget.input.validator.Email;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.input.validator.Size;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class RegistrationFormComponentView implements ComponentView
{
    private final RegistrationFormComponentDTO data;


    @Override
    public Form getForm()
    {
        return new Form("registration-form")
                .addWidget(new TextInput(RegistrationFormComponentDTO.DISPLAY_NAME_ID)
                        .value(this.data.getDisplayName())
                        .label("Név")
                        .placeholder("Hogyan szólíthatunk?")
                        .addValidator(new Required("A név megadása kötelező!"))
                        .addValidator(new Size("Maximális méret 255 karakter!").max(255))
                        .enabled(StringUtils.isBlank(this.data.getDisplayName()))
                )
                .addWidget(new TextInput(RegistrationFormComponentDTO.EMAIL_ID)
                        .value(this.data.getEmail())
                        .label("Email")
                        .addValidator(new Required("Az email megadása kötelező!"))
                        .addValidator(new Email("Hibás email formátum!"))
                        .enabled(StringUtils.isBlank(this.data.getEmail()))
                )
                .addWidget(new DateInput(RegistrationFormComponentDTO.BIRTHDATE_ID)
                        .value(this.data.getBirthdate())
                        .label("Születési dátum")
                        .addValidator(new Required("A születési dátum megadása kötelező!"))
                )
                .addWidget(new Select(RegistrationFormComponentDTO.GENDER_ID)
                        .label("Nem")
                        .data(this.data.getGender())
                        .addValidator(new Required("Válassz egyet a listából!"))
                )
                .addWidget(new Button("button-register").label("Regisztráció").style(Button.Style.PRIMARY))
                ;
    }
}
