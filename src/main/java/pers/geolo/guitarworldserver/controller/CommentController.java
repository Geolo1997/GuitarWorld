package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.geolo.guitarworldserver.controller.param.CommentParam;
import pers.geolo.guitarworldserver.entity.Comment;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.service.CommentService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

import java.util.List;

@Controller
@RequestMapping("comment")
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
    public ResponseEntity<Void> addComment(@RequestBody Comment comment) {
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        logger.debug("收到增加评论消息：" + currentUsername + " " + comment.getAuthor() + " " + comment.getWorksId());
        if (currentUsername.equals(comment.getAuthor())) {
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
