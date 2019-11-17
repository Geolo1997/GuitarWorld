package pers.geolo.guitarworld.entity.event;

import pers.geolo.guitarworld.entity.LogInfo;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/10
 */
public class RegisterSuccessEvent {

    private LogInfo logInfo;

    public RegisterSuccessEvent(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }
}
