package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import pers.geolo.guitarworldserver.annotation.Auth;
import pers.geolo.guitarworldserver.annotation.AuthType;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.service.FileService;
import pers.geolo.guitarworldserver.service.UserService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @Auth
    public ResponseEntity<Void> register(String username, String password,
                                         String email, String confirmCode) throws IOException {
        logger.debug("收到注册请求： username = " + username + ", password = " + password + ", email = " + email);
        int code = userService.register(username, password, email);
        return new ResponseEntity<>(code);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @Auth
    public ResponseEntity<Void> login(String username, String password) {
        logger.debug("收到登录请求： username = " + username + ", password = " + password);
        int code = userService.login(username, password);
        // 保存username到session
        if (code == 0) ControllerUtils.setSessionAttribute("username", username);
        ResponseEntity<Void> entity = new ResponseEntity<>(code);
        switch (code) {
            case 1:
                entity.setMessage("用户名或密码错误");
                break;
            case 2:
                entity.setMessage("用户不存在");
                break;
        }
        return entity;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @Auth(AuthType.LOGGED)
    public ResponseEntity<Void> logout(@SessionAttribute("username") String username) {
        logger.debug("收到用户" + username + "的注销登录请求");
        int code = userService.logout(username);
        ControllerUtils.getSession().removeAttribute("username");
        return new ResponseEntity<>(code);
    }
}
