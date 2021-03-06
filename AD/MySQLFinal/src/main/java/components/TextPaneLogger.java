package components;

import utils.LogLevel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pane a modo de logger para ~~debug~~ mostrar información al usuario del estado de la conexión.
 */
public class TextPaneLogger extends JTextPane {
    public TextPaneLogger() {
        setMargin(new Insets(10, 10, 10, 10));
        setEditable(false);
    }

    /**
     * Enviar un mensaje con un nivel a la consola.
     * @param level El tipo del mensaje.
     * @param content Mensaje que quieres enviar.
     */
    public void log(LogLevel level, String content) {
        try {
            StyledDocument doc = getStyledDocument();
            Style style = addStyle("", null);
            doc.insertString(doc.getLength(), String.format("[%s] [", getTime()), style);  // Digo qué tipo de conexión corresponde con esta linea y la hora.
            StyleConstants.setForeground(style, level.getColor());
            doc.insertString(doc.getLength(), String.format("%s", level.getLevel()), style);  // Cambio el color al del enum del LogLevel y añado el tipo de log que es.
            StyleConstants.setForeground(style, Color.BLACK);
            doc.insertString(doc.getLength(), String.format("]: %s%n", content), style);  // Añado el propio mensaje y salto de línea.
            setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        // this.setText(String.format("%s [%s] [%s] %s: %s%n", getText(), connType, getTime(), level.getLevel(), content));
    }

    /**
     * Limpiar la consola.
     */
    public void clearLog() {
        setText("");
    }

    /**
     * Extraer la hora actual con ms.
     * @return Hora formateada.
     */
    private String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
