/*
 * Copyright 2020-2023 the original author or authors.
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

import hu.perit.classmate.db.classmate.table.UserAccountEntity;
import hu.perit.classmate.service.api.entity.UserAccountEntityService;
import hu.perit.classmate.util.ClassMatePasswordEncoder;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import hu.perit.spvitamin.spring.security.CredentialType;
import hu.perit.spvitamin.spring.security.authprovider.AbstractSpvitaminBasicAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DbAuthenticationProvider extends AbstractSpvitaminBasicAuthenticationProvider
{
    private final UserAccountEntityService userAccountEntityService;


    @Override
    public AuthenticatedUser loadUserByUsernameAndPassword(String username, String password) throws AuthenticationException
    {
        try
        {
            UserAccountEntity userAccountEntity = this.userAccountEntityService.findByAuthProviderAndUserName("classmate", StringUtils.toRootLowerCase(username)).orElse(null);
            if (userAccountEntity == null)
            {
                throw new UsernameNotFoundException(username);
            }

            ClassMatePasswordEncoder passwordEncoder = new ClassMatePasswordEncoder();
            if (StringUtils.isBlank(userAccountEntity.getEncryptedPassword())
                    || !passwordEncoder.matches(password, userAccountEntity.getEncryptedPassword()))
            {
                throw new BadCredentialsException("Invalid user credentials!");
            }

            return AuthenticatedUser.builder()
                    .anonymous(false)
                    .username(userAccountEntity.getUserName())
                    .displayName(userAccountEntity.getDisplayName())
                    .userId(userAccountEntity.getId().toString())
                    .authorities(userAccountEntity.getRoles().stream().map(i -> new SimpleGrantedAuthority(i.name())).toList())
                    .source("classmate")
                    .credentialType(CredentialType.BASIC)
                    .build();
        }
        catch (RuntimeException ex)
        {
            log.debug("{}: {}", DbAuthenticationProvider.class.getSimpleName(), ex.getMessage());
            throw new BadCredentialsException("Authentication failed!", ex);
        }
    }
}
