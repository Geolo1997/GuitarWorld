package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.UserService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
@RequestMapping("/auth")
public class AuthController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> register(String username, String password, String email) {
        logger.debug("收到注册请求： username = " + username + ", password = " + password + ", email = " + email);
        int code = userService.register(username, password, email);
        if (code == 0) ControllerUtils.setSessionAttribute("username", username);
        return new ResponseJSONBody<>(code);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> login(String username, String password) {
        logger.debug("收到登录请求： username = " + username + ", password = " + password);
        int code = userService.login(username, password);
        // 保存username到session
        if (code == 0) ControllerUtils.setSessionAttribute("username", username);
        return new ResponseJSONBody<>(code);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> logout() {
        String username = (String) ControllerUtils.getSessionAttribute("username");
        logger.debug("收到用户" + username + "的注销登录请求");
        int code = userService.logout(username);
        return new ResponseJSONBody<>(code);
    }
}
