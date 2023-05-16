package uni.task_diploma.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.constants.ParseFieldRasp;
import uni.task_diploma.module.ClassModule;
import uni.task_diploma.module.RaspTable;
import uni.task_diploma.module.RaspTables;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RaspService {

    private final RaspTables raspTables;

    public void makeModel(Model model, String raspUrl, String groupName) {
        if (raspTables.isPresent(groupName)){
            getModel(model, groupName);
            return;
        }
        Document document = getDocument(raspUrl);
        // Parse the HTML to extract the elements you want
        Elements raspDays = document.select(ParseFieldRasp.RASP_DAY);
        Elements dayOfWeek = document.select(ParseFieldRasp.RASP_DAY_OF_WEEK);
        // Outer keu is day of the week
        // Inner key is the time
        Map<String, Map<String, ClassModule>> classes = new HashMap<String, Map<String, ClassModule>>();


        for(int i = 0; i < raspDays.size(); i++) {
            
            //Map<String, ClassModule> dayClasses = new HashMap<>();
            // Времена в течения дня
            Elements timeElements = raspDays.get(i).select(ParseFieldRasp.RASP_TIME);
            //Кабинеты в течение дня - добавить проверку на физру
            Elements roomElements = raspDays.get(i).select(ParseFieldRasp.RASP_ROOM);
            // Номера кабинетов
            Elements classNumber = raspDays.get(i).select(ParseFieldRasp.RASP_CLASS_NUMBER);
            // Описания в течение дня
            Elements classDescriptions = raspDays.get(i).select(ParseFieldRasp.RASP_CLASS_DESCRIPTION);
            // Вытаскиваем отсюда название предметра, его тип и имена преподавателей
            Elements classNames = classDescriptions.select(ParseFieldRasp.CLASS_NAME);
            Elements classTypes = classDescriptions.select(ParseFieldRasp.CLASS_TYPE);
            Elements classLectors = classDescriptions.select(ParseFieldRasp.CLASS_LECTORS);
            // Название существующего дня
            String nameOfTheDay = dayOfWeek.get(i).text();
            int count = 0;
            for(int j = 0; j < timeElements.size(); j++) {
                String time = timeElements.get(j).text();
                String classType = classTypes.get(j).text();
                String className = classNames.get(j).text();
                String classLector;
                String room;
                if (className.contains("изическ")) {
                    classLector = "";
                    room = "";
                }
                else {
                    try{
                        classLector = classLectors.get(count).text();
                        room = roomElements.get(count).text();
                    }
                    catch (Exception e){
                        classLector = "";
                        room = "";
                    }
                    count++;
                }

                String finalClassLector = classLector;
                String finalRoom = room;
                classes.computeIfAbsent(nameOfTheDay, k -> new HashMap<>())
                        .computeIfAbsent(time,
                                k ->
                                ClassModule.builder()
                                .className(className)
                                .type(classType)
                                .lector(List.of(finalClassLector))
                                .room(finalRoom)
                                .build()
                        );
            }


        }
        RaspTable raspTable = new RaspTable(classes);
        createModel(model, raspTable, groupName);
        //System.out.println(raspTable);


    }

    public void createModel(Model model, RaspTable raspTable, String groupName ){
        List<String> daysOfWeek = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
        List<String> timesOfDay = List.of("09:00 — 10:30","10:45 — 12:15", "13:15 — 14:45", "15:00 — 16:30", "16:45 — 18:15", "18:25 — 20:05");

        model.addAttribute("classTimes", timesOfDay);
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("scheduleData", raspTables.putTable(groupName, raspTable));
    }

    private void getModel(Model model, String groupName){
        List<String> daysOfWeek = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
        List<String> timesOfDay = List.of("09:00 — 10:30","10:45 — 12:15", "13:15 — 14:45", "15:00 — 16:30", "16:45 — 18:15", "18:25 — 20:05");

        model.addAttribute("classTimes", timesOfDay);
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("scheduleData", raspTables.getTable(groupName));
    }

    private Document getDocument(String raspUrl) {
        try{
            return Jsoup
                    .connect(raspUrl)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
