package pers.geolo.guitarworld.service;

import pers.geolo.guitarworld.dao.DAOFactory;
import pers.geolo.guitarworld.dao.UserDAO;
import pers.geolo.guitarworld.model.User;
import pers.geolo.guitarworld.network.*;
import pers.geolo.guitarworld.util.SingletonHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class UserService {

    UserAPI userAPI;
    UserDAO userDAO;

    public UserService() {
        userAPI = SingletonHolder.getInstance(APIFactory.class).getAPI(UserAPI.class);
        userDAO = SingletonHolder.getInstance(DAOFactory.class).getDAO(UserDAO.class);
    }

    public void setState(String username, String password, boolean isAutoLogin, boolean isRememberPassword) {
        if (isAutoLogin) { // 自动登录
            userDAO.setAutoLogin(true);
            userDAO.save(username, password);
        } else {
            userDAO.setAutoLogin(false);
            if (isRememberPassword) { // 记住密码
                userDAO.save(username, password);
            } else {
                userDAO.save(username);
            }
        }
    }

    public boolean isAutoLogin() {
        return userDAO.isAutoLogin();
    }

    public String getUsername() {
        return userDAO.getUser().getUsername();
    }

    public User getUser() {
        return userDAO.getUser();
    }

    public void logout() {
        userDAO.setAutoLogin(false);
        userDAO.clearPassword();
        userAPI.logout().enqueue(new Callback<ResponseBody<Void>>() {
            @Override
            public void onResponse(Call<ResponseBody<Void>> call, Response<ResponseBody<Void>> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody<Void>> call, Throwable t) {

            }
        });
    }

    public void login(BaseCallback<Void> callback) {
        User user = userDAO.getUser();
        userAPI.login(user.getUsername(), user.getPassword()).enqueue(callback);
    }

    public void register(String username, String password, String email, BaseCallback<Void> callback) {
        userAPI.register(username, password, email).enqueue(callback);
    }

}
