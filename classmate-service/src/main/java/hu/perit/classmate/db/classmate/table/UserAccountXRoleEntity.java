package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = UserAccountXRoleEntity.TABLE_NAME, schema = Constants.SCHEMA, indexes = {
        @Index(name = UserAccountXRoleEntity.IX_01, columnList = UserAccountXRoleEntity.COL_USER_ID + "," + UserAccountXRoleEntity.COL_ROLE_ID, unique = true)
})
@IdClass(UserAccountXRoleEntity.Pk.class)
@Generated // To disable counting in unit test coverage
public class UserAccountXRoleEntity
{
    public static final String TABLE_NAME = "useraccount_x_role";

    public static final String IX_01 = "ix_useraccount_x_role_01";

    public static final String COL_USER_ID = "user_id";
    public static final String COL_ROLE_ID = "role_id";

    @Id
    @NotNull
    @Column(name = COL_USER_ID, nullable = false)
    private UUID userId;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_USER_ID, referencedColumnName = UserAccountEntity.COL_ID, insertable = false, updatable = false)
    private UserAccountEntity userAccountEntity;

    @Id
    @NotNull
    @Column(name = COL_ROLE_ID, nullable = false)
    private UUID roleId;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_ROLE_ID, referencedColumnName = RoleEntity.COL_ID, insertable = false, updatable = false)
    private RoleEntity roleEntity;


    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Pk implements Serializable
    {
        private UUID userId;
        private UUID roleId;
    }
}
