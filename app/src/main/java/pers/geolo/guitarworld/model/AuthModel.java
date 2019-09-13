package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.dao.DataBaseManager;
import pers.geolo.guitarworld.dao.LogInfoDAO;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.AuthApi;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class AuthModel {

    private AuthApi authApi = HttpClient.getService(AuthApi.class);

    public void register(String username, String password, String email,
                         String verifyCoed, DataCallback<Void> listener) {
        authApi.register(username, password, email, verifyCoed).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void login(String username, String password, DataCallback<Void> listener) {
        authApi.login(username, password).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void getLastSavedLogInfo(DataCallback<LogInfo> listener) {
        LogInfo logInfo = DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
        if (logInfo != null) {
            listener.onReturn(logInfo);
        } else {
            listener.onError("not exist loginfo");
        }
    }

    public void saveLogInfo(LogInfo logInfo) {
        LogInfoDAO logInfoDAO = DataBaseManager.getLogInfoDAO();
        if (logInfoDAO.getLogInfo(logInfo.getUsername()) != null) {
            logInfoDAO.update(logInfo);
        } else {
            logInfoDAO.add(logInfo);
        }
    }

    public void logout(DataCallback<Void> listener) {
        authApi.logout().enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public LogInfo getLoginUser() {
        return DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
    }

    public void autoLogin(DataCallback<Void> listener) {
        getLastSavedLogInfo(new DataCallback<LogInfo>() {
            @Override
            public void onReturn(LogInfo logInfo) {
                if (logInfo.isAutoLogin()) {
                    login(logInfo.getUsername(), logInfo.getPassword(), listener);
                } else {
                    listener.onError(null);
                }
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }
}
