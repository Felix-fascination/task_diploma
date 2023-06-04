package uni.task_diploma.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "para")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Para {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String paraName;
    String type;
    String room;
    String lectors;
    String cmnt;
    String cmnt_name;
}
