package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.entity.UserRelation;

import java.util.List;

@Repository
public interface UserRelationDAO {

    void add(UserRelation userRelation);

    void remove(UserRelation userRelation);

    boolean exist(UserRelation userRelation);

    List<String> listFollowingUsername(String follower);

    List<String> listFollowerUsername(String following);
}
