package CollectionManager;

import Database.DatabaseConnection;
import Database.SpaceMarineDao;

/**
 * Класс ManagerContainer служит контейнером для экземпляра CollectionManager.
 * Обеспечивает статическую инициализацию коллекции SpaceMarine и предоставляет доступ к ней через геттер.
 * Используется для управления единственным экземпляром CollectionManager в приложении.
 *
 * @author Андрей
 * @version 1.1
 * @since 2025-04-28
 */
public class ManagerContainer {
    /**
     * Единственный экземпляр CollectionManager, инициализируемый статически.
     */
    private static CollectionManager collectionManager;

    /**
     * Статический блок инициализации.
     * Создаёт экземпляр CollectionManager при загрузке класса, используя SpaceMarineDao для работы с базой данных.
     * Если возникают ошибки при инициализации, выбрасывает RuntimeException.
     */
    static {
        try {
            // Инициализация подключения к базе данных (если ещё не инициализировано)
            DatabaseConnection.initialize();
            DatabaseConnection.connect();

            // Создаём SpaceMarineDao
            SpaceMarineDao spaceMarineDao = new SpaceMarineDao(DatabaseConnection.getConnection());

            // Создаём CollectionManager с SpaceMarineDao
            collectionManager = new CollectionManager(spaceMarineDao);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации CollectionManager: " + e.getMessage(), e);
        }
    }

    /**
     * Конструктор класса ManagerContainer.
     * Пустой конструктор, не выполняет инициализацию, так как она происходит статически.
     * Оставлен для возможного будущего расширения функциональности.
     */
    public ManagerContainer() {
    }

    /**
     * Возвращает статически инициализированный экземпляр CollectionManager.
     *
     * @return CollectionManager объект, управляющий коллекцией SpaceMarine
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
