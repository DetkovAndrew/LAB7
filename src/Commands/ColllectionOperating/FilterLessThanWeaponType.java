package Commands.ColllectionOperating;

import Commands.CommandInterface.CommandInterface;

import CollectionManager.CollectionManager;
import SpaceMarine.SpaceMarine;
import SpaceMarine.Weapon;
import java.util.Vector;

/**
 * Класс FilterLessThanWeaponType реализует команду для фильтрации элементов коллекции,
 * у которых поле weaponType меньше заданного значения.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class FilterLessThanWeaponType implements CommandInterface {
    /** Название команды. */
    private final String name = "filter_less_than_weapon_type";

    /** Описание команды. */
    private final String description = "вывести элементы с weaponType меньше заданного";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса FilterLessThanWeaponType.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public FilterLessThanWeaponType(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду filter_less_than_weapon_type.
     * Выводит элементы коллекции, у которых поле weaponType меньше заданного значения.
     * Если аргументов недостаточно или тип оружия недопустим, выводит сообщение об ошибке.
     *
     * @param args Аргументы команды, где args[0] — тип оружия (например, PLASMA_GUN).
     *             Если args пустой, выводится сообщение об использовании команды.
     * @throws IllegalArgumentException если передан недопустимый тип оружия
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: filter_less_than_weapon_type weapon");
            return;
        }
        int count = Integer.parseInt(args[0]);
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        try {
            for (SpaceMarine marine : collection) {
                if (marine.getWeaponType().getValue() < count) {
                    System.out.println(marine);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Недопустимый тип оружия: " + args[0]);
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("filter_less_than_weapon_type").
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