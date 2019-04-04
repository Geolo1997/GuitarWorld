package pers.geolo.guitarworldserver.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.geolo.guitarworldserver.dao.CommentMapper;
import pers.geolo.guitarworldserver.entity.Comment;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    public void removeCommentList(HashMap<String, Object> filter) {
        commentMapper.delete(filter);
    }

    public List<Comment> getCommentList(HashMap<String, Object> filter) {
        return commentMapper.select(filter);
    }
}
