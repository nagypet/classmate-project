package hu.perit.classmate.service.api;

import hu.perit.classmate.rest.model.CreateUserRequest;
import hu.perit.classmate.rest.model.UserProfile;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;

public interface UserAccountService
{
    UserProfile getMyProfile(AuthenticatedUser authenticatedUser);

    UserProfile createUserAccount(AuthenticatedUser authenticatedUser, CreateUserRequest request);

    AuthenticatedUser findUserAccount(AuthenticatedUser authenticatedUser);
}
