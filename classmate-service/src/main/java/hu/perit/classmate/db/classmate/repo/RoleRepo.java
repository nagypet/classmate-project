package hu.perit.classmate.db.classmate.repo;

import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.table.RoleEntity;
import hu.perit.spvitamin.spring.data.pessimistic.PessimisticJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends PessimisticJpaRepository<RoleEntity, UUID>
{
    @Query("select e from RoleEntity e where e.role = :role")
    Optional<RoleEntity> findByRole(Role role);
}
