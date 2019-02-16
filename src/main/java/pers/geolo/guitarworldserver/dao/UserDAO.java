package pers.geolo.guitarworldserver.dao;

import pers.geolo.guitarworldserver.bean.User;

public interface UserDAO {

    void save(User user);

    void update(User user);

    User getUser(String username);
}
