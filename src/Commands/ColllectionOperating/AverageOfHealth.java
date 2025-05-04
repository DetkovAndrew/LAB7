package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;

import CollectionManager.CollectionManager;
import SpaceMarine.SpaceMarine;
import java.util.Vector;

/**
 * Класс AverageOfHealth реализует команду для вычисления среднего значения поля health
 * всех элементов коллекции SpaceMarine.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class AverageOfHealth implements CommandInterface {
    /** Название команды. */
    private final String name = "average_of_health";

    /** Описание команды. */
    private final String description = "вывести среднее значение поля health для всех элементов коллекции";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса AverageOfHealth.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public AverageOfHealth(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду average_of_health.
     * Вычисляет среднее значение поля health для всех элементов коллекции.
     * Если коллекция пуста или ни у одного элемента нет ненулевого health, выводит 0.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        float sum = 0;
        int count = 0;
        for (SpaceMarine marine : collection) {
            if (marine.getHealth() != 0) {
                sum += marine.getHealth();
                count++;
            }
        }
        System.out.println("Среднее значение health: " + (count > 0 ? sum / count : 0));
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("average_of_health").
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
