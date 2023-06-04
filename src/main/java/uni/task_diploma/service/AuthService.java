package uni.task_diploma.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uni.task_diploma.DAO.repository.ParaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final CookieService cookieService;

    private final AuthenticationManager authenticationManager;

    private final ParaRepository paraRepository;



    public void authenticate(String name, HttpServletResponse response) {
        //log.error("Use got here to authent");
        cookieService.setCommentatorName(response, name);
        if (name.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, null));
        }
        catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        log.info("Authentication was successful: " + name);
        System.out.println(paraRepository.getReferenceById(1));

        response.setStatus(HttpStatus.OK.value());
    }

}
