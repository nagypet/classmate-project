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

import hu.perit.ngface.core.data.ComponentDTO;
import hu.perit.ngface.core.data.DTOValue;
import hu.perit.ngface.core.widget.input.Select;
import lombok.Data;

import java.time.LocalDate;

/**
 * This is the actual DTO object which have to be shown. This data object will be enriched with view information so that
 * the frontend receives all necessary information to render the widget.
 *
 * @author Peter Nagy
 */
@Data
public class RegistrationFormComponentDTO extends ComponentDTO
{
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String DISPLAY_NAME_ID = "display-name";
    public static final String EMAIL_ID = "email";
    public static final String BIRTHDATE_ID = "birthdate";
    public static final String GENDER_ID = "gender";

    private boolean isNewUser;

    @DTOValue(id = USER_NAME)
    private String username;

    @DTOValue(id = PASSWORD)
    private String password;

    @DTOValue(id = DISPLAY_NAME_ID)
    private String displayName;

    @DTOValue(id = EMAIL_ID)
    private String email;

    @DTOValue(id = BIRTHDATE_ID)
    private LocalDate birthdate;

    @DTOValue(id = GENDER_ID)
    private Select.Data gender;
}
