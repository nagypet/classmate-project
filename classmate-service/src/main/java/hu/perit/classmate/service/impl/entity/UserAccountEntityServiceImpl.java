package hu.perit.classmate.service.impl.entity;

import hu.perit.classmate.db.classmate.repo.UserAccountRepo;
import hu.perit.classmate.db.classmate.table.UserAccountEntity;
import hu.perit.classmate.service.api.entity.UserAccountEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountEntityServiceImpl implements UserAccountEntityService
{
    private final UserAccountRepo repo;


    @Override
    public Optional<UserAccountEntity> findByAuthProviderAndUserName(String authProvider, String userName)
    {
        return this.repo.findByAuthProviderAndUserName(authProvider, userName);
    }


    @Override
    public UserAccountEntity save(UserAccountEntity userAccountEntity)
    {
        return this.repo.save(userAccountEntity);
    }
}
