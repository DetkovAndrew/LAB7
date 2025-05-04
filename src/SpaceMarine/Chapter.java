package SpaceMarine;

/**
 * Класс Chapter представляет информацию о главе космических десантников (SpaceMarine).
 * Содержит данные о названии главы, родительском легионе, количестве десантников и мире.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Chapter {
    /** Название главы. Поле не может быть null, строка не может быть пустой. */
    private String name;

    /** Родительский легион главы. Поле может быть null. */
    private String parentLegion;

    /** Количество десантников в главе. Поле может быть null, значение должно быть больше 0 и не превышать 1000. */
    private Integer marinesCount;

    /** Мир, связанный с главой. Поле не может быть null. */
    private String world;

    /**
     * Конструктор класса Chapter.
     *
     * @param name Название главы (не может быть null или пустым).
     * @param parentLegion Родительский легион (может быть null).
     * @param marinesCount Количество десантников (может быть null, если не null, то должно быть от 1 до 1000).
     * @param world Мир, связанный с главой (не может быть null).
     */
    public Chapter(String name, String parentLegion, Integer marinesCount, String world) {
        this.name = name;
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    /**
     * Возвращает название главы.
     *
     * @return Название главы.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает родительский легион главы.
     *
     * @return Родительский легион (может быть null).
     */
    public String getParentLegion() {
        return parentLegion;
    }

    /**
     * Возвращает количество десантников в главе.
     *
     * @return Количество десантников (может быть null).
     */
    public Integer getMarinesCount() {
        return marinesCount;
    }

    /**
     * Возвращает мир, связанный с главой.
     *
     * @return Мир, связанный с главой.
     */
    public String getWorld() {
        return world;
    }
}
