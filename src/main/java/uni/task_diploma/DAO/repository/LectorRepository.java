package uni.task_diploma.DAO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.Lector;
import uni.task_diploma.DAO.Entities.Para;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Integer> {

    List<String> getAllByPara(Para para);
}
