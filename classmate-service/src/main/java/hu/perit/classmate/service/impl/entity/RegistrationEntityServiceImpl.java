package hu.perit.classmate.service.impl.entity;

import hu.perit.classmate.db.classmate.repo.RegistrationRepo;
import hu.perit.classmate.db.classmate.table.RegistrationEntity;
import hu.perit.classmate.service.api.entity.RegistrationEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationEntityServiceImpl implements RegistrationEntityService
{
    private final RegistrationRepo repo;


    @Override
    public List<RegistrationEntity> findAllById(List<UUID> ids)
    {
        return this.repo.findAllById(ids);
    }
}
