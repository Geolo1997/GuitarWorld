package pers.geolo.guitarworldserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.geolo.guitarworldserver.dao.CommentDAO;
import pers.geolo.guitarworldserver.entity.Comment;

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDAO;

    /**
     * 增加评论
     *
     * @param comment
     */
    public void addComment(Comment comment) {
        commentDAO.add(comment);
    }

    public void removeComment(int id) {
        if (commentDAO.getComment(id) != null) {
            commentDAO.remove(id);
        }
    }

    public Comment getComment(int id) {
        return commentDAO.getComment(id);
    }

    public List<Comment> listCommentOfWorks(int worksId) {
        return commentDAO.listCommentOfWorks(worksId);
    }
}
