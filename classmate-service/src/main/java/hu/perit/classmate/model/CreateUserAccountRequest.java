package hu.perit.classmate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserAccountRequest extends RegisterAuthenticatedUserRequest
{
    private String username;
    private String password;
}
