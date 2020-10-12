package editing;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Content {

    private static final ArrayList<String> saves = new ArrayList<>();

    // Retrocede en la Arraylist de contenido para simular el "deshacer".
    public static String undo() {
        if (!saves.isEmpty()) {
            return saves.get(saves.size() - 1);
        }
        return null;
    }

    // Realiza un autosave Cada vez que se presiona Enter.
    private static void autoSave(String content) {
        saves.add(content);
    }

    // Acciones ante determinadas hotkeys
    public static void getAction(int keyCode, String content) {
        if (keyCode == KeyEvent.VK_ENTER) {
            autoSave(content);
        }
    }

    public static void updateSaves(int action) {
        switch (action) {
            case 0:
                saves.clear();  // trigger al cerrar o abrir nuevo.
                break;
            case 1:
                if (!saves.isEmpty()) saves.remove(saves.size() - 1);  // trigger en undo().
                break;
            default:
                break;
        }
    }
}
