package hu.perit.classmate.service.impl;

import hu.perit.classmate.config.Gender;
import hu.perit.classmate.config.RegistrationStatus;
import hu.perit.classmate.config.Role;
import hu.perit.classmate.db.classmate.table.CourseEntity;
import hu.perit.classmate.db.classmate.table.RegistrationEntity;
import hu.perit.classmate.mapper.EntityMapper;
import hu.perit.classmate.rest.model.ExtendedCourseDto;
import hu.perit.classmate.rest.model.FindCoursesResponse;
import hu.perit.classmate.service.api.CourseService;
import hu.perit.classmate.service.api.entity.CourseEntityService;
import hu.perit.classmate.service.api.entity.RegistrationEntityService;
import hu.perit.spvitamin.core.typehelpers.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService
{
    private final CourseEntityService courseEntityService;
    private final RegistrationEntityService registrationEntityService;
    private final EntityMapper entityMapper;


    @Override
    public FindCoursesResponse findCourses(LocalDate startDate, Long days, UUID instructorId)
    {
        startDate = Objects.requireNonNullElse(startDate, LocalDate.now());
        days = Objects.requireNonNullElse(days, 7L);

        List<CourseEntity> courseEntities = this.courseEntityService.findCourses(startDate, days);
        Map<UUID, CourseEntity> courseMap = MapUtils.toMap(courseEntities, CourseEntity::getId);
        List<RegistrationEntity> registrationEntities = this.registrationEntityService.findAllById(courseEntities.stream().map(CourseEntity::getId).toList());
        Map<UUID, Set<RegistrationEntity>> registrationMap = MapUtils.groupBy(registrationEntities, RegistrationEntity::getCourseId);

        List<ExtendedCourseDto> courseDtos = new ArrayList<>();
        for (Map.Entry<UUID, Set<RegistrationEntity>> entry : registrationMap.entrySet())
        {
            if (instructorId == null || entry.getValue().stream().anyMatch(i -> i.getUserId().equals(instructorId)))
            {
                CourseEntity courseEntity = courseMap.get(entry.getKey());
                Set<RegistrationEntity> registrations = entry.getValue();
                ExtendedCourseDto courseDto = this.entityMapper.mapToExtendedCourseDto(courseEntity);
                courseDto.setCountInstructors(registrations.stream().filter(i -> i.getUser().getRoles().contains(Role.ROLE_INSTRUCTOR)).count());
                courseDto.setCountStudents(registrations.stream().filter(i -> i.getUser().getRoles().contains(Role.ROLE_STUDENT)).count());
                courseDto.setCountWaitingStudents(registrations.stream().filter(i -> i.getUser().getRoles().contains(Role.ROLE_STUDENT) && i.getStatus().equals(RegistrationStatus.WAITLISTED)).count());
                // Count registrations by gender
                EnumMap<Gender, Long> genderStat = new EnumMap<>(Gender.class);
                registrations.forEach(i -> genderStat.merge(i.getUser().getGender(), 1L, Long::sum));
                courseDto.setRegistrationCountByGender(genderStat);
                courseDtos.add(courseDto);
            }
        }

        FindCoursesResponse response = new FindCoursesResponse();
        response.getCourses().addAll(courseDtos);
        return response;
    }
}
