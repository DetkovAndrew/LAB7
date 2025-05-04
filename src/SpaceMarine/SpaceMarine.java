package SpaceMarine;

import java.time.LocalDateTime;
import java.util.Vector;

public class SpaceMarine {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private float health;
    private String achievements;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    private Integer ownerId;

    // Приватный конструктор для Builder
    private SpaceMarine(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.health = builder.health;
        this.achievements = builder.achievements;
        this.weaponType = builder.weaponType;
        this.meleeWeapon = builder.meleeWeapon;
        this.chapter = builder.chapter;
        this.ownerId = builder.ownerId;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public float getHealth() { return health; }
    public String getAchievements() { return achievements; }
    public Weapon getWeaponType() { return weaponType; }
    public MeleeWeapon getMeleeWeapon() { return meleeWeapon; }
    public Chapter getChapter() { return chapter; }
    public Integer getOwnerId() { return ownerId; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public void setHealth(float health) { this.health = health; }
    public void setAchievements(String achievements) { this.achievements = achievements; }
    public void setWeaponType(Weapon weaponType) { this.weaponType = weaponType; }
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) { this.meleeWeapon = meleeWeapon; }
    public void setChapter(Chapter chapter) { this.chapter = chapter; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    // Метод для генерации следующего ID
    public static int generateNextId(Vector<SpaceMarine> collection) {
        if (collection.isEmpty()) return 1;
        return collection.stream()
                .mapToInt(SpaceMarine::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", achievements='" + achievements + '\'' +
                ", weaponType=" + weaponType +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                ", ownerId=" + ownerId +
                '}';
    }

    // Builder
    public static class Builder {
        private int id;
        private String name;
        private Coordinates coordinates;
        private LocalDateTime creationDate;
        private float health;
        private String achievements;
        private Weapon weaponType;
        private MeleeWeapon meleeWeapon;
        private Chapter chapter;
        private Integer ownerId;

        public Builder setId(int id) { this.id = id; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; return this; }
        public Builder setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; return this; }
        public Builder setHealth(float health) { this.health = health; return this; }
        public Builder setAchievements(String achievements) { this.achievements = achievements; return this; }
        public Builder setWeaponType(Weapon weaponType) { this.weaponType = weaponType; return this; }
        public Builder setMeleeWeapon(MeleeWeapon meleeWeapon) { this.meleeWeapon = meleeWeapon; return this; }
        public Builder setChapter(Chapter chapter) { this.chapter = chapter; return this; }
        public Builder setOwnerId(Integer ownerId) { this.ownerId = ownerId; return this; }

        public SpaceMarine build() {
            if (name == null || coordinates == null || creationDate == null || health <= 0 ||
                    achievements == null || meleeWeapon == null || chapter == null) {
                throw new IllegalStateException("Все обязательные поля должны быть установлены");
            }
            return new SpaceMarine(this);
        }
    }

    public String toCSV() {
        return id + "," +
                name + "," +
                coordinates.getX() + "," +
                coordinates.getY() + "," +
                creationDate + "," +
                health + "," +
                achievements + "," +
                (weaponType != null ? weaponType.getValue() : "") + "," +
                meleeWeapon + "," +
                chapter.getName() + "," +
                chapter.getParentLegion() + "," +
                chapter.getMarinesCount() + "," +
                chapter.getWorld() + "," +
                ownerId;
    }
}