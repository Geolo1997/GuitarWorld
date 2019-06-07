package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.constant.HttpConstants;
import pers.geolo.guitarworld.dao.DataBaseManager;
import pers.geolo.guitarworld.dao.LogInfoDAO;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.http.HttpClient;
import pers.geolo.guitarworld.http.HttpMethod;
import pers.geolo.guitarworld.http.callback.IError;
import pers.geolo.guitarworld.http.callback.IFailure;
import pers.geolo.guitarworld.http.callback.ISuccess;
import pers.geolo.guitarworld.model.listener.LoginListener;
import pers.geolo.guitarworld.model.listener.LogoutListener;
import pers.geolo.guitarworld.model.listener.RegisterListener;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class AuthModel {

    public static void register(String username, String password, String email, RegisterListener listener) {
        HttpClient.newRequest()
                .url(HttpConstants.REGISTER_URL)
                .method(HttpMethod.POST)
                .params("username", username)
                .params("password", password)
                .params("email", email)
                .success(new ISuccess<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {
                        listener.onError(message);
                    }
                })
                .execute();
    }

    public static void login(String username, String password, LoginListener listener) {
        HttpClient.newRequest()
                .url(HttpConstants.LOGIN_URL)
                .method(HttpMethod.POST)
                .params("username", username)
                .params("password", password)
                .success(new ISuccess<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {
                        listener.onError(message);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        listener.onFailure();
                    }
                })
                .execute();
    }

    public static LogInfo getLastSavedLogInfo() {
        return DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
    }

    public static void saveLogInfo(LogInfo logInfo) {
        LogInfoDAO logInfoDAO = DataBaseManager.getLogInfoDAO();
        if (logInfoDAO.getLogInfo(logInfo.getUsername()) != null) {
            logInfoDAO.update(logInfo);
        } else {
            logInfoDAO.add(logInfo);
        }
    }

    public static void logout(String username, LogoutListener listener) {
        HttpClient.newRequest()
                .url(HttpConstants.LOGOUT_URL)
                .method(HttpMethod.POST)
                .params("username", username)
                .success(new ISuccess<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess();
                    }
                })
                .execute();
    }

    public static LogInfo getCurrentLoginUser() {
        return DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
    }
}
