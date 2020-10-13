package application;

import editing.Content;
import fileManagement.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Hacer un editor de texto en el cual se pueda escribir, abrir archivos y guardarlos.
 */

//TODO: zoom, información del punto en el que se está escribiendo (meh), comprobar si se está editando.

public class MainWindow {

    // Inicializar ventana desde el constructor.
    public MainWindow() {
        initialize();
    }

    private JFrame frame;
    private JTextArea textArea;
    private String windowName = "Sin título";

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
    }

    // Configuro la TextArea junto con la fuente y el tamaño de letra.
    private void createJTextArea() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.getContentPane().add(textArea, BorderLayout.NORTH);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                // Detección de si el archivo editandose ha sido eliminado.
                if (!FileManagement.fileIsNull()) {
                    if (!FileManagement.fileExists()) {
                        FileManagement.onRandomDelete();
                        Content.resetContent();
                        Content.updateSaves(0);

                        setWindowName("Sin título");
                        updateTitle("");
                    }
                    else updateTitle(Content.hasChanged(textArea.getText()) ? "*" : "");
                }

                // Autosaves.
                Content.getAction(keyEvent.getKeyCode(), textArea.getText());
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }
            @Override
            public void keyReleased(KeyEvent keyEvent) {}
        });
    }

    // Configuro un JFrame en 1280x720 con un BorderLayout para colocar más elementos.
    private void createJFrame() {
        frame = new JFrame();
        updateTitle("");
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
    }

    // Desplegable de "Archivo".
    private void createArchivoMenu(JMenuBar bar) {
        JMenu menuArchivo = new JMenu("Archivo");
        bar.add(menuArchivo);

        JMenuItem open = new JMenuItem("Abrir");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        // Listener para realizar una acción al presionar cada item del menu.
        open.addActionListener(actionEvent -> onOpen());
        menuArchivo.add(open);

        JMenuItem save = new JMenuItem("Guardar");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(actionEvent -> FileManagement.saveFile(textArea.getText()));
        menuArchivo.add(save);

        JMenuItem saveAs = new JMenuItem("Guardar como...");
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        saveAs.addActionListener(actionEvent -> FileManagement.createFile(textArea.getText()));
        menuArchivo.add(saveAs);

        JMenuItem close = new JMenuItem("Cerrar");
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        close.addActionListener(actionEvent -> FileManagement.close());
        menuArchivo.add(close);
    }

    // Desplegable de "Editar".
    private void createEditarMenu(JMenuBar bar) {
        JMenu menuEditar = new JMenu("Editar");
        bar.add(menuEditar);

        JMenuItem undo = new JMenuItem("Deshacer");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undo.addActionListener(actionEvent -> {
            textArea.setText(Content.undo());
            Content.updateSaves(1);
        });
        menuEditar.add(undo);

        JMenuItem selectAll = new JMenuItem("Seleccionar todo");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        selectAll.addActionListener(actionEvent -> textArea.selectAll());
        menuEditar.add(selectAll);
    }

    // Actualizar el título de la ventana.
    private void updateTitle(String updated) {
        frame.setTitle(String.format("%s%s - Editor de Texto", updated, windowName));
    }

    // Cambiar el valor según cambias de archivo.
    private void setWindowName(String newName) {
        windowName = newName;
    }

    // Acciones cada vez que se abre un archivo.
    private void onOpen() {
        String value = FileManagement.openFile();
        textArea.setText(value);
        textArea.setCaretPosition(0);  // Pongo el cursor en la primera linea.

        Content.updateContent(value);

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
