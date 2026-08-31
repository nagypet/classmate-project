/*
 * Copyright 2020-2024 the original author or authors.
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

package hu.perit.classmate.rest.api.frontend;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.ngface.titlebar.titlebar.TitlebarComponentView;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/forms/titlebar")
public class TitlebarRestController
{
    @GetMapping
    @LoggedRestMethod(eventId = Constants.TITLEBAR_CONTROLLER_GET_FORM)
    public Form getForm(Long ignored)
    {
        return new TitlebarComponentView().getForm();
    }
}
