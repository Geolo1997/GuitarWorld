package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.dao.DataBaseManager;
import pers.geolo.guitarworld.dao.LogInfoDAO;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.DataCallback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class AuthModel {

    private static AuthApi authApi = HttpClient.getService(AuthApi.class);

    public static void register(String username, String password, String email,
                                String verifyCoed, DataListener<Void> listener) {
        authApi.register(username, password, email, verifyCoed).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }

    public static void login(String username, String password, DataListener<Void> listener) {
        authApi.login(username, password).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }

    public static void getLastSavedLogInfo(DataListener<LogInfo> listener) {
        LogInfo logInfo = DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
        listener.onReturn(logInfo);
    }

    public static void saveLogInfo(LogInfo logInfo) {
        LogInfoDAO logInfoDAO = DataBaseManager.getLogInfoDAO();
        if (logInfoDAO.getLogInfo(logInfo.getUsername()) != null) {
            logInfoDAO.update(logInfo);
        } else {
            logInfoDAO.add(logInfo);
        }
    }

    public static void logout(DataListener<Void> listener) {
        authApi.logout().enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }

    public static LogInfo getCurrentLoginUser() {
        return DataBaseManager.getLogInfoDAO().getLastSavedLogInfo();
    }

    public static void autoLogin(DataListener<Void> listener) {
        getLastSavedLogInfo(new DataListener<LogInfo>() {
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
