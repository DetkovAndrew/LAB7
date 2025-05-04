package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к PostgreSQL на Helios.
 */
public class DatabaseConnection {
    private static final String HOST = "pg";
    private static final String PORT = "5432";
    private static final String USER = "s465750";
    private static final String PASSWORD = "lW5oSgCfWEfZdJ3p";
    private static final String DB_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/studs";

    private static Connection connection;

    public static void initialize() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить драйвер PostgreSQL: " + e.getMessage(), e);
        }
    }

    public static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("Подключился к БД");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Не удалось подключиться к базе данных", e);
        }
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Отключился от БД");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка отключения от БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        connect();
        return connection;
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
