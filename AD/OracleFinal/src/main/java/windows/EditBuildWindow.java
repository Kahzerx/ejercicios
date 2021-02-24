package windows;

import database.Query;
import org.jdesktop.swingx.JXDatePicker;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class EditBuildWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String type;
    private final int speed;
    private final String category;
    private final String orientation;
    private final int bits;
    private final String num_system;
    private final String date;

    private JLabel idLabel;
    private JLabel typeLabel;
    private JLabel speedLabel;
    private JLabel orientationLabel;
    private JLabel categoryLabel;
    private JLabel bitsLabel;
    private JLabel numSysLabel;
    private JLabel dateLabel;

    private JTextField idTextField;

    private JXDatePicker datePicker;

    private JButton submitButton;

    private JComboBox<String> catList;
    private JTextField typeTextField;
    private JTextField speedTextField;
    private JTextField orientationTextField;
    private JTextField bitsTextField;
    private JTextField numSysTextField;

    public EditBuildWindow(MainWindow mainWindow, int id, String type, int speed, String category, String orientation, int bits, String num_system, String date) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.type = type;
        this.speed = speed;
        this.category = category;
        this.orientation = orientation;
        this.bits = bits;
        this.num_system = num_system;
        this.date = date;

        createJLabel();

        createJTextField();

        createDatePicker();

        createJButton();

        createJComboBox();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 320, 500);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Editar Build");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                close();
            }
        });
    }

    private void createJLabel() {
        idLabel = (JLabel) mainWindow.createJThing(2, "ID");
        idLabel.setBounds(20, 20, 80, 30);
        typeLabel = (JLabel) mainWindow.createJThing(2, "Tipo");
        typeLabel.setBounds(20, 70, 100, 30);
        speedLabel = (JLabel) mainWindow.createJThing(2, "Velocidad");
        speedLabel.setBounds(20, 120, 100, 30);
        orientationLabel = (JLabel) mainWindow.createJThing(2, "Orientación");
        orientationLabel.setBounds(20, 170, 100, 30);
        categoryLabel = (JLabel) mainWindow.createJThing(2, "Categoría");
        categoryLabel.setBounds(20, 220, 100, 30);
        bitsLabel = (JLabel) mainWindow.createJThing(2, "Bits");
        bitsLabel.setBounds(20, 270, 100, 30);
        numSysLabel = (JLabel) mainWindow.createJThing(2, "Numeración");
        numSysLabel.setBounds(20, 320, 100, 30);
        dateLabel = (JLabel) mainWindow.createJThing(2, "Fecha");
        dateLabel.setBounds(20, 370, 100, 30);
    }

    private void createJTextField() {
        idTextField = (JTextField) mainWindow.createJThing(4, String.valueOf(this.id));
        idTextField.setBounds(130, 20, 170, 30);
        idTextField.setEditable(false);
        typeTextField = (JTextField) mainWindow.createJThing(4, type);
        typeTextField.setBounds(130, 70, 170, 30);
        speedTextField = (JTextField) mainWindow.createJThing(4, String.valueOf(speed));
        speedTextField.setBounds(130, 120, 170, 30);
        orientationTextField = (JTextField) mainWindow.createJThing(4, orientation);
        orientationTextField.setBounds(130, 170, 170, 30);
        bitsTextField = (JTextField) mainWindow.createJThing(4, String.valueOf(bits));
        bitsTextField.setBounds(130, 270, 170, 30);
        numSysTextField = (JTextField) mainWindow.createJThing(4, num_system);
        numSysTextField.setBounds(130, 320, 170, 30);
    }

    private void createDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        try {
            datePicker.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(this.date));
        } catch (ParseException e) {
            datePicker.setDate(Calendar.getInstance().getTime());
        }
        datePicker.setBounds(130, 370, 170, 30);
    }

    private void createJComboBox() {
        catList = (JComboBox<String>) mainWindow.createJThing(1, "");
        catList.setBounds(130, 220, 170, 30);
        List<String> cats = Query.getCategoryNames(mainWindow.dataSourceConnection.connection);
        assert cats != null;
        for (String album : cats) {
            catList.addItem(album);
        }
        catList.setSelectedItem(category);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 420, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            // Handle de posibles errores de usuario.
            if (StringUtils.isEmpty(typeTextField.getText(), speedTextField.getText(), Objects.requireNonNull(catList.getSelectedItem()).toString(),
                    orientationTextField.getText(), bitsTextField.getText(), numSysTextField.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (StringUtils.isNotInt(speedTextField.getText(), bitsTextField.getText())) {
                JOptionPane.showMessageDialog(null, "Tanto velocidad como bits\nson números!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String type = typeTextField.getText().trim();
                int speed = Integer.parseInt(speedTextField.getText());
                String or = orientationTextField.getText();
                String cat = Objects.requireNonNull(catList.getSelectedItem()).toString().trim();
                int bits = Integer.parseInt(bitsTextField.getText());
                String numSys = numSysTextField.getText();

                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateTimeFormatter.format(datePicker.getDate());
                if (!Query.updateBuild(mainWindow.dataSourceConnection.connection, id, type, speed, cat, or, bits, numSys, date)) {
                    JOptionPane.showMessageDialog(null, "Error al Editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(idLabel);
        add(typeLabel);
        add(speedLabel);
        add(orientationLabel);
        add(categoryLabel);
        add(bitsLabel);
        add(numSysLabel);
        add(dateLabel);

        add(idTextField);
        add(typeTextField);
        add(speedTextField);
        add(orientationTextField);
        add(bitsTextField);
        add(numSysTextField);

        add(datePicker);

        add(submitButton);

        add(catList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
