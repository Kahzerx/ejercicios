package utils;

import java.awt.*;

public enum LogLevel {
    INFORMATION("[INFO]", Color.BLUE),
    ERROR("[ERROR]", Color.RED),
    SUCCESS("[SUCCESS]", Color.GREEN);

    private final String level;
    private final Color color;

    LogLevel(String level, Color color) {
        this.level = level;
        this.color = color;
    }

    public String getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }
}
