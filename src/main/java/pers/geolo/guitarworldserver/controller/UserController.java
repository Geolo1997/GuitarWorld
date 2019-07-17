package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.service.FileService;
import pers.geolo.guitarworldserver.service.UserService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    /**
     * 获取用户公开资料
     *
     * @param username 用户名
     * @return 用户公开资料
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserPublicProfile(@PathVariable("username") String username) {
        logger.debug("收到获取个人资料请求：" + username);
        User user = userService.getProfile(username);
        ResponseEntity<User> responseEntity = new ResponseEntity<>();
        if (user != null) {
            return new ResponseEntity<>(0, user, null);
        } else {
            return new ResponseEntity<>(1);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> updateUserInfo(@RequestBody User user) {
        logger.debug("收到更新信息请求：" + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());
        if (user.getUsername().equals(ControllerUtils.getSessionAttribute("username"))) {
            int code = userService.update(user);
            return new ResponseEntity<>(code);
        } else {
            return new ResponseEntity<>(-1);
        }
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> uploadAvatar(MultipartFile avatar) throws IOException {
        logger.debug("收到更新头像请求：" + avatar.getName());
        String username = (String) ControllerUtils.getSessionAttribute("username");
        String path = fileService.saveFile(avatar);
        // 更新User表头像路径字段
        userService.updateAvatar(username, path);
        return new ResponseEntity<>(0, path, null);
    }

    //-------test--------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(0, userList, null);
    }

    /**
     * 获取某用户关注
     *
     * @param username 该用户用户名
     * @return 该用户粉丝用户名列表
     */
    @RequestMapping(value = "/{username}/following", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getFollowing(@PathVariable("username") String username) {
        List<User> followings = userService.getFollowings(username);
        return new ResponseEntity<>(0, followings, null);
    }

    /**
     * 获取某用户粉丝
     *
     * @param username 该用户用户名
     * @return 该用户偶像用户名列表
     */
    @RequestMapping(value = "/{username}/follower", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getFollower(@PathVariable("username") String username) {
        List<User> followers = userService.getFollowers(username);
        return new ResponseEntity<>(0, followers, null);
    }
}
