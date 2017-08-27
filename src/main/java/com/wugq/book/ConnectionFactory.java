package com.wugq.book;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by wuguoquan on 7/21/17.
 */
public class ConnectionFactory {

    // 下面的四个成员变量用于保存从数据库中读取的配置信息
    private static String driver = "com.mysql.jdbc.Driver";
    private static String dburl = "jdbc:mysql://localhost:3306/simpleBook";
    private static String user = "root";
    private static String password = "wugq901216";

    private static ConnectionFactory mConnectionFactory = new ConnectionFactory();

    private ConnectionFactory() {

    }

    //获取ConnectionFactory单例
    public static ConnectionFactory getInstance() {
        return mConnectionFactory;
    }

    private Connection mConnection;

    public Connection makeConnection(){

        try {
            Class.forName(driver);
            mConnection = DriverManager.getConnection(dburl, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mConnection;
    }

}
