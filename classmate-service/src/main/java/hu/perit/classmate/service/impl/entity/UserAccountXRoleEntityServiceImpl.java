package hu.perit.classmate.service.impl.entity;

import hu.perit.classmate.db.classmate.repo.UserAccountXRoleRepo;
import hu.perit.classmate.db.classmate.table.UserAccountXRoleEntity;
import hu.perit.classmate.service.api.entity.UserAccountXRoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountXRoleEntityServiceImpl implements UserAccountXRoleEntityService
{
    private final UserAccountXRoleRepo repo;

    @Override
    public UserAccountXRoleEntity save(UserAccountXRoleEntity userAccountXRoleEntity)
    {
        return null;
    }
}
