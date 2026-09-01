package hu.perit.classmate.service.api;

import hu.perit.classmate.rest.model.CreateUserAccountRequest;
import hu.perit.classmate.rest.model.UserProfile;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;

public interface UserAccountService
{
    UserProfile getMyProfile(AuthenticatedUser authenticatedUser);

    UserProfile createUserAccount(AuthenticatedUser authenticatedUser, CreateUserAccountRequest request);

    AuthenticatedUser findUserAccount(AuthenticatedUser authenticatedUser);
}
