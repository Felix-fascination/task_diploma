package uni.task_diploma.DAO.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uni.task_diploma.DAO.Entities.Para;

@Repository
public interface ParaRepository extends JpaRepository<Para, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Para p SET p.cmnt = :cmnt, p.cmnt_name = :cmnt_name WHERE p.id = :id")
    void updateById(Integer id, String cmnt, String cmnt_name);;
}
