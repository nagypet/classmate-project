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

package hu.perit.classmate.auth;

import hu.perit.classmate.rest.api.UserAccountApi;
import hu.perit.spvitamin.spring.security.auth.SimpleHttpSecurityBuilder;
import hu.perit.spvitamin.spring.security.authprovider.localuserprovider.EnableLocalUserAuthProvider;
import hu.perit.spvitamin.spring.security.oauth2.CustomOAuth2SuccessHandler;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * #know-how:simple-httpsecurity-builder
 *
 * @author Peter Nagy
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableLocalUserAuthProvider
@EnableMethodSecurity(securedEnabled = true)
@Generated // To disable counting in unit test coverage
public class WebSecurityConfig
{
    @Bean
    @Order(1)
    public SecurityFilterChain configureOAuth2Login(HttpSecurity http, CustomOAuth2SuccessHandler successHandler) throws Exception
    {
        // http://localhost:8410/oauth2/authorization/microsoft
        // http://localhost:8410/login/oauth2/code/microsoft
        // http://localhost:8410/api/spvitamin/oauth2/authorization?provider=microsoft

        SimpleHttpSecurityBuilder.newInstance(http)
                .scope(
                        "/login/**",
                        "/oauth2/authorization/*")
                .and()
                .oauth2Login(i -> i.successHandler(successHandler));

        return http.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain configureAuthenticateEndpoint(HttpSecurity http) throws Exception
    {
        SimpleHttpSecurityBuilder.newInstance(http)
                .configureAuthorizatonServer();

        return http.build();
    }


    @Bean
    @Order(3)
    public SecurityFilterChain configureTokenSecuredEndpoints(HttpSecurity http) throws Exception
    {
        SimpleHttpSecurityBuilder.newInstance(http)
                .scope(
                        UserAccountApi.BASE_URL + "/**",
                        "/h2/**",
                        "/frontend/forms/**",
                        "/frontend/sse/**",
                        "/app/browser/**"
                )
                // we do not use secure sessions here: each endpoint has to be authenticated again and again
                .ignorePersistedSecurity()
                .h2()
                .authorizeRequests(r -> r.requestMatchers("/frontend/forms/**").permitAll())
                .authorizeRequests(r -> r.requestMatchers("/frontend/sse/**").permitAll())
                .authorizeRequests(r -> r.requestMatchers("/app/browser/**").permitAll())
                .authorizeRequests(r -> r.anyRequest().authenticated())
                .jwtAuth();

        return http.build();
    }
}
