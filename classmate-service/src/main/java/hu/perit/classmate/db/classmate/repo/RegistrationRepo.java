package hu.perit.classmate.db.classmate.repo;

import hu.perit.classmate.db.classmate.table.RegistrationEntity;
import hu.perit.spvitamin.spring.data.pessimistic.PessimisticJpaRepository;

import java.util.UUID;

public interface RegistrationRepo extends PessimisticJpaRepository<RegistrationEntity, UUID>
{
}
