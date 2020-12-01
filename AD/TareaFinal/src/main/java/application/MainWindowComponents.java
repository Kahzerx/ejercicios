package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindowComponents {
    public JFrame frame = new JFrame("Practica Final Acceso a Datos");
    public static JTextArea mainTextArea;
    private JScrollPane mainScrollPane;
    private static JButton loadButton;

    public MainWindowComponents() {
        createJTextArea();
        createJScrollPane();
        createTopButtons();

        createJFrame();
    }

    private void createJFrame() {
        frame.setBounds(100, 100, 1000, 720);
        frame.setLayout(new GroupLayout(frame.getContentPane()));
        addStuff();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                onQuit();
            }
        });
    }

    private Object createJThing(int type, String text, int[] bounds) {
        Object thing;
        switch (type) {
            case 0:
                thing = new JTextArea();
                ((JTextArea) thing).setMargin(new Insets(bounds[0], bounds[1], bounds[2], bounds[3]));
                break;
            case 1:
                thing = new JButton();
                ((JButton) thing).setText(text);
                ((JButton) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JButton) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                ((JButton) thing).setMargin(new Insets(1, 1, 1, 1));
                break;
            default:
                throw new IllegalStateException("Unexpected value " + type);
        }
        return thing;
    }

    private void createJScrollPane() {
        mainScrollPane = new JScrollPane(mainTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setBounds(10, 60, 550, 620);
    }

    private void createJTextArea() {
        mainTextArea = (JTextArea) createJThing(0, "", new int[] {10, 10, 10, 10});
        mainTextArea.setEditable(false);
    }

    private void createTopButtons() {
        loadButton = (JButton) createJThing(1, "Mostrar Contenido", new int[] {170, 17, 200, 30});
        loadButton.addActionListener(actionEvent -> ButtonActions.open(0));
    }

    private void addStuff() {
        frame.add(mainScrollPane);
        frame.add(loadButton);
    }

    private void onQuit() {
        frame.dispose();
        Runtime.getRuntime().halt(0);
    }

    public static void textArea(String data) {
        mainTextArea.setText(data);
    }
}
