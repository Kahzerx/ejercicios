package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unchecked")
public class MainWindowComponents {
    public JFrame frame = new JFrame("Práctica Final Acceso a Datos");
    public static JTextArea mainTextArea;
    private JScrollPane mainScrollPane;
    private JButton loadButton;
    private JLabel editLabel;
    private JLabel editWId1Label;
    private JLabel editWA1Label;
    public static JComboBox<Integer> editWIdBox;
    public static JComboBox<String> editWCatBox;
    public static JButton applyEdit1Button;
    public static JButton saveButton;

    public MainWindowComponents() {
        createJTextArea();
        createJScrollPane();
        createButtons();
        createLabels();
        createJComboBox();

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
            case 2:
                thing = new JLabel();
                ((JLabel) thing).setText(text);
                ((JLabel) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JLabel) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            case 3:
                thing = new JComboBox<>();
                ((JComboBox<?>) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JComboBox<?>) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            default:
                throw new IllegalStateException("Unexpected value " + type);
        }
        return thing;
    }

    private void createJScrollPane() {
        mainScrollPane = new JScrollPane(mainTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setBounds(10, 60, 450, 620);
    }

    private void createJTextArea() {
        mainTextArea = (JTextArea) createJThing(0, "", new int[] {10, 10, 10, 10});
        mainTextArea.setEditable(false);
    }

    private void createButtons() {
        loadButton = (JButton) createJThing(1, "Mostrar Contenido", new int[] {130, 17, 200, 30});
        loadButton.addActionListener(actionEvent -> ButtonActions.open(0));
        applyEdit1Button = (JButton) createJThing(1, "Aplicar", new int[] {850, 130, 130, 30});
        applyEdit1Button.addActionListener(actionEvent -> ButtonActions.edit(0, new String[] {String.valueOf(editWIdBox.getSelectedItem()), (String) editWCatBox.getSelectedItem()}));
        applyEdit1Button.setEnabled(false);
        saveButton = (JButton) createJThing(1, "Guardar", new int[] {685, 180, 100, 40});
        saveButton.addActionListener(actionEvent -> ButtonActions.save());
        saveButton.setEnabled(false);
    }

    private void createLabels() {
        editLabel = (JLabel) createJThing(2, "Editar", new int[] {710, 50, 50, 30});
        editLabel.setFont(new Font("Arial", Font.BOLD, 15));
        editWId1Label = (JLabel) createJThing(2, "Categoría del elemento con ID", new int[] {480, 90, 290, 30});
        editWA1Label = (JLabel) createJThing(2, "a", new int[] {780, 90, 20, 30});
    }

    private void createJComboBox() {
        editWIdBox = (JComboBox<Integer>) createJThing(3, "", new int[] {710, 90, 60, 30});
        editWCatBox = (JComboBox<String>) createJThing(3, "", new int[] {800, 90, 180, 30});
    }

    private void addStuff() {
        frame.add(mainScrollPane);
        frame.add(loadButton);
        frame.add(editLabel);
        frame.add(editWId1Label);
        frame.add(editWIdBox);
        frame.add(editWA1Label);
        frame.add(editWCatBox);
        frame.add(applyEdit1Button);
        frame.add(saveButton);
    }

    private void onQuit() {
        frame.dispose();
        Runtime.getRuntime().halt(0);
    }

    public static void setTextArea(String data) {
        mainTextArea.setText(data);
        mainTextArea.setCaretPosition(0);
    }
}
