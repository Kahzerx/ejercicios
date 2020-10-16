package editor.application;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Content {

    private static final ArrayList<String> saves = new ArrayList<>();
    private static String actualContent;

    // Retrocede en la Arraylist de contenido para simular el "deshacer".
    public static String undo() {
        return saves.size() > 1 ? saves.get(saves.size() - 1) : actualContent;
    }

    // Realiza un autosave Cada vez que se presiona Enter.
    private static void autoSave(String content) {
        saves.add(content);
    }

    // Acciones ante determinadas hotkeys.
    public static void getAction(int keyCode, String content) {
        if (keyCode == KeyEvent.VK_ENTER) {
            autoSave(content);
        }
    }

    // Acciones para la arraylist.
    public static void updateSaves(int action) {
        switch (action) {
            case 0:
                saves.clear();  // trigger al cerrar o abrir nuevo.
                autoSave("");
                break;
            case 1:
                saves.remove(saves.size() - 1);  // trigger en undo().
                if (saves.isEmpty()) autoSave("");
                break;
            default:
                break;
        }
    }

    // Actualizar variable cada vez que se guarda.
    public static void updateContent(String text) {
        actualContent = text;
    }

    // Resetear la variable que guarda la referencia de si el archivo ha sido editado o no.
    public static void resetContent() {
        actualContent = null;
    }

    // Comprueba si el documento ha tenido cambios desde la última vez que se guardó.
    public static boolean hasChanged(String text) {
        if (actualContent != null) return !actualContent.equals(text);
        return false;
    }
}
