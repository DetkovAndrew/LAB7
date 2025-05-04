package SpaceMarine;

import com.opencsv.bean.AbstractBeanField;

/**
 * Класс ChapterConverter используется для преобразования строковых значений
 * в объекты Chapter при обработке CSV-файлов с использованием OpenCSV.
 * Ожидаемый формат строки: "name;parentLegion;marinesCount;world".
 *
 * @author Андрей
 * @version 1.1
 * @since 2025-03-10
 */
public class ChapterConverter extends AbstractBeanField<Chapter, String> {
    /**
     * Конвертирует строковое представление главы в объект Chapter.
     * Поддерживает marinesCount как null, если значение пустое или "null".
     *
     * @param value строковое значение главы в формате "name;parentLegion;marinesCount;world"
     * @return объект Chapter
     * @throws IllegalArgumentException если формат строки неверный или marinesCount невалиден
     */
    @Override
    protected Chapter convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter cannot be null or empty");
        }
        String[] parts = value.split(";");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid Chapter format. Expected 4 parts separated by ';', got: " + value);
        }
        String name = parts[0].trim();
        String parentLegion = parts[1].trim();
        Integer marinesCount;
        String marinesCountStr = parts[2].trim();
        if (marinesCountStr.isEmpty() || marinesCountStr.equalsIgnoreCase("null")) {
            marinesCount = null; // Поддержка пустой строки или "null"
        } else {
            try {
                marinesCount = Integer.parseInt(marinesCountStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid marinesCount: " + marinesCountStr);
            }
        }
        String world = parts[3].trim();
        return new Chapter(name, parentLegion, marinesCount, world);
    }
}


