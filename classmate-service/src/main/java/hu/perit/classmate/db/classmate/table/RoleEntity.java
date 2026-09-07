package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.converter.RoleConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = RoleEntity.TABLE_NAME, schema = Constants.SCHEMA, indexes = {
        @Index(name = RoleEntity.IX_01, columnList = RoleEntity.COL_ROLE, unique = true)
})
@Generated // To disable counting in unit test coverage
public class RoleEntity
{
    public static final String TABLE_NAME = "role";

    public static final String IX_01 = "ix_role_01";

    public static final String COL_ID = "id";
    public static final String COL_ROLE = "role";

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = COL_ID, nullable = false)
    private Long id;

    @NotNull
    @Column(name = COL_ROLE, nullable = false, unique = true)
    @Convert(converter = RoleConverter.class)
    private Role role;
}
