package pers.geolo.guitarworld.dao;

import pers.geolo.guitarworld.entity.User;

public interface UserDAO {
    void add(User user);

    void add(String username, String password);

    void add(String username);

    void setAutoLogin(boolean checked);

    User getUser();

    boolean isAutoLogin();

    void clearPassword();

    void update(User user);
}
