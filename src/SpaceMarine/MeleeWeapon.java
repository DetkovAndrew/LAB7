package SpaceMarine;

/**
 * Перечисление MeleeWeapon представляет типы ближнего оружия, доступные для космических десантников (SpaceMarine).
 * Содержит возможные варианты: CHAIN_AXE, POWER_BLADE и POWER_FIST.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public enum MeleeWeapon {
    /**
     * Цепной топор (CHAIN_AXE).
     * Оружие ближнего боя с высокой разрушительной силой.
     */
    CHAIN_AXE,

    /**
     * Энергетический клинок (POWER_BLADE).
     * Точный и мощный меч для ближнего боя.
     */
    POWER_BLADE,

    /**
     * Энергетический кулак (POWER_FIST).
     * Мощное оружие дальнего боя с усиленными ударами.
     */
    POWER_FIST;

    /**
     * Возвращает элемент перечисления MeleeWeapon по введённому числу.
     *
     * @param num Число, соответствующее элементу (1 - CHAIN_AXE, 2 - POWER_BLADE, 3 - POWER_FIST).
     * @return Соответствующий элемент MeleeWeapon.
     * @throws IllegalArgumentException Если введённое число не соответствует ни одному элементу.
     */
    public static MeleeWeapon fromInt(int num) {
        switch (num) {
            case 1:
                return CHAIN_AXE;
            case 2:
                return POWER_BLADE;
            case 3:
                return POWER_FIST;
            default:
                throw new IllegalArgumentException("Неверный номер для MeleeWeapon: " + num);
        }
    }
}
