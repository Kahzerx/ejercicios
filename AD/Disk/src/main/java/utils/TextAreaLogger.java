package utils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextAreaLogger extends JTextPane {
    public TextAreaLogger() {
        setMargin(new Insets(10, 10, 10, 10));
        setEditable(false);
    }

    public void log(String connType, LogLevel level, String content) {
        try {
            StyledDocument doc = getStyledDocument();
            Style style = addStyle("", null);
            doc.insertString(doc.getLength(), String.format("[%s] [%s] [", connType, getTime()), style);
            StyleConstants.setForeground(style, level.getColor());
            doc.insertString(doc.getLength(), String.format("%s", level.getLevel()), style);
            StyleConstants.setForeground(style, Color.BLACK);
            doc.insertString(doc.getLength(), String.format("]: %s%n", content), style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        // this.setText(String.format("%s [%s] [%s] %s: %s%n", getText(), connType, getTime(), level.getLevel(), content));
    }

    private String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
