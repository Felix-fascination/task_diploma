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
        // Установка атрибутов cookie
        cookie.setPath("/"); // Устанавливает путь, доступный для cookie на всем сайте
        cookie.setMaxAge(86400); // Устанавливает срок действия cookie (в секундах). Здесь 86400 секунд равны 24 часам.
        cookie.setHttpOnly(true); // Устанавливает атрибут HttpOnly, чтобы предотвратить доступ к cookie из JavaScript

        response.addCookie(cookie);
    }

    public String getGroupName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // Loop through the cookies to find a specific one
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("group_name")) {
                    return cookie.getValue();
                }
            }
        }
        throw new RuntimeException("No group name");
    }

    public void setCommentatorName(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie("commentator_nam", name);
        // Установка атрибутов cookie
        cookie.setPath("/"); // Устанавливает путь, доступный для cookie на всем сайте
        cookie.setMaxAge(86400); // Устанавливает срок действия cookie (в секундах). Здесь 86400 секунд равны 24 часам.
        cookie.setHttpOnly(true); // Устанавливает атрибут HttpOnly, чтобы предотвратить доступ к cookie из JavaScript

        response.addCookie(cookie);
    }

    public String getCommentatorName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // Loop through the cookies to find a specific one
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("commentator_nam")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
