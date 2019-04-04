package pers.geolo.guitarworld.ui.base;

import pers.geolo.guitarworld.entity.LogInfo;

public class CustomContext {

    private static final CustomContext INSTANCE = new CustomContext();

    private CustomContext() {
    }

    public static CustomContext getInstance() {
        return INSTANCE;
    }

    LogInfo logInfo;

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }
}
