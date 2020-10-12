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

    private void initialize() {
        // Configuro un JFrame en 1280x720 con un BorderLayout para colocar más elementos.
        frame = new JFrame();
        frame.setTitle("Editor de Texto");
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        // Configuro la TextArea junto con la fuente y el tamaño de letra.
        Variables.textArea = new JTextArea();
        Variables.textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        frame.getContentPane().add(Variables.textArea, BorderLayout.NORTH);

        // Scroll tanto lateral como vertical para visualizar el texto.
        JScrollPane scrollPane = new JScrollPane(Variables.textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Menu superior para desplegables.
        JMenuBar bar = new JMenuBar();
        frame.getContentPane().add(bar, BorderLayout.NORTH);

        // Desplegable de "Archivo".
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
        guardar.addActionListener(actionEvent -> {
            try {
                FileManagement.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(guardar);

        JMenuItem guardarComo = new JMenuItem("Guardar como...");
        guardarComo.addActionListener(actionEvent -> {
            try {
                FileManagement.createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuArchivo.add(guardarComo);

        JMenuItem cerrar = new JMenuItem("Cerrar");
        cerrar.addActionListener(actionEvent -> {
            FileManagement.close();
        });
        menuArchivo.add(cerrar);
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
