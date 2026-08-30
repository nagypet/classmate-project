package hu.perit.classmate.service.impl.entity;

import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.repo.RoleRepo;
import hu.perit.classmate.db.classmate.table.RoleEntity;
import hu.perit.classmate.service.api.entity.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleEntityServiceImpl implements RoleEntityService
{
    private final RoleRepo repo;


    @Override
    public Optional<RoleEntity> findByRole(Role role)
    {
        return this.repo.findByRole(role);
    }
}
