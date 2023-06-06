package uni.task_diploma.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uni.task_diploma.DAO.Entities.StudySchedule;
import uni.task_diploma.DAO.repository.LectorRepository;
import uni.task_diploma.DAO.repository.ParaRepository;
import uni.task_diploma.module.ClassModule;
import uni.task_diploma.module.TimeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MapperClass {


    private final LectorRepository lectorRepository;

    private final List<String> daysOfWeek = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
    private final List<String> timesOfDay = List.of("09:00 — 10:30","10:45 — 12:15", "13:15 — 14:45", "15:00 — 16:30", "16:45 — 18:15", "18:25 — 20:05");
    public ClassModule getClassModuleFromSchedule(StudySchedule schedule){
        List<String> lectors = lectorRepository.getAllByPara(schedule.getPara());
        String lectors_string = lectors.toString();
        lectors_string = lectors_string.substring(1, lectors_string.length() - 1);

        return  ClassModule.builder()
                .className(schedule.getPara().getParaName())
                .comment(schedule.getPara().getCmnt() == null? "" : schedule.getPara().getCmnt())
                .commentator(schedule.getPara().getCmnt_name() == null? "" : schedule.getPara().getCmnt_name())
                .timeNumber(timesOfDay.indexOf(schedule.getTime_of_day()) + 1)
                .dayNumber(daysOfWeek.indexOf(schedule.getDay_of_week()) + 1)
                .time(schedule.getTime_of_day())
                .type(schedule.getPara().getType())
                .lector(lectors_string)
                .room(schedule.getPara().getRoom())
                .id(schedule.getId())
                .build();
    }

    public Map<TimeValue, List<ClassModule>> getClassModulesFromSchedules(List<StudySchedule> schedules){

        Map<TimeValue, List<ClassModule>> raspTable = new HashMap<>();

        List<String> timesOfSchedules = new ArrayList<>();

        for (StudySchedule schedule: schedules){
            if(!timesOfSchedules.contains(schedule.getTime_of_day())) timesOfSchedules.add(schedule.getTime_of_day());
        }

        for (String time : timesOfSchedules) {
            raspTable.computeIfAbsent(
                    TimeValue.builder()
                            .timeIndex(timesOfSchedules.indexOf(time) + 1)
                            .time(time)
                            .build(), k -> new ArrayList<>());
            }

        List<StudySchedule> listForTime = new ArrayList<>();
        for (String time : timesOfSchedules) {
                for (StudySchedule schedule : schedules) {
                    if (time.equals(schedule.getTime_of_day())) {
                        listForTime.add(schedule);
                    }
                }
                for (int i = 0; i < daysOfWeek.size(); i++) {
                    if (!listForTime.isEmpty() && daysOfWeek.get(i).equals(listForTime.get(0).getDay_of_week())) {
                        raspTable.get(TimeValue.builder()
                                .timeIndex(timesOfSchedules.indexOf(time) + 1)
                                .time(time)
                                .build()).add(getClassModuleFromSchedule(listForTime.get(0)));
                        listForTime.remove(0);
                    }
                    else raspTable.get(TimeValue.builder()
                            .timeIndex(timesOfSchedules.indexOf(time) + 1)
                            .time(time)
                            .build()).add(new ClassModule());
                }

        }

        return raspTable;

    }
}
