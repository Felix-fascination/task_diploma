package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.task_diploma.service.AuthService;
import uni.task_diploma.service.CookieService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthController {


    private final AuthService authService;

    @GetMapping("/login")
    public String getAuthPage(){
        return "index";
    }

    @PostMapping("/authenticate")
    public HttpStatus authenticate(@RequestParam String name, HttpServletResponse response) throws IOException {
        return authService.authenticate(name, response);
    }
}
