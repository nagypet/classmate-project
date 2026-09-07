package hu.perit.classmate.rest.api;

import hu.perit.classmate.rest.model.FindCoursesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

public interface CourseApi
{
    String BASE_URL = "/api/courses";


    //------------------------------------------------------------------------------------------------------------------
    // findCourses
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "findCourses() - Returns courses beginning from a given date",
            security = {@SecurityRequirement(name = "bearer")}
    )
    @StandardApiResponses200_400_401_403_500
    //------------------------------------------------------------------------------------------------------------------
    FindCoursesResponse findCourses(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "days", required = false) Long days,
            @RequestParam(value = "instructorId", required = false) UUID instructorId
    );
    //------------------------------------------------------------------------------------------------------------------
}
