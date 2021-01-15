package jm.task.core.jdbc.util;


import com.mysql.cj.jdbc.MysqlDataSource;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test_users?serverTimezone=Europe/Moscow";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final SessionFactory SESSION_FACTORY;

    static {
        SESSION_FACTORY = new Configuration()
                .addProperties(getHibernateProperties())
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private Util() {
    }

    public static Connection getConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException cause) {
            throw new RuntimeException("", cause);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void closeSessionFactory() {
        if (SESSION_FACTORY != null) {
            SESSION_FACTORY.close();
        }
    }

    private static Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", DRIVER_CLASS_NAME);
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", USERNAME);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.connection.pool_size", 8);
        properties.put("hibernate.current_session_context_class", "thread");
        return properties;
    }
}
