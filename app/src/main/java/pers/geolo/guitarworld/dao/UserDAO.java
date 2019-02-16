package pers.geolo.guitarworld.dao;

import pers.geolo.guitarworld.model.User;

public interface UserDAO {
    void save(User user);

    void save(String username, String password);

    void save(String username);

    void setAutoLogin(boolean checked);

    User getUser();

    boolean isAutoLogin();

    void clearPassword();
}
