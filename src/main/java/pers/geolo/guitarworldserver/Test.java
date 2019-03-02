package pers.geolo.guitarworldserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.geolo.guitarworldserver.dao.UserDAO;
import pers.geolo.guitarworldserver.entity.User;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config/spring-mybatis.xml");
        UserDAO userDAO = context.getBean(UserDAO.class);

//        User user = new User("555", "123456", "geolo1997@163.com");
//        userDAO.add(user);
        User user = userDAO.getUser("Geolo");
        user.setPassword("456565");
        userDAO.update(user);
        user = userDAO.getUser("Geolo");
        System.out.println(user.getUsername() + " " + user.getPassword());
    }
}
