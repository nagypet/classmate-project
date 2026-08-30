package hu.perit.classmate.db.classmate.repo;

import hu.perit.classmate.db.classmate.table.UserAccountEntity;
import hu.perit.spvitamin.spring.data.pessimistic.PessimisticJpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepo extends PessimisticJpaRepository<UserAccountEntity, UUID>
{
    Optional<UserAccountEntity> findByAuthProviderAndUserName(String oauthProvider, String oauthSubject);
}
