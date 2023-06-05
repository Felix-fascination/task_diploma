package uni.task_diploma.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uni.task_diploma.DAO.Entities.StudySchedule;
import uni.task_diploma.DAO.repository.ParaRepository;
import uni.task_diploma.module.ClassModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UtilityClass {

    private final ParaRepository paraRepository;
    public ClassModule getClassModuleFromSchedule(StudySchedule schedule){
        return  ClassModule.builder()
                .className(schedule.getPara().getParaName())
                .comment(schedule.getPara().getCmnt() == null? "" : schedule.getPara().getCmnt())
                .commentator(schedule.getPara().getCmnt_name() == null? "" : schedule.getPara().getCmnt())
                .type(schedule.getPara().getType())
                .lector(schedule.getPara().getLectors())
                .build();
    }

    public Map<String, Map<String, ClassModule>> getClassModulesFromSchedules(List<StudySchedule> schedules){
        List<ClassModule> modules = new ArrayList<>(schedules.size());
        for(StudySchedule schedule: schedules){
            modules.add(getClassModuleFromSchedule(schedule));
        }
        // Outer key is day
        // Inner key is time
        Map<String, Map<String, ClassModule>> raspTable = new HashMap<>();
        List<String> daysOfWeek = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
        List<String> timesOfDay = List.of("09:00 — 10:30","10:45 — 12:15", "13:15 — 14:45", "15:00 — 16:30", "16:45 — 18:15", "18:25 — 20:05");

        for(String day: daysOfWeek){
            for(String time: timesOfDay){
                raspTable.computeIfAbsent(day, k -> new HashMap<>())
                        .computeIfAbsent(time, k -> new ClassModule());
            }
        }
        for (StudySchedule schedule: schedules){
            raspTable.get(schedule.getDay_of_week()).put(schedule.getTime_of_day(), getClassModuleFromSchedule(schedule));
        }

        return raspTable;
    }
}
