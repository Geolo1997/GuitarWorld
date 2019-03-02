package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.entity.User;

@Repository
public interface UserDAO {

    void add(User user);

    void update(User user);

    User getUser(String username);

    void remove(String username);
}
