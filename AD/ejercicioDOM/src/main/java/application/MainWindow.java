package application;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

    public MainWindow() {
        initialize();
    }

    private JFrame frame;
    private final JPanel JPanel = new JPanel();

    private void initialize() {
        createJFrame();
    }

    private void createJFrame() {
        frame = new JFrame();

        createJPanel();

        frame.add(JPanel);
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Por si intentas cerrar la ventana con un documento no guardado abierto.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                onQuit();
            }
        });
    }

    private void createJPanel() {
        frame.getContentPane();
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 10, 550, 670);
        JPanel.setLayout(null);
        JPanel.add(textArea);
        JPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void onQuit() {
        frame.dispose();
        Runtime.getRuntime().halt(0);  // Al cerrar con un documento no guardado, si le das a que Si, no finaliza el proceso :(.
    }


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.frame.setVisible(true);
    }
}
