package uni.task_diploma.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.constants.ParseFields;
import uni.task_diploma.constants.UrlParsingConstants;
import uni.task_diploma.module.GroupElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

@Service
public class MainService {

    public String makeMainPageModel(Model model){
        TreeSet<GroupElement> groupElements;
        try{
            // Connect to the website
            Document document = Jsoup
                    .connect(UrlParsingConstants.RASP_MAIN_PAGE)
                    .get();

            // Extract data from specific elements
            groupElements = new TreeSet<>();
            getGroupElements(document, groupElements, ParseFields.FirstCourse, 1);
            getGroupElementsFromOtherCourses(document, groupElements);

            Iterator<GroupElement> iterator = groupElements.descendingIterator();
            model.addAttribute("groups", groupElements);
            /*model.addAttribute("")
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }*/
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getGroupElementsFromOtherCourses(Document document, TreeSet<GroupElement> groupElements){
        for(int courseNumber = 2; courseNumber <  6; courseNumber++){
            getGroupElements(document, groupElements, ParseFields.otherCourses + courseNumber, courseNumber);
        }
    }

    public void getGroupElements(Document document, TreeSet<GroupElement> groupElements, String courseField, int courseNumber){
        Element container = document.selectFirst(courseField);
        Elements facultyContainers = container.select(ParseFields.FACULTY_CONTAINERS);

        for(Element facultyContainer : facultyContainers){
            Elements buttons = facultyContainer.select(ParseFields.ButtonClass);
            Element facultyName = facultyContainer.selectFirst(ParseFields.FACULTY_NAME);
            for (Element button : buttons){
                // Extract href value and button name
                groupElements.add(
                        GroupElement.builder()
                                .groupName(button.text())
                                .groupHref(button.attr("href"))
                                .facultyName(facultyName.text())
                                .course( courseNumber + " курс")
                                .build()
                );
            }
        }
    }

    /*public void getGroupElementsFromFirstCourse(Document document, TreeSet<GroupElement> groupElements){

    }*/

}
