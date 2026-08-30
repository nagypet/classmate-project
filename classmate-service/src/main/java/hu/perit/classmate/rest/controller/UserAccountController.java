package hu.perit.classmate.rest.controller;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.model.CreateUserRequest;
import hu.perit.classmate.model.UserProfile;
import hu.perit.classmate.rest.api.UserAccountApi;
import hu.perit.classmate.service.api.UserAccountService;
import hu.perit.spvitamin.core.exception.CheckedExceptionConverter;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountController implements UserAccountApi
{
    private final AuthorizationService authorizationService;
    private final UserAccountService userAccountService;


    @Override
    @LoggedRestMethod(eventId = Constants.USER_ACCOUNT_CONTROLLER_GET_MY_PROFILE)
    public UserProfile getMyProfile()
    {
        AuthenticatedUser authenticatedUser = this.authorizationService.getAuthenticatedUser();
        return this.userAccountService.getMyProfile(authenticatedUser);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.USER_ACCOUNT_CONTROLLER_REGISTER_USER)
    public UserProfile createUserAccount(CreateUserRequest request)
    {
        AuthenticatedUser authenticatedUser = this.authorizationService.getAuthenticatedUser();
        return CheckedExceptionConverter.invoke(() -> this.userAccountService.createUserAccount(authenticatedUser, request));
    }
}
