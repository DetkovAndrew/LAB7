package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;
import CollectionManager.CollectionManager;
import SpaceMarine.SpaceMarine;

import java.util.Vector;

/**
 * Класс Clear реализует команду для очистки коллекции объектов SpaceMarine.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Clear implements CommandInterface {
    /** Название команды. */
    private final String name = "clear";

    /** Описание команды. */
    private final String description = "очистить коллекцию";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Clear.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду clear.
     * Очищает коллекцию объектов SpaceMarine и выводит сообщение об успешном выполнении.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        collection.clear();
        System.out.println("Коллекция очищена.");
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("clear").
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