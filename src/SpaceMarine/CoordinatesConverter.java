package SpaceMarine;

import com.opencsv.bean.AbstractBeanField;

/**
 * Класс CoordinatesConverter используется для преобразования строковых значений
 * в объекты Coordinates при обработке CSV-файлов с использованием OpenCSV.
 * Ожидаемый формат строки: "x;y", где x — целое число, y — число с плавающей точкой.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class CoordinatesConverter extends AbstractBeanField<Coordinates, String> {
    /**
     * Конвертирует строковое представление координат в объект Coordinates.
     *
     * @param value строковое значение координат в формате "x;y".
     * @return Объект Coordinates.
     */
    @Override
    protected Coordinates convert(String value) {
        String[] parts = value.trim().split(";");
        return new Coordinates(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
    }
}