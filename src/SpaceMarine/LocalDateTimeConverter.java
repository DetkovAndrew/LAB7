package SpaceMarine;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс LocalDateTimeConverter используется для конвертации строковых значений
 * в объекты LocalDateTime при обработке CSV-файлов с использованием OpenCSV.
 * Он преобразует строки в соответствии с форматом ISO_LOCAL_DATE_TIME.
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    /** Форматтер для преобразования строк в LocalDateTime. */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Конвертирует строковое представление даты и времени в объект LocalDateTime.
     *
     * @param value строковое значение даты и времени в формате ISO_LOCAL_DATE_TIME.
     * @return Объект LocalDateTime.
     */
    @Override
    protected LocalDateTime convert(String value) {
        return LocalDateTime.parse(value, formatter);
    }
}

