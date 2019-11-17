package pers.geolo.guitarworld.dao;

import java.util.List;

import pers.geolo.guitarworld.entity.LogInfo;

public interface LogInfoDAO {

    void add(LogInfo logInfo);

    void update(LogInfo logInfo);

    void remove(LogInfo logInfo);

    LogInfo getLogInfo(String username);

    List<LogInfo> getAllLogInfos();

    LogInfo getLastSavedLogInfo();
}
