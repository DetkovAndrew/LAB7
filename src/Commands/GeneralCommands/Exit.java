package Commands.GeneralCommands;

import Commands.CommandInterface.CommandInterface;

/**
 * Класс Exit реализует команду для выхода из консольного приложения.
 * Команда является частью системы управления работы консольного приложения и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Exit implements CommandInterface {
    /** Название команды. */
    private final String name = "exit";

    /** Описание команды. */
    private final String description = "завершить программу";

    /**
     * Выполняет команду exit.
     * Производит выход из консольного приложения.
     *
     * @param args Аргументы команды (не используются в данной команде).
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Программа завершена.");
        System.exit(0);
    }

    /**
     * Возвращает название команды.
     *
     * @return Название команды ("exit").
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
