package Commands.ScriptOperating;

import Commands.CommandInterface.CommandInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Класс ExecuteScript реализует команду для выполнения скрипта из файла.
 * Команда является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 * Поддерживает указание полного пути к файлу скрипта через аргументы или интерактивный ввод.
 * Обрабатывает рекурсивные вызовы скриптов, пропускает комментарии и пустые строки в скрипте.
 *
 * @author Андрей
 * @version 1.5
 * @since 2025-03-10
 */
public class ExecuteScript implements CommandInterface {
    /**
     * Название команды.
     */
    private final String name = "execute_script";

    /**
     * Описание команды.
     */
    private final String description = "выполнить скрипт из файла";

    /**
     * Карта доступных команд, используемых для выполнения строк скрипта.
     */
    private final Map<String, CommandInterface> commands;

    /**
     * Набор путей к выполняемым скриптам для предотвращения рекурсивных вызовов.
     */
    private static final Set<String> executingScripts = new HashSet<>();

    /**
     * Конструктор класса ExecuteScript.
     * Инициализирует команду с заданной картой доступных команд.
     *
     * @param commands карта, содержащая имена команд и их реализации
     */
    public ExecuteScript(Map<String, CommandInterface> commands) {
        this.commands = commands;
    }

    /**
     * Выполняет команду execute_script.
     * Считывает команды из файла скрипта по указанному пути и исполняет их последовательно.
     * Поддерживает два режима ввода пути:
     * <ul>
     *   <li>Через аргументы (args[0] — полный путь к файлу).</li>
     *   <li>Интерактивно, если аргументы не переданы.</li>
     * </ul>
     * Пропускает пустые строки и строки, начинающиеся с "#", как комментарии.
     * Обрабатывает ошибки доступа к файлу и рекурсивные вызовы скриптов.
     *
     * @param args массив аргументов команды; ожидается полный путь к файлу скрипта как первый элемент,
     *             если массив пуст — путь запрашивается у пользователя
     */
    @Override
    public void execute(String[] args) {
        String filePathStr;
        Scanner scanner = new Scanner(System.in);

        // Получаем путь к файлу
        if (args.length < 1) {
            // Интерактивный ввод, если аргументов нет
            while (true) {
                System.out.print("Введите полный путь к файлу скрипта: ");
                filePathStr = scanner.nextLine().trim();
                if (filePathStr.isEmpty()) {
                    System.out.println("Ошибка: путь не может быть пустым. Попробуйте снова.");
                    continue;
                }
                try {
                    Paths.get(filePathStr); // Проверяем, валиден ли путь
                    break;
                } catch (InvalidPathException e) {
                    System.out.println("Ошибка: введён некорректный путь (" + e.getMessage() + "). Попробуйте снова.");
                }
            }
        } else {
            // Проверяем путь из аргумента
            filePathStr = args[0].trim();
            try {
                Paths.get(filePathStr); // Проверяем валидность пути
            } catch (InvalidPathException e) {
                System.err.println("Ошибка: передан некорректный путь: " + filePathStr + " (" + e.getMessage() + ")");
                return;
            }
        }

        Path filePath = Paths.get(filePathStr);
        File file = new File(filePathStr);

        String absolutePath = filePath.toAbsolutePath().toString();

        // Проверяем рекурсивный вызов
        if (executingScripts.contains(absolutePath)) {
            System.err.println("Ошибка: рекурсивный вызов скрипта " + absolutePath + " запрещён.");
            return;
        }
        executingScripts.add(absolutePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Пропускаем пустые строки и комментарии
                }

                String[] parts = line.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String[] commandArgs = parseCommandArgs(parts);

                CommandInterface command = commands.get(commandName);
                if (command != null) {
                    if (commandName.equals("execute_script")) {
                        String subScriptPath = (commandArgs.length > 0 ? Paths.get(commandArgs[0]) : filePath).toAbsolutePath().toString();
                        if (executingScripts.contains(subScriptPath)) {
                            System.err.println("Ошибка в строке " + lineNumber + ": рекурсивный вызов скрипта " + subScriptPath);
                            continue;
                        }
                    }
                    command.execute(commandArgs);
                } else {
                    System.out.println("Команда не найдена в строке " + lineNumber + ": " + commandName);
                }
            }
            System.out.println("Скрипт выполнен успешно: " + absolutePath);
        } catch (IOException e) {
            if (!file.exists()) {
                System.err.println("Ошибка: файл не найден по пути: " + filePath);
            } else {
                System.err.println("Ошибка: файл " + filePath + " недоступен из-за отсутствия прав на чтение");
            }
        } finally {
            executingScripts.remove(absolutePath);
        }
    }

    /**
     * Парсит аргументы команды из строки скрипта.
     * Разделяет строку на имя команды и аргументы, используя пробелы как разделители.
     *
     * @param parts массив, где parts[0] — имя команды, parts[1] — строка аргументов (если есть)
     * @return массив строк с аргументами команды или пустой массив, если аргументов нет
     */
    private String[] parseCommandArgs(String[] parts) {
        if (parts.length <= 1) {
            return new String[0]; // Возвращаем пустой массив, если аргументов нет
        }
        String argLine = parts[1].trim();
        return argLine.split("\\s+");
    }

    /**
     * Парсит строку с аргументами, разделёнными запятыми, с поддержкой кавычек.
     * Используется для команд, требующих CSV-формата в аргументах.
     *
     * @param argLine строка аргументов, разделённых запятыми, с возможными кавычками
     * @return массив строк, представляющих отдельные аргументы, очищенные от лишних пробелов
     */
    private String[] parseCsvArgs(String argLine) {
        List<String> args = new ArrayList<>();
        StringBuilder currentArg = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < argLine.length(); i++) {
            char c = argLine.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ',' && !inQuotes) {
                if (currentArg.length() > 0) {
                    args.add(currentArg.toString().trim());
                    currentArg = new StringBuilder();
                }
                continue;
            }
            currentArg.append(c);
        }
        if (currentArg.length() > 0) {
            args.add(currentArg.toString().trim());
        }
        return args.toArray(new String[0]);
    }

    /**
     * Возвращает название команды.
     *
     * @return String название команды ("execute_script")
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return String описание команды ("выполнить скрипт из файла")
     */
    @Override
    public String getDescription() {
        return description;
    }
}