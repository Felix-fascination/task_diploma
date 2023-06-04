package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.StudySchedule;

@Repository
public interface ScheduleRepository extends JpaRepository<StudySchedule, Integer> {
}
