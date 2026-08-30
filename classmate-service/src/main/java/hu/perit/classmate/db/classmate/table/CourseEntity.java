package hu.perit.classmate.db.classmate.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = CourseEntity.TABLE_NAME, schema = hu.perit.classmate.config.Constants.SCHEMA)
@EntityListeners(AuditingEntityListener.class)
@Generated // To disable counting in unit test coverage
public class CourseEntity
{
    public static final String TABLE_NAME = "course";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_INSTRUCTOR_ID = "instructor_id";
    public static final String COL_LOCATION = "location";
    public static final String COL_SCHEDULED_AT = "scheduled_at";
    public static final String COL_CAPACITY = "capacity";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_UPDATED_BY = "updated_by";

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

    @Column(name = COL_SCHEDULED_AT)
    private LocalDateTime scheduledAt;

    @NotNull
    @Column(name = COL_CAPACITY, nullable = false)
    private Integer capacity;

    @CreatedBy
    @Size(max = 150)
    @Column(name = COL_CREATED_BY, nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = COL_CREATED_AT, nullable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Size(max = 150)
    @Column(name = COL_UPDATED_BY)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = COL_UPDATED_AT)
    private Instant updatedAt;


    public OffsetDateTime getCreatedAt()
    {
        return createdAt == null ? null : OffsetDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }


    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt == null ? null : OffsetDateTime.ofInstant(updatedAt, ZoneId.systemDefault());
    }
}
