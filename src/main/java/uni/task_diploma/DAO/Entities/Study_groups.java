package uni.task_diploma.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "study_groups")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Study_groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "course_name")
    String courseName;
    @Column(name = "faculty_name")
    String facultyName;
    @Column(name = "group_name")
    String groupName;
    String rasp;
}
