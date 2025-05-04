package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;
import CollectionManager.CollectionManager;
import SpaceMarine.SpaceMarine;
import SpaceMarine.Chapter;

import java.util.Vector;

/**
 * Класс CountGreaterThanChapter реализует команду для подсчёта элементов коллекции,
 * у которых поле chapter больше заданного значения.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class CountGreaterThanChapter implements CommandInterface {
    /** Название команды. */
    private final String name = "count_greater_than_chapter";

    /** Описание команды. */
    private final String description = "подсчитать элементы с chapter больше заданного";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса CountGreaterThanChapter.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public CountGreaterThanChapter(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду count_greater_than_chapter.
     * Подсчитывает количество элементов коллекции, у которых поле chapter больше заданного имени.
     * Если аргументов недостаточно, выводит сообщение об использовании команды.
     *
     * @param args Аргументы команды, где args[0] — имя chapter для сравнения.
     *             Если args пустой, выводится сообщение об ошибке.
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: count_greater_than_chapter chapter");
            return;
        }
        int i = 0;
        int count = Integer.parseInt(args[0]);
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        for (SpaceMarine marine : collection) {
            if (marine.getChapter().getMarinesCount() > count) {
                i++;
            }
        }
        System.out.println("Количество элементов с chapter > " + count + ": " + i);
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("count_greater_than_chapter").
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
