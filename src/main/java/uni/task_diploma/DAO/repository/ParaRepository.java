package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.Para;

@Repository
public interface ParaRepository extends JpaRepository<Para, Integer> {
}
