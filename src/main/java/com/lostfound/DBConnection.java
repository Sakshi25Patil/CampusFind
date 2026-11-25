
package com.lostfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static Map<String, String> loadEnv() {

        Map<String, String> config = new HashMap<>();

        try {
            for (String line : Files.readAllLines(Path.of(".env"))) {

                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read .env file. Make sure it exists in the project root.", e
            );
        }

        return config;
    }

    public static Connection getConnection() {

        Map<String, String> config = loadEnv();

        String url = config.get("DB_URL");
        String user = config.get("DB_USER");
        String password = config.get("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new RuntimeException("Database configuration is missing in .env");
        }

        try {
            Connection connection = DriverManager.getConnection(
                    url, user, password
            );

            System.out.println("MySQL Connected Successfully!");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();

            return null;
        }
    }
}
