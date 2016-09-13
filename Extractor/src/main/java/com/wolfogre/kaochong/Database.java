package com.wolfogre.kaochong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class Database {
    private Connection connection;

    @Autowired
    Console console;

    @Autowired
    DatetimeUtil datetimeUtil;

    @PostConstruct
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        console.writeLine("Please input the path of sqlite file to load or to create:");
        String filePath = console.readLine();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS cource;" +
                    "CREATE TABLE cource" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " startTime DATE NOT NULL, " +
                    " timeRange TEXT NOT NULL, " +
                    " name TEXT NOT NULL, " +
                    " teacher TEXT NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertCourse(String startTime, String timeRange, String name, String teacher) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO cource (startTime, timeRange, name, teacher) VALUES" +
                    "(" + datetimeUtil.getDate(startTime).getTime() + ",'" + timeRange + "','" + name + "','" + teacher + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
