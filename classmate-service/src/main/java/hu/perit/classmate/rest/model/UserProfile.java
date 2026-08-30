package hu.perit.classmate.rest.model;

import hu.perit.classmate.config.Gender;
import hu.perit.classmate.config.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserProfile
{
    private String userId;
    private String displayName;
    private String email;
    private Gender gender;
    private LocalDate birthdate;
    private Set<Role> roles;
}
