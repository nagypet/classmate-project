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

package hu.perit.classmate.rest.controller.frontend;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.ngface.registrationform.RegistrationFormComponentController;
import hu.perit.classmate.ngface.registrationform.RegistrationFormComponentDTO;
import hu.perit.classmate.ngface.registrationform.RegistrationFormComponentView;
import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.rest.NgfaceFormRestController;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/forms/registration-form")
public class RegistrationFormRestController extends NgfaceFormRestController<RegistrationFormComponentController, RegistrationFormComponentDTO, RegistrationFormComponentView, Long>
{
    public RegistrationFormRestController(RegistrationFormComponentController componentController)
    {
        super(componentController);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.REGISTRATION_FORM_CONTROLLER_GET_FORM)
    public Form getForm(Long ignored)
    {
        return super.getForm(ignored);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.REGISTRATION_FORM_CONTROLLER_SUBMIT_FORM)
    public void submitForm(SubmitFormData submitFormData)
    {
        super.submitForm(submitFormData);
    }


    @Override
    protected RegistrationFormComponentView supplyView(RegistrationFormComponentDTO data)
    {
        return new RegistrationFormComponentView(data);
    }


    @Override
    protected RegistrationFormComponentDTO supplyDTO()
    {
        return new RegistrationFormComponentDTO();
    }
}
