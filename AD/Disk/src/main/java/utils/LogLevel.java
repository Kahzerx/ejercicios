package utils;

import java.awt.*;

public enum LogLevel {
    INFORMATION("INFO", new Color(0, 0, 255)),
    ERROR("ERROR", new Color(255, 0, 0)),
    SUCCESS("SUCCESS", new Color(0, 204, 0));

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
