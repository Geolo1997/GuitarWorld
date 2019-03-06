package pers.geolo.guitarworld.dao;

import pers.geolo.guitarworld.dao.impl.LogInfoDAOImpl;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.util.SingletonHolder;

import java.util.Date;

public class DAOService {

    public static volatile DAOService instance;

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

    private LogInfoDAO logInfoDAO = SingletonHolder.getInstance(LogInfoDAOImpl.class);

    public void saveLogInfo(LogInfo logInfo) {
        logInfo.setSaveTime(new Date());
        logInfoDAO.save(logInfo);
    }

    public LogInfo getCurrentLogInfo() {
        return logInfoDAO.getLastSavedLogInfo();
    }

    public void removeLogInfo(LogInfo logInfo) {
        logInfoDAO.remove(logInfo);
    }
}
