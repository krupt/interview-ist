package ru.ist.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Для AJAX-запросов отправляем ошибку 403, а для остальных стандартная логика Spring Security
 */
public class AjaxRequestAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String header = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(header)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        } else {
            super.handle(request, response, accessDeniedException);
        }
    }

}
