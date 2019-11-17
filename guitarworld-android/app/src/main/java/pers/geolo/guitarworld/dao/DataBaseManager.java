package pers.geolo.guitarworld.dao;

import pers.geolo.guitarworld.dao.impl.LogInfoDAOImpl;
import pers.geolo.util.SingletonHolder;

public class DataBaseManager {

    public static LogInfoDAO getLogInfoDAO() {
        return SingletonHolder.getInstance(LogInfoDAOImpl.class);
    }
}
