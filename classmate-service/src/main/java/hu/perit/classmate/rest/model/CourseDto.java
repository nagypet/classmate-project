package hu.perit.classmate.rest.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class CourseDto
{
    private UUID id;
    private String title;
    private String location;
    private LocalDateTime scheduledAt;
    private Duration duration;
    private Integer capacity;
    private String createdBy;
    private OffsetDateTime createdAt;
    private String updatedBy;
    private OffsetDateTime updatedAt;
}
