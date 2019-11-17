package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.controller.param.CommentParam;
import pers.geolo.guitarworldserver.entity.Comment;

import java.util.List;

@Repository
public interface CommentMapper {

    List<Comment> select(CommentParam param);

    void insert(Comment comment);

    void delete(CommentParam param);
}
