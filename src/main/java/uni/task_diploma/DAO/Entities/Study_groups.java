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
@AllArgsConstructor
@NoArgsConstructor
public class Study_groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String courseName;
    String facultyName;
    String groupName;
    String rasp;
}
