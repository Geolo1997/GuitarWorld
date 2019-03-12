package pers.geolo.guitarworldserver.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ControllerUtils {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }
}
