package SpaceMarine;

import com.opencsv.bean.AbstractBeanField;

/**
 * Класс WeaponConverter используется для преобразования строковых значений
 * в объекты Weapon при обработке CSV-файлов с использованием OpenCSV.
 * Поддерживает null как допустимое значение.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-04-07
 */
public class WeaponConverter extends AbstractBeanField<Weapon, String> {
    /**
     * Конвертирует строковое представление типа оружия в объект Weapon.
     * Если строка пуста или равна "null", возвращает null.
     *
     * @param value строковое значение типа оружия
     * @return объект Weapon или null
     * @throws IllegalArgumentException если значение не соответствует ни одному из Weapon
     */
    @Override
    protected Weapon convert(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            return null; // Поддержка null
        }
        try {
            return Weapon.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid weapon type: " + value);
        }
    }
}