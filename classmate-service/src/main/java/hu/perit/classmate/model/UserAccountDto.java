package hu.perit.classmate.model;

import hu.perit.classmate.config.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserAccountDto
{
    private UUID id;
    private String oauthProvider;
    private String oauthSubject;
    private String displayName;
    private Gender gender;
    private LocalDate birthdate;
    private String email;
    private String createdBy;
    private OffsetDateTime createdAt;
    private String updatedBy;
    private OffsetDateTime updatedAt;
}
