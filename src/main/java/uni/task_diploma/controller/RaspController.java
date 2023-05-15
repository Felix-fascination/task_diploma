package uni.task_diploma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.task_diploma.service.RaspService;

@Controller
@RequiredArgsConstructor
public class RaspController {

    private final RaspService raspService;

    @GetMapping("/rasp")
    public String getRaspPage(@RequestParam String raspUrl, Model model){
        raspService.makeModel(model,raspUrl);
        return "rasp";
    }
}
