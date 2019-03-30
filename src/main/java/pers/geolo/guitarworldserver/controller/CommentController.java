package pers.geolo.guitarworldserver.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pers.geolo.guitarworldserver.entity.Comment;
import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.CommentService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
@RequestMapping("comment")
public class CommentController {

    Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseJSONBody<Void> removeComment(@PathVariable("id") int id) {
        // TODO 缺少权限检查
        commentService.removeComment(id);
        return new ResponseJSONBody<>(0);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<Comment> getComment(@PathVariable("id") int id) {
        Comment comment = commentService.getComment(id);
        return new ResponseJSONBody<>(0, comment, null);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<Comment>> listCommentOfWorks(Integer worksId) {
        List<Comment> commentList = commentService.listCommentOfWorks(worksId);
        return new ResponseJSONBody<>(0, commentList, null);
    }
}
