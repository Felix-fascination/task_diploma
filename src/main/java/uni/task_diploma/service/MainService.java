package uni.task_diploma.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uni.task_diploma.DAO.Entities.Study_groups;
import uni.task_diploma.DAO.repository.GroupRepository;
import uni.task_diploma.DAO.repository.ParaRepository;
import uni.task_diploma.DAO.repository.ScheduleRepository;
import uni.task_diploma.module.ClassModule;
import uni.task_diploma.module.GroupsRequest;
import uni.task_diploma.module.ScheduleRequest;
import uni.task_diploma.module.TimeValue;
import uni.task_diploma.mapper.ClassModulesFromScheduleMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final GroupRepository groupRepository;

    private final ScheduleRepository scheduleRepository;

    private final ParaRepository paraRepository;

    private final CookieService cookieService;

    private final ClassModulesFromScheduleMapper mapperClass;

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

    public void makeSchedule(Model model, ScheduleRequest request) {
        Study_groups group = groupRepository.getStudyByGroupName(request.getGroup());
        Map<TimeValue, List<ClassModule>> modules =  mapperClass.getClassModulesFromSchedule(scheduleRepository.getStudyScheduleByGroupsAndOdd(group, request.getIsODD()));
        model.addAttribute("modules", modules);
    }

    public void postComment(String comment, Integer paraId, HttpServletRequest request) {
        comment = "Заметка: " + comment;
        paraRepository.updateById(paraId, comment, "Автор заметки: " + cookieService.getCommentatorName(request));
    }
}
