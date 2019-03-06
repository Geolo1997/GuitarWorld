package pers.geolo.guitarworld.dao;

import android.util.Log;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.entity.User;

import java.util.List;

public interface LogInfoDAO {

    void save(LogInfo logInfo);

    void remove(LogInfo logInfo);

    LogInfo getLogInfo(String username);

    List<LogInfo> getAllLogInfos();

    LogInfo getLastSavedLogInfo();
}
