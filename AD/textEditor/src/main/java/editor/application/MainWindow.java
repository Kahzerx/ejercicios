package editor.application;

import editor.fileManagement.FileManagement;
import editor.utils.LanguageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Hacer un editor de texto en el cual se pueda escribir, abrir archivos y guardarlos.
 */

//TODO: información del punto en el que se está escribiendo (meh), multilanguage support LOL.

public class MainWindow {

    // Inicializar ventana desde el constructor.
    public MainWindow() {
        if (LanguageUtils.trySetLanguage("es_es")) initialize();
        else System.out.println("No ha sido posible iniciar el programa, no ha sido posible establecer un idioma.");
    }

    private JFrame frame;
    private JTextArea textArea;
    private final String untitled = "Sin título";
    private String windowName = untitled;
    private int fontSize = 15;

    private void initialize() {
        createJFrame();

        createJTextArea();

        // Scroll tanto lateral como vertical para visualizar el texto.
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Menu superior para desplegables.
        JMenuBar bar = new JMenuBar();
        frame.getContentPane().add(bar, BorderLayout.NORTH);

        createArchivoMenu(bar);

        createEditarMenu(bar);

        createVistaMenu(bar);
    }

    // Configuro un JFrame en 1280x720 con un BorderLayout para colocar componentes y redimensionarlos.
    private void createJFrame() {
        frame = new JFrame();
        updateTitle("");
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
    }

    // Configuro la TextArea junto con la fuente y el tamaño de letra.
    private void createJTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        frame.getContentPane().add(textArea, BorderLayout.NORTH);

        // Fuerzo un autosave al iniciar un documento.
        Content.getAction(KeyEvent.VK_ENTER, textArea.getText());

        textArea.addKeyListener(new KeyListener() {  // Acciones que se ejecutan cada vez que se suelta una tecla.
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                // Autosaves.
                Content.getAction(keyEvent.getKeyCode(), textArea.getText());
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                // Detección de si el archivo editándose ha sido eliminado.
                checkFile();
            }
        });
    }

    // Desplegable de "Archivo".
    private void createArchivoMenu(JMenuBar bar) {
        JMenu menuArchivo = new JMenu(LanguageUtils.getTranslation("menu.file"));
        bar.add(menuArchivo);

        JMenuItem open = new JMenuItem(LanguageUtils.getTranslation("menu.file.open"));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));  // Hotkeys.
        // Listener para realizar una acción al presionar cada item del menu.
        open.addActionListener(actionEvent -> onOpen());
        menuArchivo.add(open);

        menuArchivo.addSeparator();

        JMenuItem save = new JMenuItem(LanguageUtils.getTranslation("menu.file.save"));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(actionEvent -> onSave());
        menuArchivo.add(save);

        JMenuItem saveAs = new JMenuItem(LanguageUtils.getTranslation("menu.file.saveAs"));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        saveAs.addActionListener(actionEvent -> onCreateAndSave());
        menuArchivo.add(saveAs);

        menuArchivo.addSeparator();

        JMenuItem close = new JMenuItem(LanguageUtils.getTranslation("menu.file.close"));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        close.addActionListener(actionEvent -> onClose());
        menuArchivo.add(close);
    }

    // Desplegable de "Editar".
    private void createEditarMenu(JMenuBar bar) {
        JMenu menuEditar = new JMenu(LanguageUtils.getTranslation("menu.edit"));
        bar.add(menuEditar);

        JMenuItem undo = new JMenuItem(LanguageUtils.getTranslation("menu.edit.undo"));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undo.addActionListener(actionEvent -> onUndo());
        menuEditar.add(undo);

        menuEditar.addSeparator();

        JMenuItem selectAll = new JMenuItem(LanguageUtils.getTranslation("menu.edit.selectAll"));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        selectAll.addActionListener(actionEvent -> textArea.selectAll());
        menuEditar.add(selectAll);
    }

    private void createVistaMenu(JMenuBar bar) {
        JMenu menuVista = new JMenu(LanguageUtils.getTranslation("menu.view"));
        bar.add(menuVista);

        //Hotkeys del teclado numérico.
        JMenuItem zoomIn = new JMenuItem(LanguageUtils.getTranslation("menu.view.zoomIn"));
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_DOWN_MASK));
        zoomIn.addActionListener(actionEvent -> onZoomIn());
        menuVista.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem(LanguageUtils.getTranslation("menu.view.zoomOut"));
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_DOWN_MASK));
        zoomOut.addActionListener(actionEvent -> onZoomOut());
        menuVista.add(zoomOut);

        JMenuItem zoomReset = new JMenuItem(LanguageUtils.getTranslation("menu.view.defaultZoom"));
        zoomReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, InputEvent.CTRL_DOWN_MASK));
        zoomReset.addActionListener(actionEvent -> onZoomReset());
        menuVista.add(zoomReset);
    }

    // Actualizar el título de la ventana.
    private void updateTitle(String updated) {
        String appName = LanguageUtils.getTranslation("app.name");
        frame.setTitle(String.format("%s%s - %s", updated, windowName, appName));
    }

    // Cambiar el valor según cambias de archivo.
    private void setWindowName(String newName) {
        windowName = newName;
    }

    // Acciones cada vez que se abre un archivo.
    private void onOpen() {
        checkFile();  // Arreglar un bug en el que eliminas el archivo editandose y sin que el programa se de cuenta (ejecutando hotkeys) hagas alguna accion de archivo.
        FileManagement.shouldSaveBeforeOpening(textArea.getText(), frame.getTitle());
        String value = FileManagement.openFile();
        textArea.setText(value);
        textArea.setCaretPosition(0);  // Pongo el cursor en la primera linea.

        if (FileManagement.fileIsNotNull() && FileManagement.fileExists()) {
            updateWindow(value);

            Content.updateSaves(0);
        }
    }

    // Acciones cada vez que se guarda un archivo.
    private void onSave() {
        String value = textArea.getText();
        FileManagement.saveFile(value);

        if (FileManagement.fileIsNotNull() && FileManagement.fileExists()) {
            updateWindow(value);
        }
    }

    // Acciones cada vez que se crea y guarda un archivo.
    private void onCreateAndSave() {
        String value = textArea.getText();
        FileManagement.createAndSaveFile(value);

        if (FileManagement.fileIsNotNull() && FileManagement.fileExists()) {
            updateWindow(value);
        }
    }

    // Acciones cada vez que se cierra el documento.
    private void onClose() {
        checkFile();  // Arreglar un bug en el que eliminas el archivo editandose y sin que el programa se de cuenta (ejecutando hotkeys) hagas alguna accion de archivo.
        FileManagement.close(textArea.getText(), frame.getTitle());
        Content.resetContent();
        Content.updateSaves(0);

        textArea.setText("");

        setWindowName(untitled);
        updateTitle("");
    }

    // Acciones cada vez que se realiza un deshacer.
    private void onUndo() {
        textArea.setText(Content.undo());
        Content.updateSaves(1);
    }

    // Acciones al añadir zoom.
    private void onZoomIn() {
        if (textArea.getFont().getSize() <= 40) textArea.setFont(new Font("Arial", Font.PLAIN, fontSize += 2));
    }

    // Acciones al quitar zoom.
    private void onZoomOut() {
        if (textArea.getFont().getSize() >= 8) textArea.setFont(new Font("Arial", Font.PLAIN, fontSize -= 2));
    }

    // Acciones al resetear zoom.
    private void onZoomReset() {
        if (textArea.getFont().getSize() != 15) textArea.setFont(new Font("Arial", Font.PLAIN, fontSize = 15));
    }

    // Verifica que el archivo está en su estado normal y añado indicadores de cuando está siendo editado.
    private void checkFile() {
        if (FileManagement.fileIsNotNull()) {
            if (!FileManagement.fileExists()) {
                FileManagement.onDelete();
                Content.resetContent();

                setWindowName(untitled);
                updateTitle("");
            }
            else updateTitle(Content.hasChanged(textArea.getText()) ? "*" : "");
        }
    }

    // Actualizar los elementos que guardan información sobre el archivo anterior asi como el nombre de la ventana.
    private void updateWindow(String text) {
        Content.updateContent(text);

        setWindowName(FileManagement.getCurrentPath());
        updateTitle("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                MainWindow window = new MainWindow();
                window.frame.setVisible(true);
            }
            catch (Exception ignored) {}
        });
    }
}
