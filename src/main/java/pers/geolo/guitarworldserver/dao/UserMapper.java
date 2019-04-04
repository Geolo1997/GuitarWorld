package pers.geolo.guitarworldserver.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.User;

@Repository
public interface UserMapper {

    void insert(User user);

    void delete(String username);

    void update(User user);

    User selectByUsername(String username);


    // test
    List<User> selectAll();
}
