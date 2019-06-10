package pers.geolo.guitarworldserver.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.entity.UserRelation;

import java.util.List;

@Repository
public interface UserRelationMapper {

    void insert(UserRelation userRelation);

    void delete(UserRelation userRelation);

    UserRelation select(@Param("current_username") String currentUsername,
                        @Param("other_username") String otherUsername);

    void updateRelationType(UserRelation userRelation);

    boolean exist(UserRelation userRelation);

    List<String> listFollowingUsername(String follower);

    List<String> listFollowerUsername(String following);
}
