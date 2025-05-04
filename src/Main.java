import Commands.CommandInterface.CommandInterface;
import Commands.Register.CommandsRegister;
import CollectionManager.CollectionManager;
import Database.DatabaseConnection;
import Database.UserDao;
import SpaceMarine.SpaceMarine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Главный класс программы, обеспечивающий запуск и управление коллекцией объектов SpaceMarine.
 * Инициализирует CollectionManager, регистрирует команды и обрабатывает консольный ввод для выполнения команд.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Main {
    private static Integer currentUserId = null;
    private static BufferedReader globalReader;

    /**
     * Читает строку из консоли с помощью глобального BufferedReader.
     *
     * @param prompt Сообщение для пользователя.
     * @return Прочитанная строка или null в случае ошибки.
     */
    private static String readLine(String prompt) {
        System.out.println(prompt);
        try {
            return globalReader.readLine();
        } catch (Exception e) {
            System.err.println("Ошибка чтения строки: " + e.getMessage());
            return null;
        }
    }

    /**
     * Читает пароль из консоли, скрывая ввод (без отображения символов).
     *
     * @param prompt Сообщение для пользователя.
     * @return Прочитанная строка пароля или null в случае ошибки.
     */
    private static String readPassword(String prompt) {
        System.out.println(prompt);
        try {
            // Используем Console для скрытого ввода, если доступен
            java.io.Console console = System.console();
            if (console != null) {
                char[] passwordArray = console.readPassword();
                return passwordArray != null ? new String(passwordArray) : null;
            } else {
                // Если Console недоступен (например, в IDE), используем BufferedReader
                return globalReader.readLine();
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения пароля: " + e.getMessage());
            return null;
        }
    }

    public static Integer getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Точка входа в программу.
     * Инициализирует менеджер коллекции, регистрирует команды, запускает консольный цикл для выполнения команд.
     *
     * @param args Аргументы командной строки (не используются в данной программе).
     */
    public static void main(String[] args) {
        // Инициализируем глобальный BufferedReader
        globalReader = new BufferedReader(new InputStreamReader(System.in));

        // Проверяем подключение к базе данных
        DatabaseConnection.initialize();
        UserDao userDao;
        try {
            userDao = new UserDao(DatabaseConnection.getConnection());
        } catch (Exception e) {
            System.err.println("Не удалось подключиться к базе данных. Пожалуйста, проверьте настройки сервера и попробуйте снова.");
            try {
                globalReader.close();
            } catch (Exception closeException) {
                System.err.println("Ошибка закрытия globalReader: " + closeException.getMessage());
            }
            return;
        }

        // Регистрация всех команд
        CommandsRegister.registerCommands();

        // Получаем CollectionManager из CommandsRegister
        CollectionManager collectionManager = CommandsRegister.getCollectionManager();

        // Получение карты команд из реестра
        Map<String, CommandInterface> commands = CommandsRegister.getCommands();

        // Запуск консольного ввода
        System.out.println("Программа запущена.");

        while (true) {
            String input = readLine("Введите команду (или 'login' для входа, 'register' для регистрации):");
            if (input == null) {
                System.err.println("Не удалось прочитать команду. Завершение программы.");
                DatabaseConnection.disconnect();
                try {
                    globalReader.close();
                } catch (Exception e) {
                    System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                }
                return;
            }
            input = input.trim();

            if (input.isEmpty()) {
                continue;
            }

            // Разбиваем ввод на команду и аргументы
            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0].toLowerCase();
            String[] commandArgs = parts.length > 1 ? new String[]{parts[1]} : new String[]{};

            // Обработка логина и регистрации
            if (currentUserId == null && (commandName.equals("login") || commandName.equals("register"))) {
                if (commandName.equals("login")) {
                    String username = readLine("Введите логин:");
                    if (username == null) {
                        System.err.println("Не удалось прочитать логин. Завершение программы.");
                        DatabaseConnection.disconnect();
                        try {
                            globalReader.close();
                        } catch (Exception e) {
                            System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                        }
                        return;
                    }
                    username = username.trim();

                    while (true) {
                        String password = readPassword("Введите пароль:");
                        if (password == null) {
                            System.err.println("Не удалось прочитать пароль. Завершение программы.");
                            DatabaseConnection.disconnect();
                            try {
                                globalReader.close();
                            } catch (Exception e) {
                                System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                            }
                            return;
                        }
                        password = password.trim();

                        try {
                            Integer userId = userDao.login(username, password);
                            if (userId == null) {
                                System.out.println("Пользователь с логином " + username + " не найден.");
                                break;
                            } else if (userId == -1) {
                                System.out.println("Неверный пароль для пользователя " + username + ".");
                                String retry = readLine("Попробовать снова? (да/нет):");
                                if (retry != null && retry.trim().toLowerCase().equals("нет")) {
                                    break;
                                }
                            } else {
                                currentUserId = userId;
                                System.out.println("Вход успешен. Ваш ID: " + currentUserId);
                                break;
                            }
                        } catch (Exception e) {
                            System.err.println("Ошибка входа: база данных недоступна. Пожалуйста, проверьте подключение.");
                            break;
                        }
                    }
                } else if (commandName.equals("register")) {
                    String username = readLine("Введите логин:");
                    if (username == null) {
                        System.err.println("Не удалось прочитать логин. Завершение программы.");
                        DatabaseConnection.disconnect();
                        try {
                            globalReader.close();
                        } catch (Exception e) {
                            System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                        }
                        return;
                    }
                    username = username.trim();

                    String password = readPassword("Введите пароль:");
                    if (password == null) {
                        System.err.println("Не удалось прочитать пароль. Завершение программы.");
                        DatabaseConnection.disconnect();
                        try {
                            globalReader.close();
                        } catch (Exception e) {
                            System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                        }
                        return;
                    }
                    password = password.trim();

                    while (true) {
                        String confirmPassword = readPassword("Подтвердите пароль:");
                        if (confirmPassword == null) {
                            System.err.println("Не удалось прочитать подтверждение пароля. Завершение программы.");
                            DatabaseConnection.disconnect();
                            try {
                                globalReader.close();
                            } catch (Exception e) {
                                System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                            }
                            return;
                        }
                        confirmPassword = confirmPassword.trim();

                        if (!password.equals(confirmPassword)) {
                            System.out.println("Пароли не совпадают. Попробуйте снова (или введите 'no' для отмены):");
                            String retry = readLine("");
                            if (retry != null && retry.trim().toLowerCase().equals("no")) {
                                break;
                            }
                            continue;
                        }

                        try {
                            userDao.register(username, password);
                            System.out.println("Регистрация успешна. Войдите с новыми данными.");
                            break;
                        } catch (Exception e) {
                            System.err.println("Ошибка регистрации: база данных недоступна или пользователь с таким именем уже существует.");
                            break;
                        }
                    }
                }
                continue;
            }

            // Выполнение команд
            try {
                // Команды, доступные всем
                if (commandName.equals("show")) {
                    if (collectionManager.getCollection().isEmpty()) {
                        System.out.println("Коллекция пуста");
                    } else {
                        System.out.println("id,name,coordinates_x,coordinates_y,creation_date,health,achievements,weapon_type,melee_weapon,chapter_name,chapter_parent_legion,chapter_marines_count,chapter_world,owner_id");
                        for (var marine : collectionManager.getCollection()) {
                            System.out.println(marine.toCSV());
                        }
                    }
                } else if (commandName.equals("info")) {
                    System.out.println("Тип коллекции: Vector");
                    System.out.println("Дата инициализации: " + java.time.LocalDateTime.now());
                    System.out.println("Количество элементов: " + collectionManager.getCollection().size());
                } else if (commandName.equals("help")) {
                    System.out.println("Доступные команды:");
                    System.out.println("  average_of_health - среднее значение здоровья");
                    System.out.println("  count_greater_than_chapter <chapter_name> - количество маринов с главой больше указанной");
                    System.out.println("  filter_less_than_weapon_type <weapon_type> - фильтр по типу оружия (меньше указанного)");
                    System.out.println("  info - информация о коллекции");
                    System.out.println("  save - сохранить коллекцию в базу данных");
                    System.out.println("  show - показать коллекцию");
                    System.out.println("  help - показать справку");
                    System.out.println("  exit - выйти из программы");
                    if (currentUserId != null) {
                        System.out.println("  logout - выйти из системы");
                        System.out.println("  add <csv_data> - добавить элемент");
                        System.out.println("  add_if_min <csv_data> - добавить элемент, если его здоровье меньше минимального");
                        System.out.println("  adding <csv_data> - добавить элемент (альтернатива add)");
                        System.out.println("  insert_at <index> <csv_data> - вставить элемент по индексу");
                        System.out.println("  update_id <id> <csv_data> - обновить элемент по ID");
                        System.out.println("  remove_by_id <id> - удалить элемент по ID");
                        System.out.println("  remove_last - удалить последний элемент (если он ваш)");
                        System.out.println("  clear - очистить коллекцию (только свои элементы)");
                        System.out.println("  execute_script <filename> - выполнить скрипт из файла");
                    }
                } else if (commandName.equals("exit")) {
                    DatabaseConnection.disconnect();
                    try {
                        globalReader.close();
                    } catch (Exception e) {
                        System.err.println("Ошибка закрытия globalReader: " + e.getMessage());
                    }
                    System.out.println("Программа завершена");
                    return;
                } else if (commandName.equals("average_of_health") || commandName.equals("count_greater_than_chapter") ||
                        commandName.equals("filter_less_than_weapon_type") || commandName.equals("save")) {
                    CommandInterface command = commands.get(commandName);
                    if (command != null) {
                        command.execute(commandArgs);
                    } else {
                        System.err.println("Команда не найдена: " + commandName);
                    }
                } else if (currentUserId == null) {
                    System.out.println("Команда недоступна без логина: " + commandName + ". Доступные команды: average_of_health, count_greater_than_chapter, filter_less_than_weapon_type, info, save, show, help, exit, login, register");
                } else if (commandName.equals("logout")) {
                    currentUserId = null;
                    System.out.println("Выход выполнен");
                } else if (commandName.equals("remove_by_id") || commandName.equals("update_id")) {
                    int id = -1;
                    // Если аргументы есть, извлекаем ID
                    if (commandArgs.length > 0) {
                        String[] idParts = commandArgs[0].split("\\s+", 2);
                        try {
                            id = Integer.parseInt(idParts[0]);
                        } catch (NumberFormatException e) {
                            System.err.println("ID должен быть числом: " + idParts[0]);
                            continue;
                        }
                    } else {
                        // Интерактивный режим: запрашиваем ID
                        String idInput = readLine("Введите id для " + (commandName.equals("remove_by_id") ? "удаления" : "обновления") + " (целое число):");
                        if (idInput == null) {
                            System.err.println("Не удалось прочитать ID.");
                            continue;
                        }
                        idInput = idInput.trim();
                        try {
                            id = Integer.parseInt(idInput);
                        } catch (NumberFormatException e) {
                            System.err.println("ID должен быть числом: " + idInput);
                            continue;
                        }
                        commandArgs = new String[]{String.valueOf(id)};
                    }
                    // Проверяем права доступа
                    int finalId = id;
                    SpaceMarine targetMarine = collectionManager.getCollection().stream()
                            .filter(m -> m.getId() == finalId)
                            .findFirst()
                            .orElse(null);
                    if (targetMarine == null) {
                        System.err.println("Элемент с ID " + id + " не найден");
                        continue;
                    }
                    if (!targetMarine.getOwnerId().equals(currentUserId)) {
                        System.err.println("У вас нет прав для изменения элемента с ID " + id);
                        continue;
                    }
                    // Передаём выполнение команде
                    CommandInterface command = commands.get(commandName);
                    if (command != null) {
                        command.execute(commandArgs);
                    } else {
                        System.err.println("Команда не найдена: " + commandName);
                    }
                } else if (commandName.equals("remove_last")) {
                    // Проверяем права для последнего элемента
                    if (collectionManager.getCollection().isEmpty()) {
                        System.err.println("Коллекция пуста");
                        continue;
                    }
                    SpaceMarine lastMarine = collectionManager.getCollection().get(collectionManager.getCollection().size() - 1);
                    if (!lastMarine.getOwnerId().equals(currentUserId)) {
                        System.err.println("У вас нет прав для удаления последнего элемента");
                        continue;
                    }
                    CommandInterface command = commands.get(commandName);
                    if (command != null) {
                        command.execute(commandArgs);
                    } else {
                        System.err.println("Команда не найдена: " + commandName);
                    }
                } else if (commandName.equals("clear")) {
                    // Для clear удаляем только свои элементы
                    collectionManager.getCollection().removeIf(marine -> marine.getOwnerId() != null && marine.getOwnerId().equals(currentUserId));
                    System.out.println("Удалены все ваши элементы из коллекции");
                } else {
                    // Для остальных команд просто выполняем
                    CommandInterface command = commands.get(commandName);
                    if (command != null) {
                        command.execute(commandArgs);
                    } else {
                        System.err.println("Команда не найдена: " + commandName);
                    }
                }
            } catch (Exception e) {
                System.err.println("Ошибка выполнения команды: " + e.getMessage());
            }
        }
    }
}