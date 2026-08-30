package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.config.RegistrationStatus;
import hu.perit.classmate.db.classmate.converter.RegistrationStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = RegistrationEntity.TABLE_NAME, schema = hu.perit.classmate.config.Constants.SCHEMA, indexes = {
        @Index(name = RegistrationEntity.IX_01, columnList = RegistrationEntity.COL_COURSE_ID + "," + RegistrationEntity.COL_USER_ID, unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public class RegistrationEntity
{
    public static final String TABLE_NAME = "registration";

    public static final String IX_01 = "ix_registration_01";

    public static final String COL_ID = "id";
    public static final String COL_COURSE_ID = "course_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_STATUS = "status";
    public static final String COL_WAITLIST_POSITION = "waitlist_position";
    public static final String COL_REGISTERED_AT = "registered_at";

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = COL_ID, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_COURSE_ID, nullable = false)
    private CourseEntity course;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_USER_ID, nullable = false)
    private UserAccountEntity user;

    @NotNull
    @Column(name = COL_STATUS, nullable = false)
    @Convert(converter = RegistrationStatusConverter.class)
    private RegistrationStatus status;

    @Column(name = COL_WAITLIST_POSITION)
    private Integer waitlistPosition;

    @CreatedDate
    @Column(name = COL_REGISTERED_AT, nullable = false)
    private Instant registeredAt;
}
