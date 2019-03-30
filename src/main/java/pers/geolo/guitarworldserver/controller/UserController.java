package pers.geolo.guitarworldserver.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.service.UserService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<User> getUserInfo(@PathVariable("username") String username) {
        logger.debug("收到获取个人资料请求：" + username);
        User user = userService.getProfile(username);
        ResponseJSONBody<User> responseJSONBody = new ResponseJSONBody<>();
        if (user != null) {
            return new ResponseJSONBody<>(0, user, null);
        } else {
            return new ResponseJSONBody<>(1);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> updateUserInfo(@RequestBody User user) {
        logger.debug("收到更新信息请求：" + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());
        if (user.getUsername().equals(ControllerUtils.getSessionAttribute("username"))) {
            int code = userService.update(user);
            return new ResponseJSONBody<>(code);
        } else {
            return new ResponseJSONBody<>(-1);
        }
    }

    //-------test--------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return new ResponseJSONBody<>(0, userList, null);
    }
}
