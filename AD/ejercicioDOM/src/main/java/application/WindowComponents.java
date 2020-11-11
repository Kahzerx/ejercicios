package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowComponents {
    public JFrame frame = new JFrame("Ejercicio de Acceso a Datos");
    private final JPanel panel = new JPanel();

    public static JTextArea textArea = new JTextArea();

    private JButton loadDOMButton;
    private JButton loadSAXButton;
    private JButton loadJAXBButton;

    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel publishedLabel;

    private JTextField titleAddField;
    private JTextField authorAddField;
    private JTextField publishedAddField;

    private JButton addButton;

    public WindowComponents() {
        createJPanel();
        createTextArea();

        createTopButtons();
        createSideButtons();

        createLabels();

        createFields();

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

    private void createTextArea() {
        textArea.setBounds(10, 80, 500, 600);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setEditable(false);
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
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return thing;
    }

    private void createTopButtons() {
        loadDOMButton = ((JButton) (createJThing(0, "Mostrar contenido del DOM", new int[] {30, 25, 200, 30})));
        loadDOMButton.addActionListener(actionEvent -> ActionButtons.onOpenDOM());

        loadSAXButton = ((JButton) (createJThing(0, "Mostrar contenido del SAX", new int[] {400, 25, 200, 30})));
        loadSAXButton.setEnabled(false);

        loadJAXBButton = ((JButton) (createJThing(0, "Mostrar contenido del JAXB", new int[] {770, 25, 200, 30})));
        loadJAXBButton.setEnabled(false);
    }

    private void createSideButtons() {
        addButton = ((JButton) (createJThing(0, "Añadir", new int[] {890, 145, 60, 50})));
    }

    private void createLabels() {
        titleLabel = ((JLabel) (createJThing(1, "Título", new int[] {550, 130, 50, 30})));
        authorLabel = ((JLabel) (createJThing(1, "Autor", new int[] {550, 160, 50, 30})));
        publishedLabel = ((JLabel) (createJThing(1, "Publicado en", new int[] {550, 190, 100, 30})));
    }

    private void createFields() {
        titleAddField = ((JTextField) (createJThing(2, "", new int[] {670, 130, 170, 25})));
        authorAddField = ((JTextField) (createJThing(2, "", new int[] {670, 160, 170, 25})));
        publishedAddField = ((JTextField) (createJThing(2, "", new int[] {670, 190, 170, 25})));
    }

    private void addComponents() {
        panel.add(textArea);

        panel.add(loadDOMButton);
        panel.add(loadSAXButton);
        panel.add(loadJAXBButton);
        panel.add(addButton);

        panel.add(titleLabel);
        panel.add(authorLabel);
        panel.add(publishedLabel);

        panel.add(titleAddField);
        panel.add(authorAddField);
        panel.add(publishedAddField);
    }

    private void onQuit() {
        frame.dispose();
        Runtime.getRuntime().halt(0);
    }
}
