package uni.task_diploma.module;

import lombok.ToString;

import java.util.*;

@ToString
public class RaspTable {
    // Second is day of the week
    // Third is time of the day
    private final  Map<String, Map<String, ClassModule>> raspTable = new HashMap<>();

    public RaspTable (Map<String, Map<String, ClassModule>> rasp) {
        List<String> daysOfWeek = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
        List<String> timesOfDay = List.of("09:00 — 10:30","10:45 — 12:15", "13:15 — 14:45", "15:00 — 16:30", "16:45 — 18:15", "18:25 — 20:05");

        for(String day: daysOfWeek){
            for(String time: timesOfDay){
                raspTable.computeIfAbsent(day, k -> new HashMap<>())
                                .computeIfAbsent(time, k -> new ClassModule());
            }
        }
        for (Map.Entry<String, Map<String, ClassModule>> dayElement: rasp.entrySet()){
            for(Map.Entry<String, ClassModule> element : dayElement.getValue().entrySet()){
                raspTable.get(dayElement.getKey()).put(element.getKey(), element.getValue());
            }
        }
    }

    public Set<Map.Entry<String, Map<String, ClassModule>>> entrySet(){
        return raspTable.entrySet();
    }

    public Map<String, ClassModule> getDaySchedule(String day){
        return raspTable.get(day);
    }
}
