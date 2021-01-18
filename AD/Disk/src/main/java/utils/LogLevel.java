package utils;

import java.awt.*;

/**
 * Enum custom para el logger y los distintos niveles de cada log.
 */
public enum LogLevel {
    INFORMATION("INFO", new Color(0, 0, 255)),
    ERROR("ERROR", new Color(255, 0, 0)),
    SUCCESS("SUCCESS", new Color(0, 204, 0));

    private final String level;
    private final Color color;

    /**
     * Registro los tipos de log junto con un color para mostrarlos por el {@link TextPaneLogger}.
     * @param level tipo de log: INFO, ERROR, SUCCESS.
     * @param color color asociado con ese tipo de log.
     */
    LogLevel(String level, Color color) {
        this.level = level;
        this.color = color;
    }

    /**
     * getter de nivel.
     * @return nombre.
     */
    public String getLevel() {
        return level;
    }

    /**
     * getter de color.
     * @return color.
     */
    public Color getColor() {
        return color;
    }
}
