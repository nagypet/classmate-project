package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = UserXRoleEntity.TABLE_NAME, schema = Constants.SCHEMA, indexes = {
        @Index(name = UserXRoleEntity.IX_01, columnList = UserXRoleEntity.COL_USER_ID + "," + UserXRoleEntity.COL_ROLE_ID, unique = true)
})
@IdClass(UserXRoleEntity.Pk.class)
@Generated // To disable counting in unit test coverage
public class UserXRoleEntity
{
    public static final String TABLE_NAME = "userxrole";

    public static final String IX_01 = "ix_userxrole_01";

    public static final String COL_USER_ID = "user_id";
    public static final String COL_ROLE_ID = "role_id";

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_USER_ID, nullable = false)
    private UserEntity user;

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_ROLE_ID, nullable = false)
    private RoleEntity role;


    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Pk implements Serializable
    {
        private UUID user;
        private UUID role;
    }
}
