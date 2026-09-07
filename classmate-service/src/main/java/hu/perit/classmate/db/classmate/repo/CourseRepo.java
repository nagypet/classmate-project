package hu.perit.classmate.db.classmate.repo;

import hu.perit.classmate.db.classmate.table.CourseEntity;
import hu.perit.spvitamin.spring.data.pessimistic.PessimisticJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CourseRepo  extends PessimisticJpaRepository<CourseEntity, UUID>
{
    @Query("select e from CourseEntity e where e.scheduledAt >= :from and e.scheduledAt < :to")
    List<CourseEntity> findCourses(LocalDateTime from, LocalDateTime to);
}
