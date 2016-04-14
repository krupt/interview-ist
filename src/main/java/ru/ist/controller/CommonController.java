package ru.ist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ist.security.Security;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class CommonController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("login");
        Object securityException = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (securityException != null) {
            log.warn("Пользователь не авторизован");
            if (securityException instanceof BadCredentialsException || securityException instanceof UsernameNotFoundException) {
                modelAndView.addObject("error", "Неверное имя пользователя/пароль");
            } else {
                modelAndView.addObject("error", "Непредвиденная ошибка. Пожалуйста, обратитесь к администратору");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String getAccessDeniedPage() {
        return "403";
    }

    /**
     * Сделано для демонстрации страницы 403 (Недостаточно прав)
     */
    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @Secured(Security.ROLE_ADMIN)
    public String getAdminPage() {
        return "forward:/";
    }

}
