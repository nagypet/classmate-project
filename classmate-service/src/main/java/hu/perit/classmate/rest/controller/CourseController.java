package hu.perit.classmate.rest.controller;

import hu.perit.classmate.config.Constants;
import hu.perit.classmate.rest.api.CourseApi;
import hu.perit.classmate.rest.model.FindCoursesResponse;
import hu.perit.classmate.service.api.CourseService;
import hu.perit.classmate.service.api.UserAccountService;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi
{
    private final AuthorizationService authorizationService;
    private final CourseService courseService;


    @Override
    @LoggedRestMethod(eventId = Constants.COURSE_CONTROLLER_FIND_COURSES)
    public FindCoursesResponse findCourses(LocalDate startDate, Long days, UUID instructorId)
    {
        return this.courseService.findCourses(startDate, days, instructorId);
    }
}
