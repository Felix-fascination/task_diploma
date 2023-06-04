package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
