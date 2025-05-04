package Commands.ElementsOuterOperating;

import CollectionManager.CollectionManager;
import SpaceMarine.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.lang.reflect.Field;

public class Adding {
    public static void execute(String[] args, CollectionManager collectionManager) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        Scanner scanner = new Scanner(System.in);
        SpaceMarine.Builder builder = new SpaceMarine.Builder();

        if (args.length == 0) { // Интерактивный режим
            // Ввод имени
            while (true) {
                System.out.print("Введите имя: ");
                String marineName = scanner.nextLine().trim();
                if (marineName.isEmpty()) {
                    System.out.println("Ошибка: имя не может быть пустым. Попробуйте снова.");
                } else {
                    builder.setName(marineName);
                    break;
                }
            }
            // Ввод координат
            int x;
            while (true) {
                System.out.print("Введите координату x (целое число): ");
                String input = scanner.nextLine().trim();
                try {
                    x = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: x должен быть целым числом. Попробуйте снова.");
                }
            }
            double y;
            while (true) {
                System.out.print("Введите координату y (дробное число): ");
                String input = scanner.nextLine().trim();
                try {
                    y = Double.parseDouble(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: y должен быть дробным числом. Попробуйте снова.");
                }
            }
            builder.setCoordinates(new Coordinates(x, y));
            // Ввод здоровья
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
            // Ввод достижений
            while (true) {
                System.out.print("Введите достижения: ");
                String achievements = scanner.nextLine().trim();
                if (achievements.isEmpty()) {
                    System.out.println("Ошибка: достижения не могут быть пустыми. Попробуйте снова.");
                } else {
                    builder.setAchievements(achievements);
                    break;
                }
            }
            // Ввод типа оружия
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
            // Ввод ближнего оружия
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
            // Ввод данных главы
            String chapterName;
            while (true) {
                System.out.print("Введите название главы: ");
                chapterName = scanner.nextLine().trim();
                if (chapterName.isEmpty()) {
                    System.out.println("Ошибка: название главы не может быть пустым. Попробуйте снова.");
                } else {
                    break;
                }
            }
            String parentLegion;
            while (true) {
                System.out.print("Введите родительский легион: ");
                parentLegion = scanner.nextLine().trim();
                if (parentLegion.isEmpty()) {
                    System.out.println("Ошибка: родительский легион не может быть пустым. Попробуйте снова.");
                } else {
                    break;
                }
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
                if (world.isEmpty()) {
                    System.out.println("Ошибка: мир не может быть пустым. Попробуйте снова.");
                } else {
                    break;
                }
            }
            builder.setChapter(new Chapter(chapterName, parentLegion, marinesCount, world));
            builder.setCreationDate(LocalDateTime.now());
        } else { // Режим CSV-ввода
            String argLine = args[0].trim();
            String[] csvArgs = parseCsvArgs(argLine);
            if (csvArgs.length != 7) {
                System.out.println("Ошибка: требуется 7 полей через запятые. Передано: " + csvArgs.length);
                return;
            }
            try {
                builder.setName(csvArgs[0].trim());
                String[] coords = csvArgs[1].trim().split(";");
                if (coords.length != 2) {
                    System.out.println("Ошибка: неверный формат координат. Ожидается x;y");
                    return;
                }
                int x = Integer.parseInt(coords[0].trim());
                double y = Double.parseDouble(coords[1].trim());
                builder.setCoordinates(new Coordinates(x, y));
                builder.setHealth(Float.parseFloat(csvArgs[2].trim()));
                builder.setAchievements(csvArgs[3].trim());
                builder.setWeaponType(Weapon.valueOf(csvArgs[4].trim().toUpperCase()));
                builder.setMeleeWeapon(MeleeWeapon.valueOf(csvArgs[5].trim().toUpperCase()));
                String[] chapterParts = csvArgs[6].trim().split(";");
                if (chapterParts.length != 4) {
                    System.out.println("Ошибка: неверный формат главы. Ожидается chapterName;parentLegion;marinesCount;world");
                    return;
                }
                String chapterName = chapterParts[0].trim();
                String parentLegion = chapterParts[1].trim();
                int marinesCount = Integer.parseInt(chapterParts[2].trim());
                String world = chapterParts[3].trim();
                builder.setChapter(new Chapter(chapterName, parentLegion, marinesCount, world));
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: некорректный формат числа: " + e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: некорректное значение перечисления: " + e.getMessage());
                return;
            }
        }
        int newId = collection.size() + 1;
        builder.setId(newId);
        // Устанавливаем ownerId с помощью рефлексии
        try {
            Class<?> mainClass = Class.forName("Main");
            Field currentUserIdField = mainClass.getDeclaredField("currentUserId");
            currentUserIdField.setAccessible(true);
            Integer userId = (Integer) currentUserIdField.get(null); // null, так как поле статическое
            if (userId == null) {
                System.out.println("Ошибка: пользователь не авторизован. Выполните вход перед добавлением элемента.");
                return;
            }
            builder.setOwnerId(userId);
        } catch (Exception e) {
            System.out.println("Ошибка: не удалось получить ID пользователя: " + e.getMessage());
            return;
        }
        SpaceMarine marine = builder.build();
        collection.add(marine);
        System.out.println("Элемент добавлен с id " + newId + ": " + marine);
    }

    private static String[] parseCsvArgs(String argLine) {
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
}