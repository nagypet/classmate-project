package hu.perit.classmate.db.classmate.converter;

import hu.perit.classmate.config.RegistrationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Generated;

@Converter
@Generated // To disable counting in unit test coverage
public class RegistrationStatusConverter implements AttributeConverter<RegistrationStatus, Long>
{
    @Override
    public Long convertToDatabaseColumn(RegistrationStatus attribute)
    {
        if (attribute == null)
        {
            return null;
        }

        return attribute.getValue();
    }


    @Override
    public RegistrationStatus convertToEntityAttribute(Long dbData)
    {
        if (dbData == null)
        {
            return null;
        }

        return RegistrationStatus.fromValue(dbData).orElse(null);
    }
}
