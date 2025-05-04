package Commands.Register;

import CollectionManager.CollectionManager;
import CollectionManager.ManagerContainer;
import Commands.ColllectionOperating.*;
import Commands.CommandInterface.CommandInterface;
import Commands.ElementsInnerOperating.UpdateId;
import Commands.ElementsOuterOperating.*;
import Commands.ScriptOperating.ExecuteScript;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс CommandsRegister отвечает за регистрацию всех доступных команд в системе.
 * Содержит карту команд и менеджер коллекции, который передаётся в каждую команду при её создании.
 * Получает CollectionManager из ManagerContainer.
 *
 * @author Андрей
 * @version 1.2
 * @since 2025-04-28
 */
public class CommandsRegister {
    private static final Map<String, CommandInterface> commands = new HashMap<>();
    private static CollectionManager collectionManager;

    /**
     * Регистрирует все команды, доступные в системе.
     * Получает CollectionManager из ManagerContainer и использует его для создания команд.
     */
    public static void registerCommands() {
        // Получаем CollectionManager из ManagerContainer
        collectionManager = new ManagerContainer().getCollectionManager();

        // Регистрируем команды, доступные всем
        commands.put("average_of_health", new AverageOfHealth(collectionManager));
        commands.put("count_greater_than_chapter", new CountGreaterThanChapter(collectionManager));
        commands.put("filter_less_than_weapon_type", new FilterLessThanWeaponType(collectionManager));
        commands.put("save", new Save(collectionManager));

        // Регистрируем команды, доступные после логина
        commands.put("add", new Add(collectionManager));
        commands.put("add_if_min", new AddIfMin(collectionManager));
        commands.put("insert_at", new InsertAtIndex(collectionManager));
        commands.put("update_id", new UpdateId(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("remove_last", new RemoveLast(collectionManager));
        commands.put("execute_script", new ExecuteScript(commands));;
    }

    /**
     * Возвращает карту зарегистрированных команд.
     *
     * @return Map<String, CommandInterface> Карта команд, где ключ — название команды, значение — объект команды.
     */
    public static Map<String, CommandInterface> getCommands() {
        return commands;
    }

    /**
     * Возвращает менеджер коллекции, используемый командами.
     *
     * @return CollectionManager Менеджер коллекции.
     */
    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }
}