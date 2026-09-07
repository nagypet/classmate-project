package hu.perit.classmate.mapper;

import hu.perit.classmate.db.classmate.table.CourseEntity;
import hu.perit.classmate.rest.model.CourseDto;
import hu.perit.classmate.rest.model.ExtendedCourseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper
{
    CourseDto mapToDto(CourseEntity entity);

    ExtendedCourseDto mapToExtendedCourseDto(CourseEntity entity);
}
