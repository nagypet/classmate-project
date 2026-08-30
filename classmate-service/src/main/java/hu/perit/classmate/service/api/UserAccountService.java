package hu.perit.classmate.service.api;

import hu.perit.classmate.model.CreateUserRequest;
import hu.perit.classmate.model.UserProfile;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;

public interface UserAccountService
{
    UserProfile getMyProfile(AuthenticatedUser authenticatedUser);

    UserProfile createUserAccount(AuthenticatedUser authenticatedUser, CreateUserRequest request);

    AuthenticatedUser findUserAccount(AuthenticatedUser authenticatedUser);
}
