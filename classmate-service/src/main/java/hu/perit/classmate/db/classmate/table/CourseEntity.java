package hu.perit.classmate.db.classmate.table;

import hu.perit.classmate.db.classmate.converter.DurationConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = CourseEntity.TABLE_NAME, schema = hu.perit.classmate.config.Constants.SCHEMA)
@Generated // To disable counting in unit test coverage
public class CourseEntity extends BaseAuditedEntity<UUID>
{
    public static final String TABLE_NAME = "course";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_INSTRUCTOR_ID = "instructor_id";
    public static final String COL_LOCATION = "location";
    public static final String COL_SCHEDULED_AT = "scheduled_at";
    public static final String COL_DURATION = "duration";
    public static final String COL_CAPACITY = "capacity";

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = COL_ID, nullable = false)
    private UUID id;

    @NotNull
    @Size(max = 255)
    @Column(name = COL_TITLE, nullable = false)
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = COL_INSTRUCTOR_ID, nullable = false)
    private UserAccountEntity instructor;

    @Size(max = 255)
    @Column(name = COL_LOCATION)
    private String location;

    @NotNull
    @Column(name = COL_SCHEDULED_AT, nullable = false)
    private LocalDateTime scheduledAt;

    @NotNull
    @Column(name = COL_DURATION, nullable = false)
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @NotNull
    @Column(name = COL_CAPACITY, nullable = false)
    private Integer capacity;
}
