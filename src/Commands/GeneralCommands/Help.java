package Commands.GeneralCommands;

import Commands.CommandInterface.CommandInterface;

import java.util.Map;

/**
 * Класс Help реализует команду для вывода справки о доступных командах.
 * Команда является частью системы управления работы консольного приложения и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Help implements CommandInterface {
    /** Название команды. */
    private final String name = "help";

    /** Описание команды. */
    private final String description = "вывести справку по доступным командам";

    /** Описание команды. */
    private final Map<String, CommandInterface> commands;

    /**
     * Конструктор класса Add.
     *
     * @param commands Карта доступных команд.
     */
    public Help(Map<String, CommandInterface> commands) {
        this.commands = commands;
    }

    /**
     * Выполняет команду Help.
     * Производит вывод информации о доступных командах.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Доступные команды:");
        for (Map.Entry<String, CommandInterface> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getDescription());
        }
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("help").
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
