package hu.perit.classmate.service.api;

import hu.perit.classmate.rest.model.FindCoursesResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface CourseService
{
    FindCoursesResponse findCourses(LocalDate startDate, Long days, UUID instructorId);
}
