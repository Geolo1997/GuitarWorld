package pers.geolo.guitarworld.dao;

import java.util.Date;

import pers.geolo.guitarworld.dao.impl.LogInfoDAOImpl;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.util.SingletonHolder;

public class DataBaseManager {

    public static volatile DataBaseManager instance;
    private LogInfoDAO logInfoDAO = SingletonHolder.getInstance(LogInfoDAOImpl.class);

    public static DataBaseManager getInstance() {
        if (instance == null) {
            synchronized (DataBaseManager.class) {
                if (instance == null) {
                    instance = new DataBaseManager();
                }
            }
        }
        return instance;
    }

    public LogInfo getLogInfo(String username) {
        return logInfoDAO.getLogInfo(username);
    }

    public void addLogInfo(LogInfo logInfo) {
        logInfo.setSaveTime(new Date());
        logInfoDAO.add(logInfo);
    }

    public LogInfo getCurrentLogInfo() {
        return logInfoDAO.getLastSavedLogInfo();
    }

    public void removeLogInfo(LogInfo logInfo) {
        logInfoDAO.remove(logInfo);
    }

    public void updateLogInfo(LogInfo logInfo) {
        logInfo.setSaveTime(new Date());
        logInfoDAO.update(logInfo);
    }

    public static LogInfoDAO getLogInfoDAO() {
        return SingletonHolder.getInstance(LogInfoDAOImpl.class);
    }
}
