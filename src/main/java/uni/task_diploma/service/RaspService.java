package uni.task_diploma.service;

import jakarta.persistence.PersistenceContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.constants.ParseFieldRasp;
import uni.task_diploma.module.ClassModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RaspService {

    public void makeModel(Model model, String raspUrl) {
        Document document = getDocument(raspUrl);
        // Parse the HTML to extract the elements you want
        Elements raspDays = document.select(ParseFieldRasp.RASP_DAY);
        Elements dayOfWeek = document.select(ParseFieldRasp.RASP_DAY_OF_WEEK);
        // Outer keu is day of the week
        // Inner key is the time
        Map<String, Map<String, ClassModule>> classes = new HashMap<String, Map<String, ClassModule>>();


        for(int i = 0; i < raspDays.size(); i++) {
            Map<String, ClassModule> dayClasses = new HashMap<>();


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
            String nameOfTheDay = dayOfWeek.get(0).text();
            int lectorCount = 0;
            for(int j = 0; j < timeElements.size(); j++) {
                String time = timeElements.get(j).text();
                String room = roomElements.get(j).text();
                String classType = classTypes.get(j).text();
                String className = classNames.get(j).text();
                String classLector;
                if (className.contains("изическ")) classLector = "";
                else {
                    classLector = classLectors.get(lectorCount).text();
                    lectorCount++;
                }

                classes.computeIfAbsent(nameOfTheDay, k -> new HashMap<>())
                        .computeIfAbsent(time,
                                k ->
                                ClassModule.builder()
                                .type(classType)
                                .lector(List.of(classLector))
                                .room(room)
                                .build()
                        );
            }


            /*Class.builder()
                    .type(classTypes.get(j).text())
                    .lector(List.of(classLectors.get(j).text()))
                    .room(roomElements.get(j).text()))
                                .build();*/

        }
        System.out.println("de");
        /*// Need to loop for everyday
        Elements timeElements = raspDays.select(ParseFieldRasp.RASP_TIME);
        // Нужна проверка на физру - нет кабинета
        Elements roomElements = raspDays.select(ParseFieldRasp.RASP_ROOM);
        //Все эти элементы идут параллельно
        Elements classNumber = raspDays.select(ParseFieldRasp.RASP_CLASS_NUMBER);

        // Experimental need to loop
        Elements classDescriptions = raspDays.select(ParseFieldRasp.RASP_CLASS_DESCRIPTION);
        Elements classNames = classDescriptions.select(ParseFieldRasp.CLASS_NAME);
        Elements classTypes = classDescriptions.select(ParseFieldRasp.CLASS_TYPE);
        Elements classLectors = classDescriptions.select(ParseFieldRasp.CLASS_LECTORS);*/


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
