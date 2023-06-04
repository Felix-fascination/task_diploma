package uni.task_diploma.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.DAO.repository.ParaRepository;
import uni.task_diploma.constants.ParseFieldsMain;
import uni.task_diploma.constants.UrlParsingConstants;
import uni.task_diploma.module.GroupElement;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ParaRepository paraRepository;

    public void makeMainPageModel(Model model){
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

            Set<String> entry = null;
            for(int i = 0; i < 1; i++) {
                entry = groupElements.get("1 курс").keySet();
            }

            model.addAttribute("groupMap", groupElements);
            model.addAttribute("Faculties", entry);
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

    /*public void getGroupElementsFromFirstCourse(Document document, TreeSet<GroupElement> groupElements){

    }*/

}
