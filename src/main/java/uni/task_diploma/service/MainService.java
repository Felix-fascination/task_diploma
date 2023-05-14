package uni.task_diploma.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uni.task_diploma.constants.ParseFields;
import uni.task_diploma.constants.UrlParsingConstants;
import uni.task_diploma.module.GroupElement;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

@Service
public class MainService {

    public String makeMainPageModel(){
        try{
            // Connect to the website
            Document document = Jsoup
                    .connect(UrlParsingConstants.RASP_MAIN_PAGE)
                    .get();

            // Extract data from specific elements
            TreeSet<GroupElement> groupElements = new TreeSet<>();
            getGroupElementsFromFirstCourse(document, groupElements);
            //getGroupElementsFromOtherCourses(document, groupElements);


            Iterator<GroupElement> iterator = groupElements.descendingIterator();
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getGroupElementsFromOtherCourses(Document document, TreeSet<GroupElement> groupElements){
        Elements containers = document.select(ParseFields.otherCourses);
        int courseNumber = 2;
        for(Element courseContainer:containers){
            Elements facultyContainers = courseContainer.select(ParseFields.FACULTY_CONTAINERS);
            // В рамках факультета курса
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
                                    .course(courseNumber + " курс")
                                    .build()
                    );
                }
            }
            courseNumber++;
        }
    }

    public void getGroupElementsFromFirstCourse(Document document, TreeSet<GroupElement> groupElements){
        Element container = document.selectFirst(ParseFields.FirstCourse);
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
                                .course("1 курс")
                                .build()
                );
            }
        }
    }

}
