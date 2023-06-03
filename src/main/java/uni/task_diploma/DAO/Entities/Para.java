package uni.task_diploma.DAO.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Para {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String paraName;
    String type;
    String groupName;
    String rasp;


}
