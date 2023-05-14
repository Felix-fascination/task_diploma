package uni.task_diploma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uni.task_diploma.service.MainService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String getMainPage(){
        mainService.makeMainPageModel();
        return null;
    }
}
