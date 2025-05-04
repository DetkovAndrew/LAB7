package Commands.ElementsOuterOperating;

import CollectionManager.CollectionManager;
import Commands.CommandInterface.CommandInterface;
import SpaceMarine.SpaceMarine;
import SpaceMarine.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Класс Add реализует команду для добавления нового элемента в коллекцию объектов SpaceMarine.
 * Команда поддерживает два режима ввода: интерактивный (через консоль) и через аргументы в формате CSV.
 * CSV-строка должна иметь следующий формат:
 * <pre>
 * id,name,coordinates,creationDate,health,achievements,weaponType,meleeWeapon,chapter
 * </pre>
 * где:
 * <ul>
 *   <li><b>coordinates</b> — строка вида "x;y" (координаты x и y, разделённые точкой с запятой)</li>
 *   <li><b>chapter</b> — строка вида "chapterName;parentLegion;marinesCount;world"</li>
 * </ul>
 * При добавлении id из CSV игнорируется, генерируется новый уникальный идентификатор.
 * Если пользователь вводит некорректные данные, выводится сообщение об ошибке и запрашивается повторный ввод.
 *
 * @author Андрей
 * @version 3.0
 * @since 2025-03-10
 */
public class Add implements CommandInterface {
    /**
     * Название команды.
     */
    private final String name = "add";

    /**
     * Описание команды.
     */
    private final String description = "добавить новый элемент в коллекцию";

    /**
     * Экземпляр CollectionManager, используемый для управления коллекцией SpaceMarine.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Add.
     * Инициализирует команду с заданным экземпляром CollectionManager.
     *
     * @param collectionManager объект CollectionManager для работы с коллекцией
     */
    public Add(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     * Делегирует выполнение статическому методу Adding.execute, передавая аргументы и менеджер коллекции.
     * Поддерживает как интерактивный ввод, так и ввод через CSV-строку в аргументах.
     *
     * @param args массив аргументов команды; ожидается либо пустой массив (для интерактивного ввода),
     *             либо массив с одним элементом — CSV-строкой с данными SpaceMarine
     */
    @Override
    public void execute(String[] args) {
        Adding.execute(args, collectionManager);
    }

    /**
     * Возвращает название команды.
     *
     * @return String название команды ("add")
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return String описание команды ("добавить новый элемент в коллекцию")
     */
    @Override
    public String getDescription() {
        return description;
    }
}

