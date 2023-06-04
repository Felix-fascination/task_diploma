package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.Study_groups;

@Repository
public interface GroupRepository extends JpaRepository<Study_groups, Integer> {
    boolean existsByGroupName(String groupName);

    Study_groups getStudyByGroupName(String groupName);
}
