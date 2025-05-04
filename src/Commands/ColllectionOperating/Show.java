package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;
import SpaceMarine.SpaceMarine;
import CollectionManager.CollectionManager;
import java.util.Vector;

/**
 * Класс Show реализует команду для вывода всех элементов коллекции объектов SpaceMarine.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 * Выводит элементы коллекции или сообщение о пустой коллекции.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Show implements CommandInterface {
    /** Название команды. */
    private final String name = "show";

    /** Описание команды. */
    private final String description = "вывести все элементы коллекции";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Show.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду show.
     * Выводит все элементы коллекции SpaceMarine.
     * Если коллекция пуста, выводит соответствующее сообщение.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            for (SpaceMarine marine : collection) {
                System.out.println(marine);
            }
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("show").
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
