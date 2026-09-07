package hu.perit.classmate.rest.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class FindCoursesResponse
{
    @Setter(AccessLevel.NONE)
    private List<ExtendedCourseDto> courses = new ArrayList<>();
}
