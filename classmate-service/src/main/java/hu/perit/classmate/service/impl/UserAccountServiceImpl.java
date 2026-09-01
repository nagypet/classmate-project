package hu.perit.classmate.service.impl;

import hu.perit.classmate.config.Gender;
import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.table.RoleEntity;
import hu.perit.classmate.db.classmate.table.UserAccountEntity;
import hu.perit.classmate.db.classmate.table.UserAccountXRoleEntity;
import hu.perit.classmate.rest.model.CreateUserAccountRequest;
import hu.perit.classmate.rest.model.UserProfile;
import hu.perit.classmate.service.api.UserAccountService;
import hu.perit.classmate.service.impl.entity.RoleEntityServiceImpl;
import hu.perit.classmate.service.impl.entity.UserAccountEntityServiceImpl;
import hu.perit.classmate.service.impl.entity.UserAccountXRoleEntityServiceImpl;
import hu.perit.spvitamin.spring.exception.CannotProcessException;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService
{
    private final UserAccountEntityServiceImpl userAccountEntityService;
    private final RoleEntityServiceImpl roleEntityService;
    private final UserAccountXRoleEntityServiceImpl userAccountXRoleEntityService;


    @Override
    public UserProfile getMyProfile(AuthenticatedUser authenticatedUser)
    {
        UserAccountEntity userAccountEntity = this.userAccountEntityService.findByAuthProviderAndUserName(authenticatedUser.getSource(), authenticatedUser.getUsername()).orElse(null);
        if (userAccountEntity != null)
        {
            return mapEntityToProfile(userAccountEntity);
        }

        if (authenticatedUser.isAnonymous())
        {
            throw new CannotProcessException("This kind of user has no profile!");
        }

        return UserProfile.builder()
                .registrationNeeded(true)
                .authProvider(authenticatedUser.getSource())
                .authSubject(authenticatedUser.getUsername())
                .displayName(authenticatedUser.getDisplayName())
                .email(authenticatedUser.getAdditionalClaim("email", String.class).orElse(null))
                .gender(Gender.fromName(authenticatedUser.getAdditionalClaim("gender", String.class).orElse(null)).orElse(null))
                .birthdate(authenticatedUser.getAdditionalClaim("birthdate", LocalDate.class).orElse(null))
                .build();
    }


    @Override
    @Transactional
    public UserProfile createUserAccount(AuthenticatedUser authenticatedUser, CreateUserAccountRequest request)
    {
        UserAccountEntity userAccountEntity = this.userAccountEntityService.findByAuthProviderAndUserName(authenticatedUser.getSource(), authenticatedUser.getUsername()).orElse(null);
        if (userAccountEntity != null)
        {
            return mapEntityToProfile(userAccountEntity);
        }

        if (authenticatedUser.isAnonymous())
        {
            throw new CannotProcessException("This kind of user cannot create user account!");
        }

        // UserAccountEntity
        UserAccountEntity entity = new UserAccountEntity();
        entity.setAuthProvider(authenticatedUser.getSource());
        entity.setUserName(authenticatedUser.getUsername());
        entity.setDisplayName(StringUtils.defaultIfBlank(request.getDisplayName(), authenticatedUser.getDisplayName()));
        entity.setGender(request.getGender() != null ? request.getGender() : Gender.fromName(authenticatedUser.getAdditionalClaim("gender", String.class).orElse(null)).orElse(null));
        entity.setBirthdate(request.getBirthdate() != null ? request.getBirthdate() : authenticatedUser.getAdditionalClaim("birthdate", LocalDate.class).orElse(null));
        entity.setEmail(StringUtils.defaultIfBlank(request.getEmail(), authenticatedUser.getAdditionalClaim("email", String.class).orElse(null)));
        UserAccountEntity savedUserAccountEntity = this.userAccountEntityService.save(entity);

        // UserAccountXRoleEntity
        RoleEntity roleEntity = this.roleEntityService.findByRole(Role.ROLE_STUDENT).orElse(null);
        if (roleEntity != null)
        {
            UserAccountXRoleEntity xRoleEntity = new UserAccountXRoleEntity();
            xRoleEntity.setRoleId(roleEntity.getId());
            xRoleEntity.setUserId(savedUserAccountEntity.getId());
            this.userAccountXRoleEntityService.save(xRoleEntity);
        }

        return mapEntityToProfile(savedUserAccountEntity);
    }


    private static UserProfile mapEntityToProfile(UserAccountEntity entity)
    {
        return UserProfile.builder()
                .registrationNeeded(false)
                .authProvider(entity.getAuthProvider())
                .authSubject(entity.getUserName())
                .userId(entity.getId().toString())
                .displayName(entity.getDisplayName())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .birthdate(entity.getBirthdate())
                .roles(entity.getRoles())
                .build();
    }


    @Override
    public AuthenticatedUser findUserAccount(AuthenticatedUser authenticatedUser)
    {
        UserAccountEntity userAccountEntity = this.userAccountEntityService.findByAuthProviderAndUserName(authenticatedUser.getSource(), authenticatedUser.getUsername()).orElse(null);
        if (userAccountEntity == null)
        {
            return authenticatedUser;
        }
        return AuthenticatedUser.builder()
                .username(userAccountEntity.getUserName())
                .userId(userAccountEntity.getId().toString())
                .displayName(userAccountEntity.getDisplayName())
                .authorities(userAccountEntity.getRoles().stream().map(i -> new SimpleGrantedAuthority(i.name())).collect(Collectors.toSet()))
                .anonymous(false)
                .source(userAccountEntity.getAuthProvider())
                .credentialType(authenticatedUser.getCredentialType())
                .build();
    }
}
