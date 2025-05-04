package SpaceMarine;

/**
 * Класс Coordinates представляет координаты объекта в двумерном пространстве.
 * Содержит значения x (целочисленное) и y (дробное).
 *
 * @author Андрей
 * @version 1.0
 * @since 2025-03-10
 */
public class Coordinates {
    /** Координата x. Поле не может быть null. */
    private Integer x;

    /** Координата y. */
    private double y;

    /**
     * Конструктор класса Coordinates.
     *
     * @param x Координата x (не может быть null).
     * @param y Координата y.
     */
    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату x.
     *
     * @return Значение x.
     */
    public Integer getX() {
        return x;
    }

    /**
     * Возвращает координату y.
     *
     * @return Значение y.
     */
    public Double getY() {
        return y;
    }
}