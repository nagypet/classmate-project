package hu.perit.classmate.service.impl.entity;

import hu.perit.classmate.db.classmate.repo.CourseRepo;
import hu.perit.classmate.db.classmate.table.CourseEntity;
import hu.perit.classmate.service.api.entity.CourseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEntityServiceImpl implements CourseEntityService
{
    private final CourseRepo repo;


    @Override
    public List<CourseEntity> findCourses(LocalDate startDate, Long days)
    {
        return this.repo.findCourses(startDate.atStartOfDay(), startDate.plusDays(days).atStartOfDay());
    }
}
