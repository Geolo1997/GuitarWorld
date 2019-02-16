package pers.geolo.guitarworld.dao;

import pers.geolo.guitarworld.dao.impl.UserDAOImpl;

public class DAOFactory {

    public <T> T getDAO(Class<T> iDAO) {
        if (iDAO == UserDAO.class) {
            return (T) new UserDAOImpl();
        }
        try {
            return iDAO.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
