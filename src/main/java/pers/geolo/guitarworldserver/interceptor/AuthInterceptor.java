package pers.geolo.guitarworldserver.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.geolo.guitarworldserver.annotation.Auth;
import pers.geolo.guitarworldserver.annotation.AuthType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取类和方法上的注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth classAuth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        Auth methodAuth = handlerMethod.getMethodAnnotation(Auth.class);
        // 将所有的注解value值放入集合
        Set<AuthType> authTypes = new HashSet<>();
        if (classAuth != null) {
            Collections.addAll(authTypes, classAuth.value());
        }
        if (methodAuth != null) {
            Collections.addAll(authTypes, methodAuth.value());
        }
        if (authTypes.contains(AuthType.ALL)) {
            return true;
        } else if (authTypes.contains(AuthType.LOGGED)) {
            String loginUsername = (String) request.getSession().getAttribute("username");
            if (loginUsername != null && !loginUsername.equals("")) {
                return true;
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }
}
