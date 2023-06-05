package uni.task_diploma.DAO.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lectors")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            @ToString.Exclude
    Integer id;

    @ManyToOne
    @JoinColumn(name = "para_id")
            @ToString.Exclude
    Para para;

    String name;
}
