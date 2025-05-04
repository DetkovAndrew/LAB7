package Commands.ElementsOuterOperating;

import CollectionManager.CollectionManager;
import Commands.CommandInterface.CommandInterface;
import SpaceMarine.SpaceMarine;

import java.util.Scanner;
import java.util.Vector;

import static java.lang.Integer.parseInt;

/**
 * Класс RemoveById реализует команду для удаления элемента из коллекции по его идентификатору (id).
 * Команда является частью системы управления коллекцией SpaceMarine и реализует интерфейс CommandInterface.
 * Поддерживает два режима ввода: интерактивный (через консоль) и через аргументы командной строки.
 * После удаления элемента пересчитывает идентификаторы оставшихся элементов для сохранения последовательности.
 *
 * @author Андрей
 * @version 1.1
 * @since 2025-03-10
 */
public class RemoveById implements CommandInterface {
    /**
     * Название команды.
     */
    private final String name = "remove_by_id";

    /**
     * Описание команды.
     */
    private final String description = "удалить элемент по id";

    /**
     * Менеджер коллекции, предоставляющий доступ к коллекции SpaceMarine.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса RemoveById.
     * Инициализирует команду с заданным менеджером коллекции.
     *
     * @param collectionManager менеджер коллекции для работы с объектами SpaceMarine
     */
    public RemoveById(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду remove_by_id.
     * Удаляет элемент из коллекции SpaceMarine по указанному идентификатору (id).
     * Если элемент найден и удалён, пересчитывает id оставшихся элементов, начиная с 1.
     * Поддерживает два режима:
     * <ul>
     *   <li>Интерактивный режим (args пустой): запрашивает id через консоль с проверкой корректности.</li>
     *   <li>Режим аргументов (args содержит id): использует переданный id с проверкой на валидность.</li>
     * </ul>
     *
     * @param args массив аргументов команды; если пустой — запускается интерактивный режим,
     *             если содержит один элемент — ожидается id в виде строки, представляющей целое число
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        int id;

        // Проверка индекса
        if (args.length == 0) {
            // Интерактивный режим: запрашиваем индекс
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Введите id для удаления (целое число от 1 до " + (collection.size()) + "): ");
                try {
                    id = parseInt(scanner.nextLine().trim());
                    if (id < 1 || id > collection.size() + 1) {
                        System.out.println("Недопустимый id: " + id + ". Должен быть от 1 до " + collection.size() + ".");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("id должен быть числом.");
                }
            }
        } else {
            // Режим через аргументы: индекс берём из args[0]
            if (args.length > 1) {
                System.out.println("Использование: remove_by_id id");
                return;
            }
            try {
                id = parseInt(args[0]);
                if (id < 1 || id > collection.size() + 1) {
                    System.out.println("Недопустимый id: " + id);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("id должен быть числом.");
                return;
            }
        }

        // Удаляем элемент
        int finalId = id;
        boolean removed = collection.removeIf(marine -> marine.getId() == finalId);
        if (removed) {
            // Пересчитываем id всех оставшихся элементов
            for (int i = 0; i < collection.size(); i++) {
                collection.get(i).setId(i + 1);
            }
            System.out.println("Элемент с id " + id + " удалён.");
        } else {
            System.out.println("Элемент с id " + id + " не найден.");
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return String название команды ("remove_by_id")
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return String описание команды ("удалить элемент по id")
     */
    @Override
    public String getDescription() {
        return description;
    }
}
