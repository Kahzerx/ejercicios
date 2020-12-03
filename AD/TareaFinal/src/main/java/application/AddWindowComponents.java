package application;

import javax.swing.*;
import java.awt.*;

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


    public AddWindowComponents() {
        createJLabel();
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
            default:
                throw new IllegalStateException("Unexpected value " + type);
        }
        return thing;
    }

    private void createJLabel() {
        dateLabel = (JLabel) createJThing(2, "Fecha", new int[] {10, 10, 200, 30});
        compArchLabel = (JLabel) createJThing(2, "Arquitectura compatible", new int[] {10, 40, 200, 30});
        typeLabel = (JLabel) createJThing(2, "Tipo", new int[] {10, 70, 200, 30});
        speedLabel = (JLabel) createJThing(2, "Velocidad", new int[] {10, 100, 200, 30});
        categoryLabel = (JLabel) createJThing(2, "Categoría", new int[] {10, 130, 200, 30});
        orientationLabel = (JLabel) createJThing(2, "Orientación", new int[] {10, 160, 200, 30});
        bitsLabel = (JLabel) createJThing(2, "Bits", new int[] {10, 190, 200, 30});
        numSysLabel = (JLabel) createJThing(2, "Sistema de numeración", new int[] {10, 220, 200, 30});
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
    }
}
