package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uni.task_diploma.module.GroupsRequest;
import uni.task_diploma.module.ScheduleRequest;
import uni.task_diploma.service.MainService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/main")
    public String getMainPage(Model model){
        mainService.makeMainPageModel(model);
        return "schedule";
    }

    @PostMapping("/main/groups/get")
    public String getGroups(Model model,
                            @RequestBody GroupsRequest request){
        mainService.makeGroupsModel(model, request);
        return "group_span";
    }

    @PostMapping("main/schedule/get")
    public String getSchedule(Model model,
                              @RequestBody ScheduleRequest request){
        mainService.makeSchedule(model, request);
        return "schedule_body";
    }

    @ResponseBody
    @PostMapping("/main/comment/post")
    public void postComment(@RequestParam String comment, @RequestParam Integer paraId,
                            HttpServletRequest request){
        log.info("{} + {}", comment, paraId);
        mainService.postComment(comment, paraId, request);
    }


}
