package hu.perit.classmate.service.api.entity;

import hu.perit.classmate.db.classmate.table.RegistrationEntity;

import java.util.List;
import java.util.UUID;

public interface RegistrationEntityService
{
    List<RegistrationEntity> findAllById(List<UUID> ids);
}
