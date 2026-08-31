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

package hu.perit.classmate.ngface.titlebar.titlebar;

import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Action;
import hu.perit.ngface.core.widget.titlebar.Titlebar;
import hu.perit.spvitamin.spring.config.SpringContext;
import hu.perit.spvitamin.spring.manifest.ManifestReader;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class TitlebarComponentView implements ComponentView
{

    public static final String TITLEBAR = "titlebar";
    public static final String MEBIL = "ClassMate";
    public static final String VERSION_100 = "1.0.0";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";


    @Override
    public Form getForm()
    {
        Environment environment = SpringContext.getBean(Environment.class);
        String applicationName = environment.getProperty("spring.application.name");
        Properties manifest = ManifestReader.getManifestAttributes(applicationName);
        String version = manifest.getProperty("Implementation-Version", "");
        String buildTime = manifest.getProperty("Build-Time", "");
        String build;
        if (StringUtils.isNoneBlank(buildTime))
        {
            build = String.format("%s", buildTime);
        }
        else
        {
            build = "Started from IDE, no build info is available!";
        }

        AuthorizationService authorizationService = SpringContext.getBean(AuthorizationService.class);
        AuthenticatedUser authenticatedUser = authorizationService.getAuthenticatedUser();
        return getPublicTitlebar(version, build, authenticatedUser.isAnonymous());
    }


    private Form getPublicTitlebar(String version, String build, Boolean anonymous)
    {
        List<Action> actions = new ArrayList<>();
        if (BooleanUtils.isTrue(anonymous))
        {
            actions.add(new Action(LOGIN).icon(LOGIN).label("Login"));
        }
        else
        {
            actions.add(new Action(LOGOUT).icon(LOGOUT).label("Logout"));
        }

        return new Form(TITLEBAR)
                .addWidget(new Titlebar(TITLEBAR)
                        .appTitle(MEBIL)
                        .version(StringUtils.isNotBlank(version) ? version : VERSION_100)
                        .buildTime(build)
                        .actions(actions)
                );
    }
}
