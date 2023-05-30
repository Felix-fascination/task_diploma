package uni.task_diploma.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.task_diploma.service.AuthService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @GetMapping("/login")
    public String getAuthPage(){
        return "auth";
    }

    @PostMapping("/user/authenticate")
    public void authenticate(@RequestParam String name, HttpServletResponse response) throws IOException {
        authService.authenticate(name, response);
    }
}
