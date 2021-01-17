package utils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextAreaLogger extends JTextArea {
    public TextAreaLogger() {
        setMargin(new Insets(10, 10, 10, 10));
        setEditable(false);
    }

    public void log(String connType, LogLevel level, String content) {
        this.setText(String.format("%s [%s] [%s] %s: %s%n", getText(), connType, getTime(), level.getLevel(), content));
    }

    private String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
