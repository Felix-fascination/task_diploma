package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RaspController {

    private final RaspService raspService;

    private final RaspTables raspTables;

    private final CookieService cookieService;


    @GetMapping()
    public String getRaspPage(@RequestParam String raspUrl, @RequestParam String groupName,
                              @RequestParam(required = false) Boolean odd,
                              HttpServletResponse response, Model model){
        if (odd == null) odd = false;
        log.info(odd.toString());
        raspService.makeModel(model,raspUrl, groupName, odd);
        cookieService.setGroupName(response, groupName);
        return "rasp";
    }

    @ResponseBody
    @PostMapping("/comment/post")
    public void postComment(@RequestBody Comment comment,@RequestParam Boolean odd,
                            HttpServletRequest request){
        // Todo add a commentator through cookie
        String groupName = cookieService.getGroupName(request);
        String commentatorName = cookieService.getCommentatorName(request);
        comment.setCommentatorName(commentatorName);
        raspTables.addComment(groupName, odd, comment);

    }
}
