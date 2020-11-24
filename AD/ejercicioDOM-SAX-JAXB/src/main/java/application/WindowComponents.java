package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class WindowComponents {
    public JFrame frame = new JFrame("Ejercicio de Acceso a Datos");
    private final JPanel panel = new JPanel();

    public static JTextArea textArea;
    public static JTextArea queryTextArea;
    public static JTextArea queryResultTextArea;

    private static JButton loadDOMButton;
    private static JButton loadSAXButton;
    private static JButton loadJAXBButton;

    private static JLabel titleLabel;
    private static JLabel authorLabel;
    private static JLabel publishedLabel;
    private static JLabel editorialLabel;
    private static JLabel titleUpdateLabel;
    private static JLabel queryLabel;
    private static JLabel queryResultLabel;

    private static JTextField titleAddField;
    private static JTextField authorAddField;
    private static JTextField publishedAddField;
    private static JTextField editorialAddField;
    private static JTextField titleUpdateField;

    private static JButton addButton;
    private static JButton updateTitleButton;
    private static JButton saveFileButton;
    private static JButton doQueryButton;

    private static JComboBox<String> titleComboBox;

    public WindowComponents() {
        createJPanel();

        createTextArea();

        createTopButtons();
        createSideButtons();

        createLabels();

        createFields();

        createBox();

        addComponents();

        createJFrame();
    }

    private void createJFrame() {
        frame.add(panel);
        frame.setBounds(100, 100, 1000, 720);
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
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private Object createJThing(int type, String text, int[] bounds) {
        Object thing;
        switch (type) {
            case 0:
                thing = new JButton();
                ((JButton) thing).setText(text);
                ((JButton) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JButton) thing).setFont(new Font("Arial", Font.PLAIN, 13));
                ((JButton) thing).setMargin(new Insets(1, 1, 1, 1));
                break;
            case 1:
                thing = new JLabel();
                ((JLabel) thing).setText(text);
                ((JLabel) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JLabel) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            case 2:
                thing = new JTextField();
                ((JTextField) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JTextField) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            case 3:
                thing = new JComboBox<>();
                ((JComboBox) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JComboBox) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            case 4:
                thing = new JTextArea();
                ((JTextArea) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JTextArea) thing).setMargin(new Insets(10, 10, 10, 10));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return thing;
    }

    private void createTopButtons() {
        loadDOMButton = (JButton) createJThing(0, "Mostrar contenido del DOM", new int[] {30, 25, 200, 30});
        loadDOMButton.addActionListener(actionEvent -> Actions.open(0));

        loadSAXButton = (JButton) createJThing(0, "Mostrar contenido del SAX", new int[] {400, 25, 200, 30});
        loadSAXButton.addActionListener(actionEvent -> Actions.open(1));

        loadJAXBButton = (JButton) createJThing(0, "Mostrar contenido del JAXB", new int[] {770, 25, 200, 30});
        loadJAXBButton.addActionListener(actionEvent -> Actions.open(2));
    }

    private void createJScrollPane() {
        JScrollPane scrollPane = new JScrollPane(textArea);
    }

    private void createTextArea() {
        textArea = (JTextArea) createJThing(4, "", new int[]{10, 80, 500, 600});
        textArea.setEditable(false);

        queryTextArea = (JTextArea) createJThing(4, "", new int[]{550, 430, 230, 40});
        queryTextArea.setEditable(false);

        queryResultTextArea = (JTextArea) createJThing(4, "", new int[] {550, 520, 420, 160});
        queryResultTextArea.setEditable(false);
    }

    private void createSideButtons() {
        addButton = (JButton) createJThing(0, "Añadir", new int[] {890, 132, 60, 50});
        addButton.setEnabled(false);
        addButton.addActionListener(actionEvent -> Actions.sideActions(0, new String[] {publishedAddField.getText(), titleAddField.getText(), authorAddField.getText(), editorialAddField.getText()}));

        saveFileButton = (JButton) createJThing(0, "Guardar", new int[] {700, 245, 130, 40});
        saveFileButton.setEnabled(false);
        saveFileButton.addActionListener(actionEvent -> Actions.sideActions(1, null));

        updateTitleButton = (JButton) createJThing(0, "Actualizar", new int[] {840, 335, 130, 40});
        updateTitleButton.setEnabled(false);
        updateTitleButton.addActionListener(actionEvent -> Actions.sideActions(2, new String[] {(String) titleComboBox.getSelectedItem(), titleUpdateField.getText()}));

        doQueryButton = (JButton) createJThing(0, "Consultar", new int[] {840, 430, 130, 40});
        doQueryButton.setEnabled(false);
        doQueryButton.addActionListener(actionEvent -> Actions.sideActions(3, new String[] {queryTextArea.getText()}));
    }

    private void createLabels() {
        titleLabel = (JLabel) createJThing(1, "Título", new int[] {550, 100, 50, 30});
        authorLabel = (JLabel) createJThing(1, "Autor", new int[] {550, 130, 50, 30});
        publishedLabel = (JLabel) createJThing(1, "Publicado en", new int[] {550, 160, 100, 30});
        editorialLabel = (JLabel) createJThing(1, "Editorial", new int[] {550, 190, 100, 30});
        titleUpdateLabel = (JLabel) createJThing(1, "Actualizar título", new int[] {550, 300, 150, 30});
        queryLabel = (JLabel) createJThing(1, "Consulta", new int[] {550, 400, 150, 30});
        queryResultLabel = (JLabel) createJThing(1, "Resultado", new int[] {550, 490, 150, 30});
    }

    private void createFields() {
        titleAddField = (JTextField) createJThing(2, "", new int[] {670, 100, 170, 25});
        authorAddField = (JTextField) createJThing(2, "", new int[] {670, 130, 170, 25});
        publishedAddField = (JTextField) createJThing(2, "", new int[] {670, 160, 170, 25});
        editorialAddField = (JTextField) createJThing(2, "", new int[] {670, 190, 170, 25});
        titleUpdateField = (JTextField) createJThing(2, "", new int[] {550, 360, 230, 25});
    }

    private void createBox() {
        titleComboBox = (JComboBox<String>) createJThing(3, "", new int[] {550, 330, 229, 25});
    }

    private void addComponents() {
        panel.add(textArea);
        panel.add(queryTextArea);
        panel.add(queryResultTextArea);

        panel.add(loadDOMButton);
        panel.add(loadSAXButton);
        panel.add(loadJAXBButton);
        panel.add(addButton);
        panel.add(updateTitleButton);
        panel.add(saveFileButton);
        panel.add(doQueryButton);

        panel.add(titleLabel);
        panel.add(authorLabel);
        panel.add(publishedLabel);
        panel.add(editorialLabel);
        panel.add(titleUpdateLabel);
        panel.add(queryLabel);
        panel.add(queryResultLabel);

        panel.add(titleAddField);
        panel.add(authorAddField);
        panel.add(publishedAddField);
        panel.add(editorialAddField);
        panel.add(titleUpdateField);

        panel.add(titleComboBox);
    }

    private void onQuit() {
        frame.dispose();
        Runtime.getRuntime().halt(0);
    }

    public static void resetFields() {
        titleAddField.setText("");
        authorAddField.setText("");
        publishedAddField.setText("");
        editorialAddField.setText("");
        titleUpdateField.setText("");
    }

    public static void resetTextArea() {
        textArea.setText("");
        queryResultTextArea.setText("");
        queryTextArea.setText("");
    }

    public static void updateBox(ArrayList<String[]> data) {
        titleComboBox.removeAllItems();
        titleComboBox.addItem("");
        for (String[] row : data) {
            titleComboBox.addItem(row[1]);
        }
    }

    public static void updateGui(int opt) {
        switch (opt) {
            case -1:  // Default.
            case 1:  // Una vez abierto el SAX.
            case 2:  // Una vez abierto el JAXB.
                addButton.setEnabled(false);
                saveFileButton.setEnabled(false);
                updateTitleButton.setEnabled(false);
                doQueryButton.setEnabled(false);
                queryTextArea.setEditable(false);
                break;
            case 0:  // Una vez abierto el DOM.
                addButton.setEnabled(true);
                saveFileButton.setEnabled(true);
                updateTitleButton.setEnabled(true);
                doQueryButton.setEnabled(true);
                queryTextArea.setEditable(true);
                break;
        }
    }
}
