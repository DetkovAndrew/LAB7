package Database;

import SpaceMarine.SpaceMarine;
import SpaceMarine.Coordinates;
import SpaceMarine.Weapon;
import SpaceMarine.MeleeWeapon;
import SpaceMarine.Chapter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;

/**
 * Класс SpaceMarineDao отвечает за операции с таблицей space_marines в базе данных.
 * Предоставляет методы для загрузки, добавления, обновления и удаления объектов SpaceMarine.
 *
 * @author Андрей
 * @version 1.1
 * @since 2025-04-28
 */
public class SpaceMarineDao {
    private final Connection connection;

    public SpaceMarineDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Загружает коллекцию SpaceMarine из базы данных.
     *
     * @return Vector<SpaceMarine> коллекция объектов SpaceMarine
     */
    public Vector<SpaceMarine> loadCollection() {
        Vector<SpaceMarine> collection = new Vector<>();
        String query = "SELECT * FROM s465750.space_marines"; // Добавлена схема
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SpaceMarine marine = new SpaceMarine.Builder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setCoordinates(new Coordinates(rs.getInt("coordinates_x"), rs.getDouble("coordinates_y")))
                        .setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime())
                        .setHealth(rs.getFloat("health"))
                        .setAchievements(rs.getString("achievements"))
                        .setWeaponType(rs.getString("weapon_type") != null ? Weapon.valueOf(rs.getString("weapon_type")) : null)
                        .setMeleeWeapon(MeleeWeapon.valueOf(rs.getString("melee_weapon")))
                        .setChapter(new Chapter(
                                rs.getString("chapter_name"),
                                rs.getString("chapter_parent_legion"),
                                rs.getInt("chapter_marines_count"),
                                rs.getString("chapter_world")
                        ))
                        .setOwnerId(rs.getInt("owner_id"))
                        .build();
                collection.add(marine);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке коллекции из базы данных: " + e.getMessage());
        }
        return collection;
    }

    /**
     * Добавляет объект SpaceMarine в базу данных.
     * Использует sequence для генерации id и возвращает его в объекте.
     *
     * @param marine объект SpaceMarine для добавления
     * @throws SQLException если произошла ошибка при выполнении запроса
     */
    public void add(SpaceMarine marine) throws SQLException {
        String query = "INSERT INTO s465750.space_marines (name, coordinates_x, coordinates_y, creation_date, health, achievements, weapon_type, melee_weapon, chapter_name, chapter_parent_legion, chapter_marines_count, chapter_world, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, marine.getName());
            pstmt.setInt(2, marine.getCoordinates().getX());
            pstmt.setDouble(3, marine.getCoordinates().getY());
            pstmt.setTimestamp(4, Timestamp.valueOf(marine.getCreationDate()));
            pstmt.setFloat(5, marine.getHealth());
            pstmt.setString(6, marine.getAchievements());
            pstmt.setString(7, marine.getWeaponType() != null ? marine.getWeaponType().toString() : null);
            pstmt.setString(8, marine.getMeleeWeapon().toString());
            pstmt.setString(9, marine.getChapter().getName());
            pstmt.setString(10, marine.getChapter().getParentLegion());
            pstmt.setInt(11, marine.getChapter().getMarinesCount());
            pstmt.setString(12, marine.getChapter().getWorld());
            pstmt.setInt(13, marine.getOwnerId());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt("id");
                marine.setId(generatedId);
            }
        }
    }

    /**
     * Обновляет объект SpaceMarine в базе данных по его id.
     *
     * @param marine объект SpaceMarine для обновления
     * @throws SQLException если произошла ошибка при выполнении запроса
     */
    public void update(SpaceMarine marine) throws SQLException {
        String query = "UPDATE s465750.space_marines SET name = ?, coordinates_x = ?, coordinates_y = ?, creation_date = ?, health = ?, achievements = ?, weapon_type = ?, melee_weapon = ?, chapter_name = ?, chapter_parent_legion = ?, chapter_marines_count = ?, chapter_world = ?, owner_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, marine.getName());
            pstmt.setInt(2, marine.getCoordinates().getX());
            pstmt.setDouble(3, marine.getCoordinates().getY());
            pstmt.setTimestamp(4, Timestamp.valueOf(marine.getCreationDate()));
            pstmt.setFloat(5, marine.getHealth());
            pstmt.setString(6, marine.getAchievements());
            pstmt.setString(7, marine.getWeaponType() != null ? marine.getWeaponType().toString() : null);
            pstmt.setString(8, marine.getMeleeWeapon().toString());
            pstmt.setString(9, marine.getChapter().getName());
            pstmt.setString(10, marine.getChapter().getParentLegion());
            pstmt.setInt(11, marine.getChapter().getMarinesCount());
            pstmt.setString(12, marine.getChapter().getWorld());
            pstmt.setInt(13, marine.getOwnerId());
            pstmt.setInt(14, marine.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Удаляет объект SpaceMarine из базы данных по его id.
     *
     * @param id идентификатор объекта для удаления
     * @throws SQLException если произошла ошибка при выполнении запроса
     */
    public void remove(int id) throws SQLException {
        String query = "DELETE FROM s465750.space_marines WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Очищает таблицу space_marines для указанного пользователя.
     * Если userId = -1, очищает всю таблицу.
     *
     * @param userId идентификатор пользователя, чьи записи нужно удалить
     * @throws SQLException если произошла ошибка при выполнении запроса
     */
    public void clear(int userId) throws SQLException {
        String query = userId == -1 ? "DELETE FROM s465750.space_marines" : "DELETE FROM s465750.space_marines WHERE owner_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if (userId != -1) {
                pstmt.setInt(1, userId);
            }
            pstmt.executeUpdate();
        }
    }
}
