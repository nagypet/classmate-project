package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.config.Gender;
import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.converter.GenderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = UserAccountEntity.TABLE_NAME, schema = Constants.SCHEMA, indexes = {
        @Index(name = UserAccountEntity.IX_01, columnList = UserAccountEntity.COL_AUTH_PROVIDER + "," + UserAccountEntity.COL_USER_NAME, unique = true),
        @Index(name = UserAccountEntity.IX_02, columnList = UserAccountEntity.COL_EMAIL, unique = true),
})
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public class UserAccountEntity
{
    public static final String TABLE_NAME = "user_account";

    public static final String IX_01 = "ix_user_account_01";
    public static final String IX_02 = "ix_user_account_02";

    public static final String COL_ID = "id";
    public static final String COL_AUTH_PROVIDER = "auth_provider";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_ENCRYPTED_PASSWORD = "encrypted_password";
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
    @Column(name = COL_AUTH_PROVIDER, nullable = false)
    private String authProvider;

    @Size(max = 255)
    @Column(name = COL_USER_NAME, nullable = false)
    private String userName;

    @Size(max = 200)
    @Column(name = COL_ENCRYPTED_PASSWORD, nullable = true)
    private String encryptedPassword;

    @Size(max = 255)
    @Column(name = COL_DISPLAY_NAME, nullable = false)
    private String displayName;

    @Column(name = COL_GENDER, nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = COL_BIRTHDATE, nullable = false)
    private LocalDate birthdate;

    @Size(max = 255)
    @Column(name = COL_EMAIL, nullable = false)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = UserAccountXRoleEntity.TABLE_NAME,
            schema = Constants.SCHEMA,
            joinColumns = @JoinColumn(name = UserAccountXRoleEntity.COL_USER_ID, referencedColumnName = COL_ID),
            inverseJoinColumns = @JoinColumn(name = UserAccountXRoleEntity.COL_ROLE_ID, referencedColumnName = RoleEntity.COL_ID)
    )
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<RoleEntity> roles = new HashSet<>();


    public OffsetDateTime getCreatedAt()
    {
        return createdAt == null ? null : OffsetDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }


    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt == null ? null : OffsetDateTime.ofInstant(updatedAt, ZoneId.systemDefault());
    }


    public Set<Role> getRoles()
    {
        return this.roles.stream().map(i -> i.getRole()).collect(Collectors.toSet());
    }
}
