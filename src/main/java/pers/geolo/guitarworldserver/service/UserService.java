package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.dao.UserDAO;
import pers.geolo.guitarworldserver.value.LogState;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public int login(String username, String password) {
        User user = userDAO.getUser(username);
        if (user == null) { // 不存在该用户
            return 2;
        } else if (user.getPassword().equals(password)) { // 登录成功
            user.setState(LogState.LOGIN); // 设置登录状态
            return 0;
        } else { //用户名或密码错误
            return 1;
        }

    }

    public int register(String username, String password, String email) {
        if (userDAO.getUser(username) != null) { // 用户名已存在
            return 1;
        } else { // 注册成功
            User user = new User(username, password, email);
            user.setState(LogState.LOGIN);
            userDAO.add(user);
            return 0;
        }
    }

    public int logout(String username) {
        User user = userDAO.getUser(username);
        if (user == null) { //不存在该用户
            return 1;
        } else {
            user.setState(LogState.LOGOUT);
            return 0;
        }
    }

    public User getProfile(String username) {
        User user = userDAO.getUser(username);
        System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getEmail());
        return userDAO.getUser(username);
    }

    public int update(User user) {
        if (userDAO.getUser(user.getUsername()) == null) {
            return 1;
        } else {
            userDAO.update(user);
            return 0;
        }
    }
}
