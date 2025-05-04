package Commands.ElementsInnerOperating;

import CollectionManager.CollectionManager;
import Commands.CommandInterface.CommandInterface;
import SpaceMarine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Класс UpdateId реализует команду для обновления элемента коллекции по его идентификатору (id).
 * Команда позволяет заменить существующий элемент коллекции SpaceMarine на новый с сохранением старого id.
 * Является частью системы управления коллекцией и реализует интерфейс CommandInterface.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class UpdateId implements CommandInterface {
    /** Название команды. */
    private final String name = "update_id";

    /** Описание команды. */
    private final String description = "обновить элемент по id";

    /** Менеджер коллекции, с которой работает команда. */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса UpdateId.
     *
     * @param collectionManager Менеджер коллекции, который предоставляет доступ к коллекции SpaceMarine.
     */
    public UpdateId(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду update_id.
     * Обновляет элемент коллекции с указанным id, заменяя его на новый объект.
     * Если аргументов нет, запрашивает id и данные интерактивно.
     * Если id некорректен или элемент не найден, выводит соответствующее сообщение.
     *
     * @param args Аргументы команды, где:
     *             args[0] — идентификатор элемента для обновления (целое число),
     *             args[1] — строка в формате CSV для создания нового элемента (name,x,y,health,achievements,weaponType,meleeWeapon,chapterName,parentLegion,marinesCount,world).
     */
    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();

        // Проверяем, пуста ли коллекция
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста, обновлять нечего.");
            return;
        }

        int oldId;
        Scanner scanner = new Scanner(System.in);

        // Получаем id
        try {
            if (args.length > 0) {
                // Если аргументы переданы, парсим id из args[0]
                oldId = Integer.parseInt(args[0]);
            } else {
                // Если аргументов нет, запрашиваем id интерактивно
                while (true) {
                    System.out.print("Введите id для обновления: ");
                    try {
                        oldId = Integer.parseInt(scanner.nextLine().trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: id должен быть целым числом. Попробуйте снова.");
                    }
                }
            }

            // Ищем элемент с указанным id
            for (int i = 0; i < collection.size(); i++) {
                if (collection.get(i).getId() == oldId) {
                    SpaceMarine.Builder builder = new SpaceMarine.Builder();

                    // Проверяем, есть ли CSV-аргументы
                    if (args.length > 1) {
                        // Парсим CSV-строку из args[1]
                        String[] csvArgs = parseCsvArgs(args[1]);
                        if (csvArgs.length != 11) {
                            System.out.println("Ошибка: для update_id с аргументами требуется 11 полей, разделённых запятыми: " +
                                    "name,x,y,health,achievements,weaponType,meleeWeapon,chapterName,parentLegion,marinesCount,world");
                            System.out.println("Передано аргументов: " + csvArgs.length);
                            return;
                        }
                        try {
                            builder.setName(csvArgs[0].trim());
                            builder.setCoordinates(new Coordinates(Integer.parseInt(csvArgs[1].trim()), Double.parseDouble(csvArgs[2].trim())));
                            builder.setHealth(Float.parseFloat(csvArgs[3].trim()));
                            builder.setAchievements(csvArgs[4].trim());
                            builder.setWeaponType(Weapon.valueOf(csvArgs[5].trim().toUpperCase()));
                            builder.setMeleeWeapon(MeleeWeapon.valueOf(csvArgs[6].trim().toUpperCase()));
                            builder.setChapter(new Chapter(csvArgs[7].trim(), csvArgs[8].trim(), Integer.parseInt(csvArgs[9].trim()), csvArgs[10].trim()));
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: некорректный формат числа: " + e.getMessage());
                            return;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: некорректное значение перечисления: " + e.getMessage());
                            return;
                        }
                    } else {
                        // Интерактивный ввод данных
                        String marineName;
                        while (true) {
                            System.out.print("Введите имя: ");
                            marineName = scanner.nextLine().trim();
                            if (!marineName.isEmpty()) break;
                            System.out.println("Ошибка: имя не может быть пустым. Попробуйте снова.");
                        }
                        builder.setName(marineName);

                        Integer x;
                        while (true) {
                            System.out.print("Введите координату x (целое число): ");
                            try {
                                x = Integer.parseInt(scanner.nextLine().trim());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: x должен быть целым числом. Попробуйте снова.");
                            }
                        }

                        Double y;
                        while (true) {
                            System.out.print("Введите координату y (дробное число): ");
                            try {
                                y = Double.parseDouble(scanner.nextLine().trim());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: y должен быть дробным числом. Попробуйте снова.");
                            }
                        }
                        builder.setCoordinates(new Coordinates(x, y));

                        while (true) {
                            System.out.print("Введите здоровье (дробное число больше 0): ");
                            String input = scanner.nextLine().trim();
                            try {
                                float health = Float.parseFloat(input);
                                if (health <= 0) {
                                    System.out.println("Ошибка: здоровье должно быть больше 0. Попробуйте снова.");
                                } else {
                                    builder.setHealth(health);
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: здоровье должно быть дробным числом. Попробуйте снова.");
                            }
                        }

                        String achievements;
                        while (true) {
                            System.out.print("Введите достижения: ");
                            achievements = scanner.nextLine().trim();
                            if (!achievements.isEmpty()) break;
                            System.out.println("Ошибка: достижения не могут быть пустыми. Попробуйте снова.");
                        }
                        builder.setAchievements(achievements);

                        while (true) {
                            System.out.print("Введите тип оружия (1 - MELTAGUN, 2 - PLASMA_GUN, 3 - HEAVY_FLAMER, или нажмите Enter для null): ");
                            String input = scanner.nextLine().trim();
                            if (input.isEmpty()) {
                                builder.setWeaponType(null); // Устанавливаем null при пустом вводе
                                break;
                            }
                            try {
                                int num = Integer.parseInt(input);
                                builder.setWeaponType(Weapon.fromInt(num));
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: введите число от 1 до 3 или оставьте пустым для null. Попробуйте снова.");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Ошибка: тип оружия должен быть одним из: MELTAGUN, PLASMA_GUN, HEAVY_FLAMER. Попробуйте снова.");
                            }
                        }

                        while (true) {
                            System.out.print("Введите ближнее оружие (1 - CHAIN_AXE, 2 - POWER_BLADE, 3 - POWER_FIST): ");
                            String input = scanner.nextLine().trim();
                            try {
                                int num = Integer.parseInt(input);
                                builder.setMeleeWeapon(MeleeWeapon.fromInt(num));
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Ошибка: ближнее оружие должно быть одним из: CHAIN_AXE, POWER_BLADE, POWER_FIST. Попробуйте снова.");
                            }
                        }

                        String chapterName;
                        while (true) {
                            System.out.print("Введите название главы: ");
                            chapterName = scanner.nextLine().trim();
                            if (!chapterName.isEmpty()) break;
                            System.out.println("Ошибка: название главы не может быть пустым. Попробуйте снова.");
                        }

                        String parentLegion;
                        while (true) {
                            System.out.print("Введите родительский легион: ");
                            parentLegion = scanner.nextLine().trim();
                            if (!parentLegion.isEmpty()) break;
                            System.out.println("Ошибка: родительский легион не может быть пустым. Попробуйте снова.");
                        }

                        Integer marinesCount;
                        while (true) {
                            System.out.print("Введите количество маринов (целое число от 1 до 1000 включительно, либо null (нажмите Enter)): ");
                            String input = scanner.nextLine().trim();

                            if (input.isEmpty()) {  // Если нажали Enter без ввода, то это null
                                marinesCount = null;
                                break;
                            }

                            try {
                                marinesCount = Integer.parseInt(input);
                                if (marinesCount < 1 || marinesCount > 1000) {
                                    System.out.println("Ошибка: число должно быть от 1 до 1000. Попробуйте снова.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: количество маринов должно быть целым числом. Попробуйте снова.");
                            }
                        }

                        String world;
                        while (true) {
                            System.out.print("Введите мир: ");
                            world = scanner.nextLine().trim();
                            if (!world.isEmpty()) break;
                            System.out.println("Ошибка: мир не может быть пустым. Попробуйте снова.");
                        }
                        builder.setChapter(new Chapter(chapterName, parentLegion, marinesCount, world));
                    }

                    // Сохраняем старый id вместо генерации нового
                    builder.setId(oldId);
                    SpaceMarine newMarine = builder.build();
                    collection.set(i, newMarine);
                    System.out.println("Элемент с id " + oldId + " обновлён.");
                    return;
                }
            }
            System.out.println("Элемент с id " + oldId + " не найден.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: id должен быть целым числом.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Парсит строку аргументов в формате CSV, учитывая кавычки.
     *
     * @param argLine Строка аргументов, разделённых запятыми, с возможными кавычками.
     * @return Массив строк, представляющих поля из CSV.
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
     * @return Название команды ("update_id").
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
