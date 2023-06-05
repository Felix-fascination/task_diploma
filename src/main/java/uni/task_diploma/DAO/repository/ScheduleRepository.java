package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.StudySchedule;
import uni.task_diploma.DAO.Entities.Study_groups;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<StudySchedule, Integer> {

    List<StudySchedule> getStudyScheduleByGroupsAndOdd(Study_groups group, Boolean isOdd);
}
