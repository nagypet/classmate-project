package hu.perit.classmate.model;

import hu.perit.classmate.config.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequest
{
    private String displayName;
    private String email;
    private Gender gender;
    private LocalDate birthdate;
}
