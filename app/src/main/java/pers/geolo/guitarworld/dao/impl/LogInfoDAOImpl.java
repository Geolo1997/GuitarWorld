package pers.geolo.guitarworld.dao.impl;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import pers.geolo.guitarworld.dao.LogInfoDAO;
import pers.geolo.guitarworld.entity.LogInfo;

import java.util.List;

public class LogInfoDAOImpl implements LogInfoDAO {

    public LogInfoDAOImpl() {
        LitePal.getDatabase();
    }

    @Override
    public void save(LogInfo logInfo) {
        if (getLogInfo(logInfo.getUsername()) == null) {
            logInfo.save();
        } else {
            logInfo.updateAll("username = ?", logInfo.getUsername());
        }
    }

    @Override
    public void remove(LogInfo logInfo) {
        DataSupport.deleteAll(LogInfo.class, "username = ?", logInfo.getUsername());
    }

    @Override
    public LogInfo getLogInfo(String username) {
        List<LogInfo> logInfos = DataSupport.where("username = ?", username).find(LogInfo.class);
        if (logInfos == null || logInfos.size() == 0) {
            return null;
        } else {
            return logInfos.get(0);
        }
    }

    @Override
    public List<LogInfo> getAllLogInfos() {
        return DataSupport.findAll(LogInfo.class);
    }

    @Override
    public LogInfo getLastSavedLogInfo() {
        List<LogInfo> logInfos = DataSupport.order("saveTime desc").find(LogInfo.class);
        if (logInfos == null || logInfos.size() == 0) {
            return null;
        } else {
            return logInfos.get(0);
        }
    }
}
