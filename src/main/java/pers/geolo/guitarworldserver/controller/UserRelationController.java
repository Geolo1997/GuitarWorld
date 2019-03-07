package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.entity.UserRelation;
import pers.geolo.guitarworldserver.entity.UserRelationType;
import pers.geolo.guitarworldserver.service.UserRelationService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

import java.util.List;

@Controller
public class UserRelationController {

    Logger logger = Logger.getLogger(UserRelationController.class);

    @Autowired
    UserRelationService userRelationService;

    @RequestMapping(value = "/addRelation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> addRelation(@RequestBody UserRelation userRelation) {
        logger.debug("收到关注请求：" + userRelation.toString());
        // 获取当前用户的用户名
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        // 获取粉丝用户名
        String followerUsername = userRelation.getFollowerUsername();
        if (currentUsername != null && currentUsername.equals(followerUsername)) { // 当前用户是关系中的粉丝
            userRelationService.addRelation(userRelation);
            return new ResponseJSONBody<>(0);
        } else {
            return new ResponseJSONBody<>(1);
        }
    }

    @RequestMapping(value = "/removeRelation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> removeRelation(@RequestBody UserRelation userRelation) {
        logger.debug("收到取关请求：" + userRelation.toString());
        // 获取当前用户的用户名
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        // 获取粉丝用户名
        String followerUsername = userRelation.getFollowerUsername();
        if (currentUsername != null && currentUsername.equals(followerUsername)) { // 当前用户是关系中的粉丝
            userRelationService.removeRelation(userRelation);
            return new ResponseJSONBody<>(0);
        } else {
            return new ResponseJSONBody<>(1);
        }
    }

    @RequestMapping(value = "/getMyRelationTypeWith", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<UserRelationType> getRelationType(String otherUsername) {
        logger.debug("收到获取关系请求：" + otherUsername);
        // 获取当前用户的用户名
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        // 获取用户关系类型
        UserRelationType userRelationType = userRelationService.getRelationType(currentUsername, otherUsername);
        return new ResponseJSONBody<>(0, userRelationType, null);
    }

    /**
     * 获取某用户关注
     * @param username 该用户用户名
     * @return 该用户粉丝用户名列表
     */
    @RequestMapping(value = "/getFollowing", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<List<String>> getFollowing(String username) {
        List<String> followings = userRelationService.getFollowings(username);
        return new ResponseJSONBody<>(0, followings, null);
    }

    /**
     * 获取某用户粉丝
     * @param username 该用户用户名
     * @return 该用户偶像用户名列表
     */
    @RequestMapping(value = "/getFollower", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<List<String>> getFollower(String username) {
        List<String> followers = userRelationService.getFollowers(username);
        return new ResponseJSONBody<>(0, followers, null);
    }
}
