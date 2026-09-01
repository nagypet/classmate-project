package hu.perit.classmate.rest.api;

import hu.perit.classmate.rest.model.UserProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

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
}
