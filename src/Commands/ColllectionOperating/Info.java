package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;
import CollectionManager.CollectionManager;

import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * Класс Info реализует команду для вывода информации о коллекции.
 * Команда предоставляет данные о типе коллекции, дате инициализации и количестве элементов.
 * Является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Info implements CommandInterface {
    /** Название команды. */
    private final String name = "info";

    /** Описание команды. */
    private final String description = "вывести информацию о коллекции";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Info.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции и её данным.
     */
    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду info.
     * Выводит информацию о коллекции: тип, дату инициализации и количество элементов.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Информация о коллекции:");
        System.out.println("Тип: " + collectionManager.getCollection().getClass().getSimpleName());
        System.out.println("Дата инициализации: " + collectionManager.getInitializationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("Количество элементов: " + collectionManager.getCollection().size());
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("info").
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
