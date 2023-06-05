package uni.task_diploma.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uni.task_diploma.DAO.Entities.Lector;
import uni.task_diploma.DAO.Entities.Para;
import uni.task_diploma.DAO.Entities.StudySchedule;
import uni.task_diploma.DAO.Entities.Study_groups;
import uni.task_diploma.DAO.repository.GroupRepository;
import uni.task_diploma.DAO.repository.LectorRepository;
import uni.task_diploma.DAO.repository.ParaRepository;
import uni.task_diploma.DAO.repository.ScheduleRepository;
import uni.task_diploma.constants.ParseFieldRasp;
import uni.task_diploma.constants.ParseFieldsMain;
import uni.task_diploma.constants.UrlParsingConstants;
import uni.task_diploma.module.GroupElement;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleCollector {

    private final GroupRepository groupRepository;

    private final ParaRepository paraRepository;

    private final ScheduleRepository scheduleRepository;

    private final LectorRepository lectorRepository;

    @Scheduled(initialDelay = 0, fixedRate = 1000000000)
    public void runOnStartup() {
        log.warn("Starting ScheduleCollector");
        setGroupsForDb();
        List<Study_groups> groups = groupRepository.findAll();
        for (Study_groups group : groups) {
            for(int i = 0; i < 2; i++ ){
                makeSchedule(group.getRasp(), group.getGroupName(), i == 0);
            }
        }
        log.warn("Ended ScheduleCollector");
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void collectGarbageConnections(){

    }

    public void setGroupsForDb(){
        // First key is course
        // Second key is faculty name
        TreeMap<String, Map<String, List<GroupElement>>> groupElements;
        try{
            // Connect to the website
            Document document = Jsoup
                    .connect(UrlParsingConstants.RASP_MAIN_PAGE)
                    .get();
            // Extract data from specific elements
            groupElements = new TreeMap<>();
            getGroupElements(document, groupElements, ParseFieldsMain.FirstCourse, 1);
            getGroupElementsFromOtherCourses(document, groupElements);

            for(String course : groupElements.keySet()){
                for(Map.Entry<String, List<GroupElement>> facultyEntry : groupElements.get(course).entrySet()){
                    for(GroupElement groupElement : facultyEntry.getValue()){
                        if (!groupRepository.existsByGroupName(groupElement.getGroupName())){
                            groupRepository.save(Study_groups.builder()
                                    .courseName(course)
                                    .facultyName(facultyEntry.getKey())
                                    .rasp(groupElement.getGroupHref())
                                    .groupName(groupElement.getGroupName())
                                    .build());
                        }

                    }
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getGroupElementsFromOtherCourses(Document document, Map<String, Map<String, List<GroupElement>>> groupElements){
        for(int courseNumber = 2; courseNumber <  6; courseNumber++){
            getGroupElements(document, groupElements, ParseFieldsMain.otherCourses + courseNumber, courseNumber);
        }
    }

    public void getGroupElements(Document document, Map<String, Map<String, List<GroupElement>>> groupElements, String courseField, int courseNumber){
        Element container = document.selectFirst(courseField);
        Elements facultyContainers = container.select(ParseFieldsMain.FACULTY_CONTAINERS);

        for(Element facultyContainer : facultyContainers){
            Elements buttons = facultyContainer.select(ParseFieldsMain.ButtonClass);
            Element facultyName = facultyContainer.selectFirst(ParseFieldsMain.FACULTY_NAME);
            for (Element button : buttons){
                // Extract href value and button name
                groupElements.computeIfAbsent(courseNumber + " курс", k -> new HashMap<>())
                        .computeIfAbsent(facultyName.text(), k -> new ArrayList<>())
                        .add(GroupElement.builder()
                                .groupName(button.text())
                                .groupHref(button.attr("href"))
                                .build()
                        );
            }
        }
    }


    public void makeSchedule(String raspUrl, String groupName, Boolean odd) {
        Document document = getDocument(raspUrl, odd);
        // Parse the HTML to extract the elements
        Elements raspDays = document.select(ParseFieldRasp.RASP_DAY);
        Elements dayOfWeek = document.select(ParseFieldRasp.RASP_DAY_OF_WEEK);



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

            // Название существующего дня
            String nameOfTheDay = dayOfWeek.get(i).text();
            int count = 0;
            for(int j = 0; j < timeElements.size(); j++) {
                Element classNames = classDescriptions.get(j).selectFirst(ParseFieldRasp.CLASS_NAME);
                Element classTypes = classDescriptions.get(j).selectFirst(ParseFieldRasp.CLASS_TYPE);
                Elements classLectors = classDescriptions.get(j).select(ParseFieldRasp.CLASS_LECTORS);

                List<String> lectors = new ArrayList<>();
                for (int k = 0; k < classLectors.size(); k++) {
                    lectors.add(classLectors.get(k).text());
                }
                if (lectors.isEmpty()) lectors.add("");


                String time = timeElements.get(j).text();
                String classType = classTypes.text();
                String className = classNames.text();
                String room;
                if (className.contains("изическ")) {

                    room = "";
                }
                else {
                    try{
                        room = roomElements.get(count).text();
                    }
                    catch (Exception e){
                        room = "";
                    }
                    count++;
                }
                String finalRoom = room;

                String lectors_string = lectors.toString();
                if (lectors_string.length() > 2) lectors_string = lectors_string.substring(1, lectors_string.length() - 1 );
                else lectors_string = "";

                List<Lector> lector_entities = new ArrayList<Lector>();

                for(String lector : lectors){
                    Lector lector_entity = Lector.builder()
                            .name(lector)
                            .build();

                    lector_entities.add(lector_entity);
                    lectorRepository.save(lector_entity);
                }

                Para para = Para.builder()
                        .paraName(className)
                        .type(classType)
                        .lector(lector_entities)
                        .room(finalRoom)
                        .build();

                paraRepository.save(para);

                StudySchedule schedule = StudySchedule.builder()
                        .day_of_week(nameOfTheDay)
                        .time_of_day(time)
                        .groups(groupRepository.getStudyByGroupName(groupName))
                        .para(para)
                        .odd(odd)
                        .build();
                scheduleRepository.save(schedule);

            }
        }
    }

    private Document getDocument(String raspUrl, Boolean odd) {
        try{
            return Jsoup
                    .connect(raspUrl + "?odd=" + (odd ? "1" : "0"))
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
