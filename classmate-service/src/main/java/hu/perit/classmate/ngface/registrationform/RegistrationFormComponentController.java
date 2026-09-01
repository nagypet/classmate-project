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

import hu.perit.classmate.config.Gender;
import hu.perit.classmate.rest.model.CreateUserAccountRequest;
import hu.perit.classmate.rest.model.UserProfile;
import hu.perit.classmate.service.api.UserAccountService;
import hu.perit.ngface.core.controller.ComponentController;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationFormComponentController implements ComponentController<RegistrationFormComponentDTO, Long>
{
    private final AuthorizationService authorizationService;
    private final UserAccountService userAccountService;


    @Override
    public RegistrationFormComponentDTO getForm(Long id)
    {
        UserProfile myProfile = this.userAccountService.getMyProfile(this.authorizationService.getAuthenticatedUser());

        // The data
        RegistrationFormComponentDTO data = new RegistrationFormComponentDTO();

        data.setDisplayName(myProfile.getDisplayName());
        data.setEmail(myProfile.getEmail());
        data.setBirthdate(myProfile.getBirthdate());
        data.setGender(getGenderData(myProfile.getGender()));

        return data;
    }


    private static Select.Data getGenderData(Gender gender)
    {
        Select.Data data = new Select.Data();
        Arrays.stream(Gender.values()).forEach(i -> data.addOption(new Select.Option(i.name(), i.getLabel())));
        if (gender != null)
        {
            data.selected(gender.name());
        }
        return data;
    }


    @Override
    public void onFormSubmit(RegistrationFormComponentDTO data)
    {
        log.info("Form submitted: {}", data);

        CreateUserAccountRequest request = new CreateUserAccountRequest();
        request.setDisplayName(data.getDisplayName());
        request.setEmail(data.getEmail());
        request.setBirthdate(data.getBirthdate());
        request.setGender(Gender.fromName(data.getGender().getSelected()).orElse(null));
        this.userAccountService.createUserAccount(this.authorizationService.getAuthenticatedUser(), request);
    }
}
