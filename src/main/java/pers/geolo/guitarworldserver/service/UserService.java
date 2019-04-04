package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.dao.UserMapper;
import pers.geolo.guitarworldserver.value.LogState;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int login(String username, String password) {
        User user = userMapper.selectByUsername(username);
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
        if (userMapper.selectByUsername(username) != null) { // 用户名已存在
            return 1;
        } else { // 注册成功
            User user = new User(username, password, email);
            user.setState(LogState.LOGIN);
            userMapper.insert(user);
            return 0;
        }
    }

    public int logout(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) { //不存在该用户
            return 1;
        } else {
            user.setState(LogState.LOGOUT);
            return 0;
        }
    }

    public User getProfile(String username) {
        return userMapper.selectByUsername(username);
    }

    public int update(User user) {
        if (userMapper.selectByUsername(user.getUsername()) == null) {
            return 1;
        } else {
            userMapper.update(user);
            return 0;
        }
    }

    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }
}
