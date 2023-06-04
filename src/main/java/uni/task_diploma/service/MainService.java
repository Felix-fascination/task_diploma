package uni.task_diploma.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.DAO.Entities.Study_groups;
import uni.task_diploma.DAO.repository.GroupRepository;
import uni.task_diploma.constants.ParseFieldsMain;
import uni.task_diploma.constants.UrlParsingConstants;
import uni.task_diploma.module.GroupElement;
import uni.task_diploma.module.GroupsRequest;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final GroupRepository groupRepository;

    public void makeMainPageModel(Model model){
        List<String> courses = Arrays.asList("1 курс", "2 курс", "3 курс", "4 курс", "5 курс");
        List<String> faculties = new ArrayList<>();
        faculties.add("Управление перевозками и логистика");
        faculties.add("Автоматизация и интеллектуальные технологии");
        faculties.add("Промышленное и гражданское строительство");
        faculties.add("Экономика и менеджмент");
        faculties.add("Транспортное строительство");
        faculties.add("Транспортные и энергетические системы");


        model.addAttribute("courses", courses);
        model.addAttribute("Faculties", faculties);
    }
    public void makeGroupsModel(Model model, GroupsRequest request) {
        List<Study_groups> groups = groupRepository.getStudyByCourseNameAndFacultyName(request.getCourse(), request.getFaculty());
        List<String> groups_names = new ArrayList<>(groups.size());
        for (Study_groups group : groups) {
            groups_names.add(group.getGroupName());
        }
        model.addAttribute("groups", groups_names);
    }
}
