package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.dao.UserRelationMapper;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.entity.UserRelation;
import pers.geolo.guitarworldserver.entity.UserRelationType;

import java.util.List;

@Service
public class UserRelationService {

    @Autowired
    UserRelationMapper userRelationMapper;

    /**
     * 增加用户关系
     *
     * @param userRelation
     */
    public void addRelation(UserRelation userRelation) {
        if (!userRelationMapper.exist(userRelation)) {
            userRelationMapper.insert(userRelation);
        }
    }

    /**
     * 删除用户关系
     *
     * @param userRelation
     */
    public void removeRelation(UserRelation userRelation) {
        if (userRelationMapper.exist(userRelation)) {
            userRelationMapper.delete(userRelation);
        }
    }

    /**
     * 更新用户关系
     * @param userRelation
     */
    public void updateRelationType(UserRelation userRelation) {
        if (userRelationMapper.exist(userRelation)) {
            userRelationMapper.updateRelationType(userRelation);
        }
    }

    /**
     * 获取用户粉丝
     *
     * @param username
     * @return
     */
    public List<String> getFollowers(String username) {
        return userRelationMapper.listFollowerUsername(username);
    }

    /**
     * 获取用户偶像
     *
     * @param username
     * @return
     */
    public List<String> getFollowings(String username) {
        return userRelationMapper.listFollowingUsername(username);
    }

    /**
     * 获取当前用户和其他用户的关系
     * @param currentUsername
     * @param otherUsername
     * @return
     */
    public UserRelation getUserRelation(String currentUsername, String otherUsername) {
        // 该用户是否是当前用户的粉丝
        return userRelationMapper.select(currentUsername, otherUsername);
    }
}
