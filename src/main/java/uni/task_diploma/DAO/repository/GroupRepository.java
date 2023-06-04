package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.Study_groups;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Study_groups, Integer> {
    boolean existsByGroupName(String groupName);

    Study_groups getStudyByGroupName(String groupName);

    List<Study_groups> getStudyByCourseNameAndFacultyName(String courseName, String facultyName);
}
