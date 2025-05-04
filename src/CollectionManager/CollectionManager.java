package CollectionManager;

import SpaceMarine.SpaceMarine;
import Database.SpaceMarineDao;
import java.time.LocalDateTime;
import java.util.Vector;

/**
 * Класс CollectionManager управляет коллекцией объектов SpaceMarine.
 * Отвечает за загрузку данных из базы данных, хранение коллекции и предоставление информации о ней.
 * Коллекция инициализируется из базы данных через SpaceMarineDao.
 *
 * @author Андрей
 * @version 1.3
 * @since 2025-04-28
 */
public class CollectionManager {
    private final Vector<SpaceMarine> collection = new Vector<>();
    private final LocalDateTime initializationDate;
    private final SpaceMarineDao spaceMarineDao;

    public CollectionManager(SpaceMarineDao spaceMarineDao) {
        this.initializationDate = LocalDateTime.now();
        this.spaceMarineDao = spaceMarineDao;

        // Загружаем коллекцию из базы данных
        Vector<SpaceMarine> loadedCollection = spaceMarineDao.loadCollection();
        collection.addAll(loadedCollection);
        System.out.println("Коллекция загружена из базы данных. Количество элементов: " + collection.size());
    }

    public Vector<SpaceMarine> getCollection() {
        return collection;
    }

    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public SpaceMarineDao getSpaceMarineDao() {
        return spaceMarineDao;
    }
}