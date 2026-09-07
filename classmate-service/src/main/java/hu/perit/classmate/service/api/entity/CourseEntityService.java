package hu.perit.classmate.service.api.entity;

import hu.perit.classmate.db.classmate.table.CourseEntity;

import java.time.LocalDate;
import java.util.List;

public interface CourseEntityService
{
    List<CourseEntity> findCourses(LocalDate startDate, Long days);
}
