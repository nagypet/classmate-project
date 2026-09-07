package hu.perit.classmate.rest.model;

import hu.perit.classmate.config.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExtendedCourseDto extends CourseDto
{
    private Long countInstructors;
    private Long countStudents;
    private Map<Gender, Long> registrationCountByGender;
    private Long countWaitingStudents;
}
