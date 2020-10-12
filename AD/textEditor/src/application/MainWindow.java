package application;

import editing.Content;
import fileManagement.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/*
 * Hacer un editor de texto en el cual se pueda escribir, abrir archivos y guardarlos.
 */

//TODO: zoom, información del punto en el que se está escribiendo (meh).

public class MainWindow {

    // Inicializar ventana desde el constructor
    public MainWindow() {
        initialize();
    }

    private JFrame frame;
    private JTextArea textArea;

    private void initialize() {
        // Configuro un JFrame en 1280x720 con un BorderLayout para colocar más elementos.
        frame = new JFrame();
        frame.setTitle("Nuevo Documento - Editor de Texto");
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        // Configuro la TextArea junto con la fuente y el tamaño de letra.
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.getContentPane().add(textArea, BorderLayout.NORTH);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                Content.getAction(keyEvent.getKeyCode(), textArea.getText());
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            @Override
            public void keyReleased(KeyEvent keyEvent) {}
        });

        // Scroll tanto lateral como vertical para visualizar el texto.
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Menu superior para desplegables.
        JMenuBar bar = new JMenuBar();
        frame.getContentPane().add(bar, BorderLayout.NORTH);

        // Desplegable de "Archivo".
        JMenu menuArchivo = new JMenu("Archivo");
        bar.add(menuArchivo);

        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        // Listener para realizar una acción al presionar cada item del menu.
        abrir.addActionListener(actionEvent -> {
            try {
                textArea.setText(FileManagement.openFile());
                textArea.setCaretPosition(0);  // Pongo el cursor en la primera linea.
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(abrir);

        JMenuItem guardar = new JMenuItem("Guardar");
        guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        guardar.addActionListener(actionEvent -> {
            try {
                FileManagement.saveFile(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(guardar);

        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        guardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        guardarComo.addActionListener(actionEvent -> {
            try {
                FileManagement.createFile(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(guardarComo);

        JMenuItem cerrar = new JMenuItem("Cerrar");
        cerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        cerrar.addActionListener(actionEvent -> FileManagement.close());
        menuArchivo.add(cerrar);

        // Desplegable de "Editar".
        JMenu menuEditar = new JMenu("Editar");
        bar.add(menuEditar);

        JMenuItem undo = new JMenuItem("Deshacer");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        undo.addActionListener(actionEvent -> {
            textArea.setText(Content.undo());
            Content.updateSaves(1);
        });
        menuEditar.add(undo);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow window = new MainWindow();
                window.frame.setVisible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
