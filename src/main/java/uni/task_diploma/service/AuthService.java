package uni.task_diploma.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final CookieService cookieService;

    private final AuthenticationManager authenticationManager;



    public HttpStatus authenticate(String name, HttpServletResponse response) {
        cookieService.setCommentatorName(response, name);
        if (name.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, "password"));
        }
        catch (Exception e){
            return HttpStatus.UNAUTHORIZED;
        }
        log.info("Authentication was successful: " + name);
        return HttpStatus.OK;
    }

}
