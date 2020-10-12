package application;

import fileManagement.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
 * Hacer un editor de texto en el cual se pueda escribir, abrir archivos y guardarlos.
 */

//TODO: Arraylist para ctrl + Z, miniautosaves ante determinadas hotkeys, hotkeys para determinadas acciones de archivos, zoom, información del punto en el que se está escribiendo (meh).

public class MainWindow {

    // Inicializar ventana desde el constructor
    public MainWindow() {
        initialize();
    }

    private JFrame frame;
    public static JTextArea textArea;  // Lo declaro aquí arriba para fácil acceso al contenido siempre.

    private void initialize() {
        // Configuro un JFrame en 1280x720 con un BorderLayout para colocar más elementos.
        frame = new JFrame();
        frame.setTitle("Editor de Texto");
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        // Configuro la TextArea junto con la fuente y el tamaño de letra.
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.getContentPane().add(textArea, BorderLayout.NORTH);

        // Scroll tanto lateral como vertical para visualizar el texto.
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Menu superior con otras opciones de archivos.
        JMenuBar bar = new JMenuBar();
        frame.getContentPane().add(bar, BorderLayout.NORTH);

        JMenu menuArchivo = new JMenu("Archivo");
        bar.add(menuArchivo);

        JMenuItem menuAbrirArchivo = new JMenuItem("Abrir");
        // Listener para realizar una acción al presionar cada item del menu.
        menuAbrirArchivo.addActionListener(actionEvent -> {
            try {
                FileManagement.openFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(menuAbrirArchivo);

        JMenuItem guardar = new JMenuItem("Guardar");
        guardar.addActionListener(actionEvent -> FileManagement.saveFile());
        menuArchivo.add(guardar);

        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        guardarComo.addActionListener(actionEvent -> FileManagement.createFile());
        menuArchivo.add(guardarComo);
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
