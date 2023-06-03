package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.task_diploma.service.CookieService;
import uni.task_diploma.service.MainService;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    private final CookieService cookieService;

    @GetMapping("/")
    public String getMainPage(@RequestParam String name, Model model,
                              HttpServletResponse response){
        log.warn("getMainPage");
        cookieService.setCommentatorName(response, name);
        mainService.makeMainPageModel(model);
        return "main";
    }


}
