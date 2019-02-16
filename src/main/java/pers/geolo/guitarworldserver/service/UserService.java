package pers.geolo.guitarworldserver.service;

import pers.geolo.guitarworldserver.bean.User;
import pers.geolo.guitarworldserver.dao.UserDAO;
import pers.geolo.guitarworldserver.value.LogState;

public class UserService {

    UserDAO userDAO;

    public UserService() {

    }

    public int login(String username, String password) {
        return 0;
//        User user = userDAO.getUser(username);
//        if (user == null) { // 不存在该用户
//            return 2;
//        } else if (user.getPassword().equals(password)) { // 登录成功
//            user.setState(LogState.LOGIN); // 设置登录状态
//            return 0;
//        } else { //用户名或密码错误
//            return 1;
//        }

    }

    public int register(String username, String password, String email) {
        if (userDAO.getUser(username) != null) { // 用户名已存在
            return 1;
        } else { // 注册成功
            User user = new User(username, password, email);
            user.setState(LogState.LOGIN);
            userDAO.save(user);
            return 0;
        }
    }

    public int logout(String username) {
        return 0;
//        User user = userDAO.getUser(username);
//        if (user == null) { //不存在该用户
//            return 1;
//        } else {
//            user.setState(LogState.LOGOUT);
//            return 0;
//        }
    }
}
