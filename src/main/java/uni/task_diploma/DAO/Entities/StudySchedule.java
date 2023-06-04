package uni.task_diploma.DAO.Entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "study_schedule")
@Builder
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Study_groups groups;

    String day_of_week;

    String time_of_day;

    @ManyToOne
    @JoinColumn(name = "para_id")
    Para para;

    Boolean odd;

}
