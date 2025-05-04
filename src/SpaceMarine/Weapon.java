package SpaceMarine;

/**
 * Перечисление Weapon представляет типы дальнего оружия, доступные для космических десантников (SpaceMarine).
 * Содержит возможные варианты: MELTAGUN, PLASMA_GUN и HEAVY_FLAMER.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public enum Weapon {
    /**
     * Мелтаган (MELTAGUN).
     * Оружие дальнего боя, использующее высокотемпературный луч для уничтожения брони.
     */
    MELTAGUN(1),

    /**
     * Плазменная пушка (PLASMA_GUN).
     * Мощное оружие дальнего боя, стреляющее зарядами плазмы.
     */
    PLASMA_GUN(2),

    /**
     * Тяжёлый огнемёт (HEAVY_FLAMER).
     * Оружие дальнего боя, использующее поток огня для поражения врагов.
     */
    HEAVY_FLAMER(3);

    private final int value;

    Weapon(int i) {
        this.value = i;
    }

    /**
     * Возвращает элемент перечисления Weapon по введённому числу.
     *
     * @param num Число, соответствующее элементу (1 - MELTAGUN, 2 - PLASMA_GUN, 3 - HEAVY_FLAMER).
     * @return Соответствующий элемент Weapon.
     * @throws IllegalArgumentException Если введённое число не соответствует ни одному элементу.
     */
    public static Weapon fromInt(int num) {
        switch (num) {
            case 1:
                return MELTAGUN;
            case 2:
                return PLASMA_GUN;
            case 3:
                return HEAVY_FLAMER;
            default:
                throw new IllegalArgumentException("Неверный номер для Weapon: " + num);
        }
    }

    /**
     * Возвращает число, соответсвующее константе Weapon.
     */
    public int getValue() {
        return value;
    }
}
