package Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public void register(String username, String password) throws SQLException {
        String passwordHash = hashPassword(password);
        String sql = "INSERT INTO s465750.users (username, password_hash) VALUES (?, ?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Пользователь " + username + " зарегистрирован с ID: " + rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new SQLException("Пользователь с именем " + username + " уже существует");
            }
            throw e;
        }
    }

    public Integer login(String username, String password) throws SQLException {
        String sql = "SELECT id, password_hash FROM s465750.users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    String passwordHash = hashPassword(password);
                    if (storedHash.equals(passwordHash)) {
                        return rs.getInt("id"); // Логин и пароль верны
                    } else {
                        return -1; // Пользователь найден, но пароль неверный
                    }
                }
                return null; // Пользователь не найден
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-224");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Алгоритм SHA-224 не поддерживается", e);
        }
    }
}
