package application;

import utils.ComponentCat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unchecked")
public class AddWindowComponents {
    public JFrame frame = new JFrame("Añadir");
    private JLabel dateLabel;
    private JLabel compArchLabel;
    private JLabel typeLabel;
    private JLabel speedLabel;
    private JLabel categoryLabel;
    private JLabel orientationLabel;
    private JLabel bitsLabel;
    private JLabel numSysLabel;
    private JLabel authorLabel;

    private JComboBox<String> dd;
    private JComboBox<String> mm;
    private JComboBox<String> yy;
    private JComboBox<String> arch;
    private JComboBox<String> category;
    private JComboBox<String> orientation;
    private JComboBox<String> numSys;

    private JTextField typeField;
    private JTextField speedField;
    private JTextField bitsField;
    public static JTextField authorField;

    private JButton addAuthorButton;
    private JButton addButton;

    public static JTextArea authorTextArea;

    private JScrollPane authorScrollPane;


    public AddWindowComponents() {
        createJTextArea();
        createJScrollPane();
        createJButton();
        createJLabel();
        createJComboBox();
        initializeComboBox();
        createJTextField();
        createJFrame();
    }

    private void createJFrame() {
        frame.setBounds(100, 100, 700, 460);
        frame.setLayout(new GroupLayout(frame.getContentPane()));
        addStuff();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                onQuit();
            }

            private void onQuit() {
                frame.dispose();
                MainWindowComponents.addButton.setEnabled(true);
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
            case 4:
                thing = new JTextField();
                ((JTextField) thing).setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
                ((JTextField) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                ((JTextField) thing).setMargin(new Insets(1, 1, 1, 1));
                break;
            default:
                throw new IllegalStateException("Unexpected value " + type);
        }
        return thing;
    }

    private void createJScrollPane() {
        authorScrollPane = new JScrollPane(authorTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        authorScrollPane.setBounds(450, 10, 240, 350);
    }

    private void createJTextArea() {
        authorTextArea = (JTextArea) createJThing(0, "", new int[] {10, 10, 10, 10});
        authorTextArea.setEditable(false);
    }

    private void createJButton() {
        addAuthorButton = (JButton) createJThing(1, "-->", new int[] {370, 330, 60, 30});
        addAuthorButton.addActionListener(actionEvent -> ButtonActions.addAuthor(authorTextArea.getText(), authorField.getText()));

        addButton = (JButton) createJThing(1, "Añadir", new int[] {300, 380, 100, 30});
        addButton.addActionListener(actionEvent -> ButtonActions.add(new String[] {(String) dd.getSelectedItem(), (String) mm.getSelectedItem(), (String) yy.getSelectedItem()},
                (String) arch.getSelectedItem(), typeField.getText().trim(), speedField.getText().trim(), (String) category.getSelectedItem(),
                (String) orientation.getSelectedItem(), bitsField.getText().trim(), (String) numSys.getSelectedItem(), authorTextArea.getText().trim()));
    }

    private void createJLabel() {
        dateLabel = (JLabel) createJThing(2, "Fecha (dd-mm-yy)", new int[] {10, 10, 200, 30});
        compArchLabel = (JLabel) createJThing(2, "Arquitectura compatible", new int[] {10, 50, 200, 30});
        typeLabel = (JLabel) createJThing(2, "Tipo", new int[] {10, 90, 200, 30});
        speedLabel = (JLabel) createJThing(2, "Velocidad (rticks)", new int[] {10, 130, 200, 30});
        categoryLabel = (JLabel) createJThing(2, "Categoría", new int[] {10, 170, 200, 30});
        orientationLabel = (JLabel) createJThing(2, "Orientación", new int[] {10, 210, 200, 30});
        bitsLabel = (JLabel) createJThing(2, "Bits", new int[] {10, 250, 200, 30});
        numSysLabel = (JLabel) createJThing(2, "Sistema de numeración", new int[] {10, 290, 200, 30});
        authorLabel = (JLabel) createJThing(2, "Autor", new int[] {10, 330, 200, 30});
    }

    private void createJComboBox() {
        dd = (JComboBox<String>) createJThing(3, "", new int[] {210, 10, 60, 30});
        mm = (JComboBox<String>) createJThing(3, "", new int[] {290, 10, 60, 30});
        yy = (JComboBox<String>) createJThing(3, "", new int[] {370, 10, 60, 30});

        arch = (JComboBox<String>) createJThing(3, "", new int[] {210, 50, 140, 30});

        category = (JComboBox<String>) createJThing(3, "", new int[] {210, 170, 140, 30});

        orientation = (JComboBox<String>) createJThing(3, "", new int[] {210, 210, 140, 30});

        numSys = (JComboBox<String>) createJThing(3, "", new int[] {210, 290, 140, 30});
    }

    private void createJTextField() {
        typeField = (JTextField) createJThing(4, "", new int[] {210, 90, 140, 30});
        speedField = (JTextField) createJThing(4, "", new int[] {210, 130, 60, 30});
        bitsField = (JTextField) createJThing(4, "", new int[] {210, 250, 60, 30});
        authorField = (JTextField) createJThing(4, "", new int[] {210, 330, 140, 30});
    }

    private void addStuff() {
        frame.add(authorScrollPane);

        frame.add(addAuthorButton);
        frame.add(addButton);

        frame.add(dateLabel);
        frame.add(compArchLabel);
        frame.add(typeLabel);
        frame.add(speedLabel);
        frame.add(categoryLabel);
        frame.add(orientationLabel);
        frame.add(bitsLabel);
        frame.add(numSysLabel);
        frame.add(authorLabel);

        frame.add(dd);
        frame.add(mm);
        frame.add(yy);
        frame.add(arch);
        frame.add(category);
        frame.add(orientation);
        frame.add(numSys);

        frame.add(typeField);
        frame.add(speedField);
        frame.add(bitsField);
        frame.add(authorField);
    }

    private void initializeComboBox() {
        for (int i = 1; i <= 31; i++) {
            String e = i < 10 ? "0" + i : String.valueOf(i);
            dd.addItem(e);
            if (i < 13) mm.addItem(e);
            if (i < 21) yy.addItem(e);
        }

        for (String arch : new String[] {"CCA", "ICA", "RCA"}) {
            this.arch.addItem(arch);
        }

        for (String cat : ComponentCat.categories) {
            category.addItem(cat);
        }

        for (String orientation : new String[] {"Vertical", "Diagonal", "Horizontal"}) {
            this.orientation.addItem(orientation);
        }

        for (String numSys : new String[] {"Binary", "Hexadecimal"}) {
            this.numSys.addItem(numSys);
        }
    }
}
