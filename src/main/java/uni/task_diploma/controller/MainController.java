package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uni.task_diploma.module.GroupsRequest;
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


}
