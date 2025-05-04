package Commands.ElementsOuterOperating;

import CollectionManager.CollectionManager;
import Commands.CommandInterface.CommandInterface;
import SpaceMarine.SpaceMarine;
import SpaceMarine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.lang.reflect.Field;

import static java.lang.Integer.parseInt;

public class InsertAtIndex implements CommandInterface {
    private final String name = "insert_at_index";
    private final String description = "вставить элемент на указанную позицию";
    private final CollectionManager collectionManager;

    public InsertAtIndex(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        Vector<SpaceMarine> collection = collectionManager.getCollection();
        SpaceMarine.Builder builder = new SpaceMarine.Builder();
        int index;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Введите индекс для вставки (целое число от 1 до " + (collection.size()) + "): ");
                try {
                    index = parseInt(scanner.nextLine().trim());
                    if (index < 1 || index > collection.size()+1) {
                        System.out.println("Недопустимый индекс: " + index + ". Должен быть от 1 до " + collection.size() + ".");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Индекс должен быть числом.");
                }
            }
        } else {
            if (args.length < 2) {
                System.out.println("Использование: insert_at_index index name,x,y,health,achievements,weaponType,meleeWeapon,chapterName,parentLegion,marinesCount,world");
                return;
            }
            try {
                index = parseInt(args[0]);
                if (index < 1 || index > collection.size()+1) {
                    System.out.println("Недопустимый индекс: " + index);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Индекс должен быть числом.");
                return;
            }
        }

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            String marineName = null;
            Integer x = null;
            Double y = null;
            String achievements = null;
            Weapon weaponType = null;
            MeleeWeapon meleeWeapon = null;
            String chapterName = null;
            String parentLegion = null;
            Integer marinesCount = null;
            String world = null;

            builder.setId(index);
            while (true) {
                System.out.print("Введите имя: ");
                try {
                    marineName = scanner.nextLine().trim();
                    if (marineName.isEmpty()) {
                        System.out.println("Ошибка: имя не может быть пустым. Попробуйте снова.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
                }
            }
            builder.setName(marineName);

            while (true) {
                System.out.print("Введите координату x (целое число): ");
                try {
                    x = parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: x должен быть целым числом. Попробуйте снова.");
                }
            }

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
                    Float health = Float.parseFloat(input);
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

            while (true) {
                System.out.print("Введите достижения: ");
                try {
                    achievements = scanner.nextLine().trim();
                    if (achievements.isEmpty()) {
                        System.out.println("Ошибка: достижения не могут быть пустыми. Попробуйте снова.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
                }
            }
            builder.setAchievements(achievements);

            while (true) {
                System.out.print("Введите тип оружия (1 - MELTAGUN, 2 - PLASMA_GUN, 3 - HEAVY_FLAMER, или нажмите Enter для null): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    builder.setWeaponType(null);
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
                    int num = parseInt(input);
                    builder.setMeleeWeapon(MeleeWeapon.fromInt(num));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: ближнее оружие должно быть одним из: CHAIN_AXE, POWER_BLADE, POWER_FIST. Попробуйте снова.");
                }
            }

            while (true) {
                System.out.print("Введите название главы (chapter name): ");
                try {
                    chapterName = scanner.nextLine().trim();
                    if (chapterName.isEmpty()) {
                        System.out.println("Ошибка: название главы не может быть пустым. Попробуйте снова.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
                }
            }

            while (true) {
                System.out.print("Введите родительский легион (parent legion): ");
                try {
                    parentLegion = scanner.nextLine().trim();
                    if (parentLegion.isEmpty()) {
                        System.out.println("Ошибка: родительский легион не может быть пустым. Попробуйте снова.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
                }
            }

            while (true) {
                System.out.print("Введите количество маринов (целое число от 1 до 1000, или нажмите Enter для null): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    marinesCount = null;
                    break;
                }
                try {
                    marinesCount = parseInt(input);
                    if (marinesCount <= 0) {
                        System.out.println("Ошибка: количество маринов должно быть больше 0. Попробуйте снова.");
                    } else if (marinesCount > 1000) {
                        System.out.println("Ошибка: количество маринов не может превышать 1000. Попробуйте снова.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: количество маринов должно быть целым числом. Попробуйте снова.");
                }
            }

            while (true) {
                System.out.print("Введите мир (world): ");
                try {
                    world = scanner.nextLine().trim();
                    if (world.isEmpty()) {
                        System.out.println("Ошибка: мир не может быть пустым. Попробуйте снова.");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
                }
            }
            builder.setChapter(new Chapter(chapterName, parentLegion, marinesCount, world));
        } else {
            String argLine = args[1].trim();
            String[] csvArgs = parseCsvArgs(argLine);
            if (csvArgs.length != 11) {
                System.out.println("Ошибка: для insert_at_index требуется 11 полей, разделённых запятыми: " +
                        "name,x,y,health,achievements,weaponType,meleeWeapon,chapterName,parentLegion,marinesCount,world");
                System.out.println("Передано аргументов: " + csvArgs.length);
                return;
            }
            try {
                builder.setId(index);
                builder.setName(csvArgs[0].trim());
                builder.setCoordinates(new Coordinates(parseInt(csvArgs[1].trim()), Double.parseDouble(csvArgs[2].trim())));
                builder.setHealth(Float.parseFloat(csvArgs[3].trim()));
                builder.setAchievements(csvArgs[4].trim());
                builder.setWeaponType(Weapon.valueOf(csvArgs[5].trim().toUpperCase()));
                builder.setMeleeWeapon(MeleeWeapon.valueOf(csvArgs[6].trim().toUpperCase()));
                builder.setChapter(new Chapter(csvArgs[7].trim(), csvArgs[8].trim(), parseInt(csvArgs[9].trim()), csvArgs[10].trim()));
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: некорректный формат числа: " + e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: некорректное значение перечисления: " + e.getMessage());
                return;
            }
        }

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
        SpaceMarine marine;
        try {
            marine = builder.build();
        } catch (Exception e) {
            System.out.println("Ошибка при создании объекта: " + e.getMessage());
            return;
        }

        try {
            collection.add(index-1, marine);
            System.out.println("Элемент вставлен на позицию " + index + " с сдвигом элементов ниже: " + marine);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        int i = 1;
        for (SpaceMarine marine1 : collection) {
            marine1.setId(i);
            i++;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

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
}