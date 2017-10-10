package uk.co.squarecloud.sqlidemos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Database() {
        conn = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:mem:test;TRACE_LEVEL_SYSTEM_OUT=3;INIT=RUNSCRIPT FROM 'classpath:scripts/create.sql'",
                    "sa",
                    "");
        } catch (SQLException e) {
            System.out.println("Failed to open database: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Connected to database");
    }

    public Connection getConnection() {
        return conn;
    }
}
