package com.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    public static String driverName = "com.mysql.jdbc.Driver";
    public static String userName = "root";
    public static String password = "cftl2018";
    public static String url = "jdbc:mysql://123.207.148.155:3306/Chat";
    public static DataSource dataSource = null;

    /**
     *
     * @return 返回一个数据库的connection
     * @throws SQLException
     * 数据库链接失败
     * @throws ClassNotFoundException
     * 数据库Driver加载失败
     */
    public static Connection getConnecttion() throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection(url,userName,password);
        return conn;
    }
}