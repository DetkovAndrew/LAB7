package Commands.ElementsOuterOperating;

import Commands.CommandInterface.CommandInterface;
import CollectionManager.CollectionManager;
import SpaceMarine.SpaceMarine;

import java.util.Vector;

/**
 * Класс RemoveLast реализует команду для удаления последнего элемента из коллекции объектов SpaceMarine.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class RemoveLast implements CommandInterface {
    /** Название команды. */
    private final String name = "remove_last";

    /** Описание команды. */
    private final String description = "удалить последний элемент";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса RemoveLast.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public RemoveLast(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду remove_last.
     * Удаляет последний элемент из коллекции SpaceMarine.
     * Если коллекция пуста, выводит соответствующее сообщение.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        if (!collection.isEmpty()) {
            collection.remove(collection.size() - 1);
            System.out.println("Последний элемент удалён.");
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("remove_last").
     */
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
