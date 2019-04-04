package pers.geolo.guitarworldserver.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.Comment;

@Repository
public interface CommentMapper {

    void insert(Comment comment);

    void delete(HashMap<String, Object> filter);

    List<Comment> select(HashMap<String, Object> filter);
}
