package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uni.task_diploma.module.Comment;
import uni.task_diploma.module.RaspTables;
import uni.task_diploma.service.CookieService;
import uni.task_diploma.service.RaspService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rasp")
public class RaspController {

    private final RaspService raspService;

    private final RaspTables raspTables;

    private final CookieService cookieService;


    @GetMapping()
    public String getRaspPage(@RequestParam String raspUrl, @RequestParam String groupName,
                              HttpServletResponse response, Model model){
        raspService.makeModel(model,raspUrl, groupName);
        cookieService.setGroupName(response, groupName);

        return "rasp";
    }

    @PostMapping("/comment/post")
    public void postComment(@RequestBody Comment comment, HttpServletRequest request,
                            HttpServletResponse response){
        // Todo add a commentator through cookiew
        String groupName = cookieService.getGroupName(request);
        raspTables.addComment(groupName, comment);




    }
}
