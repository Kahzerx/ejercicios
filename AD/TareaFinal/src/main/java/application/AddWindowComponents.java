package application;

import javax.swing.*;
import java.awt.*;

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

    private JComboBox<Integer> dd;
    private JComboBox<Integer> mm;
    private JComboBox<Integer> yy;
    private JComboBox<String> arch;
    private JComboBox<String> category;
    private JComboBox<String> orientation;
    private JComboBox<String> numSys;

    private JTextField typeField;
    private JTextField speedField;
    private JTextField bitsField;


    public AddWindowComponents() {
        createJLabel();
        createJComboBox();
        createJTextField();
        createJFrame();
    }

    private void createJFrame() {
        frame.setBounds(100, 100, 700, 500);
        frame.setLayout(new GroupLayout(frame.getContentPane()));
        addStuff();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    private void createJLabel() {
        dateLabel = (JLabel) createJThing(2, "Fecha (dd-mm-yy)", new int[] {10, 10, 200, 30});
        compArchLabel = (JLabel) createJThing(2, "Arquitectura compatible", new int[] {10, 50, 200, 30});
        typeLabel = (JLabel) createJThing(2, "Tipo", new int[] {10, 90, 200, 30});
        speedLabel = (JLabel) createJThing(2, "Velocidad (rticks)", new int[] {10, 130, 200, 30});
        categoryLabel = (JLabel) createJThing(2, "Categoría", new int[] {10, 170, 200, 30});
        orientationLabel = (JLabel) createJThing(2, "Orientación", new int[] {10, 210, 200, 30});
        bitsLabel = (JLabel) createJThing(2, "Bits", new int[] {10, 250, 200, 30});
        numSysLabel = (JLabel) createJThing(2, "Sistema de numeración", new int[] {10, 290, 200, 30});
    }

    private void createJComboBox() {
        dd = (JComboBox<Integer>) createJThing(3, "", new int[] {210, 10, 60, 30});
        mm = (JComboBox<Integer>) createJThing(3, "", new int[] {290, 10, 60, 30});
        yy = (JComboBox<Integer>) createJThing(3, "", new int[] {370, 10, 60, 30});

        arch = (JComboBox<String>) createJThing(3, "", new int[] {210, 50, 140, 30});

        category = (JComboBox<String>) createJThing(3, "", new int[] {210, 170, 140, 30});

        orientation = (JComboBox<String>) createJThing(3, "", new int[] {210, 210, 140, 30});

        numSys = (JComboBox<String>) createJThing(3, "", new int[] {210, 290, 140, 30});
    }

    private void createJTextField() {
        typeField = (JTextField) createJThing(4, "", new int[] {210, 90, 140, 30});
        speedField = (JTextField) createJThing(4, "", new int[] {210, 130, 60, 30});
        bitsField = (JTextField) createJThing(4, "", new int[] {210, 250, 60, 30});
    }

    private void addStuff() {
        frame.add(dateLabel);
        frame.add(compArchLabel);
        frame.add(typeLabel);
        frame.add(speedLabel);
        frame.add(categoryLabel);
        frame.add(orientationLabel);
        frame.add(bitsLabel);
        frame.add(numSysLabel);

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
    }
}
