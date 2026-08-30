package hu.perit.classmate.service.api.entity;

import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.table.RoleEntity;

import java.util.Optional;

public interface RoleEntityService
{
    Optional<RoleEntity> findByRole(Role role);
}
