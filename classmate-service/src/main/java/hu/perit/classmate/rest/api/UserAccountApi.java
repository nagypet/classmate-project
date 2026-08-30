package hu.perit.classmate.rest.api;

import hu.perit.classmate.rest.model.CreateUserRequest;
import hu.perit.classmate.rest.model.UserProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserAccountApi
{
    String BASE_URL = "/api/user-accounts";


    //------------------------------------------------------------------------------------------------------------------
    // getMyProfile
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getMyProfile() - Returns the profile of the currently logged in user",
            security = {@SecurityRequirement(name = "bearer")}
    )
    @StandardApiResponses200_400_401_403_500
    //------------------------------------------------------------------------------------------------------------------
    UserProfile getMyProfile();
    //------------------------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------
    // createUserAccount
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(value = BASE_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "createUserAccount() - Creates an account for the currently logged in user",
            security = {@SecurityRequirement(name = "bearer")}
    )
    @StandardApiResponses200_400_401_403_500
    //------------------------------------------------------------------------------------------------------------------
    UserProfile createUserAccount(@RequestBody @Valid CreateUserRequest request);
    //------------------------------------------------------------------------------------------------------------------
}
