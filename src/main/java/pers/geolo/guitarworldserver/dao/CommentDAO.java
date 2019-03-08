package pers.geolo.guitarworldserver.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.Comment;

@Repository
public interface CommentDAO {

    void add(Comment comment);

    void remove(int id);

    Comment getComment(int id);

    List<Comment> listCommentOfWorks(int worksId);
}
