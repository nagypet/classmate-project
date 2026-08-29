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

package hu.perit.classmate.rest.controller;

import hu.perit.spvitamin.spring.auth.AuthorizationToken;
import hu.perit.spvitamin.spring.rest.api.AuthApi;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import hu.perit.spvitamin.spring.security.CredentialType;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import hu.perit.spvitamin.spring.security.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peter Nagy
 */

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi
{
    private final JwtTokenProvider tokenProvider;
    private final AuthorizationService authorizationService;


    @Override
    @LoggedRestMethod(eventId = 1)
    public ResponseEntity<AuthorizationToken> authenticateUsingGET(String traceId)
    {
        AuthenticatedUser authenticatedUser = this.authorizationService.getAuthenticatedUser();
        if (authenticatedUser.getCredentialType() == CredentialType.BASIC)
        {
            log.info("{} logged in", authenticatedUser.getUsername());
        }
        return tokenProvider.generateToken(authenticatedUser);
    }
}
