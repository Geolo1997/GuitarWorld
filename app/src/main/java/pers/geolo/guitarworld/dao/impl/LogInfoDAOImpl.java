package pers.geolo.guitarworld.dao.impl;

import java.util.List;
import org.litepal.LitePal;

import pers.geolo.guitarworld.dao.LogInfoDAO;
import pers.geolo.guitarworld.entity.LogInfo;

public class LogInfoDAOImpl implements LogInfoDAO {

    public LogInfoDAOImpl() {
        LitePal.getDatabase();
    }

    @Override
    public void add(LogInfo logInfo) {
        logInfo.save();
    }

    @Override
    public void update(LogInfo logInfo) {
        if (!logInfo.isSavePassword()) {
            logInfo.setToDefault("savePassword");
        }
        if (!logInfo.isAutoLogin()) {
            logInfo.setToDefault("autoLogin");
        }
        logInfo.updateAll("username = ?", logInfo.getUsername());
    }

    @Override
    public void remove(LogInfo logInfo) {
        LitePal.deleteAll(LogInfo.class, "username = ?", logInfo.getUsername());
    }

    @Override
    public LogInfo getLogInfo(String username) {
        List<LogInfo> logInfos = LitePal.where("username = ?", username).find(LogInfo.class);
        if (logInfos == null || logInfos.size() == 0) {
            return null;
        } else {
            return logInfos.get(0);
        }
    }

    @Override
    public List<LogInfo> getAllLogInfos() {
        return LitePal.findAll(LogInfo.class);
    }

    @Override
    public LogInfo getLastSavedLogInfo() {
        List<LogInfo> logInfos = LitePal.order("saveTime desc").find(LogInfo.class);
        if (logInfos == null || logInfos.size() == 0) {
            return null;
        } else {
            return logInfos.get(0);
        }
    }
}
