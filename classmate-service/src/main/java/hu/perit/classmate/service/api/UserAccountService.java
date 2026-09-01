package hu.perit.classmate.service.api;

import hu.perit.classmate.model.CreateUserAccountRequest;
import hu.perit.classmate.model.RegisterAuthenticatedUserRequest;
import hu.perit.classmate.rest.model.UserProfile;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;

public interface UserAccountService
{
    UserProfile getMyProfile(AuthenticatedUser authenticatedUser);

    UserProfile registerAuthenticatedUser(AuthenticatedUser authenticatedUser, RegisterAuthenticatedUserRequest request);

    UserProfile createUserAccount(CreateUserAccountRequest request);

    AuthenticatedUser findUserAccount(AuthenticatedUser authenticatedUser);
}
