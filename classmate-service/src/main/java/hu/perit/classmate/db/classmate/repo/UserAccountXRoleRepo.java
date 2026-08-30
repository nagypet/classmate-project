package hu.perit.classmate.db.classmate.repo;

import hu.perit.classmate.db.classmate.table.UserAccountXRoleEntity;
import hu.perit.spvitamin.spring.data.pessimistic.PessimisticJpaRepository;

public interface UserAccountXRoleRepo extends PessimisticJpaRepository<UserAccountXRoleEntity, UserAccountXRoleEntity.Pk>
{
}
