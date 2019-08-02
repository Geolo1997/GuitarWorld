package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.geolo.guitarworldserver.annotation.Auth;
import pers.geolo.guitarworldserver.annotation.AuthType;
import pers.geolo.guitarworldserver.controller.param.CommentParam;
import pers.geolo.guitarworldserver.entity.Comment;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("comment")
@Auth(AuthType.LOGGED)
public class CommentController {

    Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Comment>> getComment(CommentParam param) {
        List<Comment> commentList = commentService.getCommentList(param);
        return new ResponseEntity<>(0, commentList, null);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addComment(@SessionAttribute("username") String username, @RequestBody Comment comment) {
        logger.debug("收到增加评论消息：" + username + " " + comment.getAuthor() + " " + comment.getWorksId());
        if (username.equals(comment.getAuthor())) {
            commentService.addComment(comment);
            return new ResponseEntity<>(0);
        } else {
            return new ResponseEntity<>(1);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> removeComment(CommentParam param) {
        // TODO 缺少权限检查
        commentService.removeCommentList(param);
        return new ResponseEntity<>(0);
    }
}
