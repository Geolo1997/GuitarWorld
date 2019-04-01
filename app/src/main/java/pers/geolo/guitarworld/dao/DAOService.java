package pers.geolo.guitarworld.dao;

import java.util.Date;

import pers.geolo.guitarworld.dao.impl.LogInfoDAOImpl;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.util.SingletonHolder;

public class DAOService {

    public static volatile DAOService instance;
    private LogInfoDAO logInfoDAO = SingletonHolder.getInstance(LogInfoDAOImpl.class);

    public static DAOService getInstance() {
        if (instance == null) {
            synchronized (DAOService.class) {
                if (instance == null) {
                    instance = new DAOService();
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
}
