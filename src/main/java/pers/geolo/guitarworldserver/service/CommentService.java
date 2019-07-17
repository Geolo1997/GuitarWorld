package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.controller.param.CommentParam;
import pers.geolo.guitarworldserver.dao.CommentMapper;
import pers.geolo.guitarworldserver.entity.Comment;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    public List<Comment> getCommentList(CommentParam param) {
        return commentMapper.select(param);
    }

    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    public void removeCommentList(CommentParam param) {
        commentMapper.delete(param);
    }
}
