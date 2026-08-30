package hu.perit.classmate.service.api.entity;

import hu.perit.classmate.db.classmate.table.UserAccountEntity;

import java.util.Optional;

public interface UserAccountEntityService
{
    Optional<UserAccountEntity> findByAuthProviderAndUserName(String authProvider, String userName);

    UserAccountEntity save(UserAccountEntity userAccountEntity);
}
