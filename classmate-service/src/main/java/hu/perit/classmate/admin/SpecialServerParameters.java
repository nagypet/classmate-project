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

package hu.perit.classmate.admin;

import hu.perit.spvitamin.spring.admin.serverparameter.ServerParameter;
import hu.perit.spvitamin.spring.admin.serverparameter.ServerParameterList;
import hu.perit.spvitamin.spring.admin.serverparameter.ServerParameterListImpl;
import hu.perit.spvitamin.spring.config.AdminProperties;
import hu.perit.spvitamin.spring.config.ServerProperties;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Generated // To disable counting in unit test coverage
class SpecialServerParameters
{
    public static final String LINKS = "1-Links";

    private final AdminProperties adminProperties;
    private final ServerProperties serverProperties;

    @Bean(name = "SpecialServerParameters")
    public ServerParameterList getParameterList()
    {
        ServerParameterList params = new ServerParameterListImpl();

        // add sepcial server parameters
        params.add(LINKS, new ServerParameter("(4) GUI", String.format("%s%s/%s", serverProperties.getServiceUrl(), adminProperties.getDefaultSiteUrl(), adminProperties.getDefaultSiteRootFileName()), true));

        return params;
    }
}
