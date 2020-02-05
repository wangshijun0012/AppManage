package cn.onesdream.interceptor;

import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("devUserSession") == null && request.getSession().getAttribute("userSession") == null){
            response.sendRedirect("/dev/login");
            return false;
        }
            return true;
    }
}
