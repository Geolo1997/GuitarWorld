package pers.geolo.guitarworldserver.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.geolo.guitarworldserver.entity.Comment;
import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.CommentService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
public class CommentController {

    Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> addComment(@RequestBody Comment comment) {
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        logger.debug("收到增加评论消息：" + currentUsername + " " + comment.getAuthor() + " " + comment.getWorksId());
        if (currentUsername.equals(comment.getAuthor())) {
            commentService.addComment(comment);
            return new ResponseJSONBody<>(0);
        } else {
            return new ResponseJSONBody<>(1);
        }
    }

    @RequestMapping(value = "/removeComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> removeComment(int id) {
        // TODO 缺少权限检查
        commentService.removeComment(id);
        return new ResponseJSONBody<>(0);
    }

    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Comment> getComment(int id) {
        Comment comment = commentService.getComment(id);
        return new ResponseJSONBody<>(0, comment, null);
    }

    @RequestMapping(value = "/listCommentOfWorks", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<List<Comment>> listCommentOfWorks(int worksId) {
        List<Comment> commentList = commentService.listCommentOfWorks(worksId);
        return new ResponseJSONBody<>(0, commentList, null);
    }
}
