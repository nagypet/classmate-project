package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.config.Gender;
import hu.perit.classmate.db.classmate.converter.GenderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = UserEntity.TABLE_NAME, schema = Constants.SCHEMA, indexes = {
        @Index(name = UserEntity.IX_01, columnList = UserEntity.COL_OAUTH_PROVIDER + "," + UserEntity.COL_OAUTH_SUBJECT, unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public class UserEntity
{
    public static final String TABLE_NAME = "user";

    public static final String IX_01 = "ix_user_01";

    public static final String COL_ID = "id";
    public static final String COL_OAUTH_PROVIDER = "oauth_provider";
    public static final String COL_OAUTH_SUBJECT = "oauth_subject";
    public static final String COL_DISPLAY_NAME = "display_name";
    public static final String COL_EMAIL = "email";
    public static final String COL_GENDER = "gender";
    public static final String COL_BIRTHDATE = "birthdate";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_UPDATED_BY = "updated_by";

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = COL_ID, nullable = false)
    private UUID id;

    @Size(max = 32)
    @Column(name = COL_OAUTH_PROVIDER)
    private String oauthProvider;

    @Size(max = 255)
    @Column(name = COL_OAUTH_SUBJECT)
    private String oauthSubject;

    @Size(max = 255)
    @Column(name = COL_DISPLAY_NAME)
    private String displayName;

    @Column(name = COL_GENDER)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = COL_BIRTHDATE)
    private LocalDate birthdate;

    @Size(max = 255)
    @Column(name = COL_EMAIL)
    private String email;

    @CreatedBy
    @Size(max = 150)
    @Column(name = COL_CREATED_BY, nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = COL_CREATED_AT, nullable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Size(max = 150)
    @Column(name = COL_UPDATED_BY)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = COL_UPDATED_AT)
    private Instant updatedAt;

    public OffsetDateTime getCreatedAt()
    {
        return createdAt == null ? null : OffsetDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }


    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt == null ? null : OffsetDateTime.ofInstant(updatedAt, ZoneId.systemDefault());
    }
}
