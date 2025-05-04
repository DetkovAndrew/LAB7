package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;
import SpaceMarine.SpaceMarine;
import CollectionManager.CollectionManager;
import Database.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс Save реализует команду для сохранения коллекции объектов SpaceMarine в базу данных.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 * Сохранение выполняется в таблицу space_marines через SpaceMarineDao, используя sequence для генерации id.
 *
 * @author Андрей
 * @version 1.2
 * @since 2025-04-28
 */
public class Save implements CommandInterface {
    /** Название команды. */
    private final String name = "save";

    /** Описание команды. */
    private final String description = "сохранить коллекцию в базу данных";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Save.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду save.
     * Сохраняет коллекцию объектов SpaceMarine в базу данных.
     * Сначала очищает таблицу space_marines, сбрасывает sequence, затем добавляет все элементы из коллекции,
     * позволяя базе данных генерировать id через sequence.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        try {
            // Очищаем таблицу space_marines
            collectionManager.getSpaceMarineDao().clear(-1); // Очищаем все записи

            // Сбрасываем sequence, чтобы id начинался с 1
            String resetSequenceQuery = "ALTER SEQUENCE s465750.space_marines_id_seq RESTART WITH 1";
            try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
                stmt.executeUpdate(resetSequenceQuery);
            }

            // Сохраняем все элементы коллекции в базу данных
            for (SpaceMarine marine : collectionManager.getCollection()) {
                // Сбрасываем id, чтобы база данных сгенерировала новый
                marine.setId(0);
                collectionManager.getSpaceMarineDao().add(marine);
                // После вызова add id уже обновлён в объекте marine
            }
            System.out.println("Коллекция сохранена в базу данных. Количество элементов: " + collectionManager.getCollection().size());
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении в базу данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("save").
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return Описание команды.
     */
    @Override
    public String getDescription() {
        return description;
    }
}
