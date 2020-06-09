/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package live.sidian.database.autoddl.utils;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SqlUtil {
    private static Connection connection;

    /**
     * 打开连接
     */
    public static void open(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭连接
     */
    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    /**
     * 执行查询语句
     */
    public static ResultSet querySQL(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * 执行数据定义语句
     */
    public static void executeDDL(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    /**
     * 获取属性的字符串格式
     */
    public static String getDBString(String s) {
        return "'" + s.replaceAll("'", "''") + "'";
    }
}
