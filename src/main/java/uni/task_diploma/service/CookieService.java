package uni.task_diploma.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public void setGroupName(HttpServletResponse response, String groupName) {
        Cookie cookie = new Cookie("group_name", groupName);
        response.addCookie(cookie);
    }

    public String getGroupName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // Loop through the cookies to find a specific one
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("group_name")) {
                    String cookieValue = cookie.getValue();
                    // Do something with the cookie value
                    return cookieValue;
                }
            }
        }
        throw new RuntimeException("No group name");
    }
}
