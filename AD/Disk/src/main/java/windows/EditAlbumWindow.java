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

public class EditAlbumWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String title;
    private final String date;

    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel dateLabel;

    private JTextArea idTextArea;
    private JTextArea titleTextArea;

    private JXDatePicker datePicker;

    private JButton submitButton;

    public EditAlbumWindow(MainWindow mainWindow, int id, String title, String date) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.title = title;
        this.date = date;

        createJLabel();

        createJTextArea();

        createDatePicker();

        createJButton();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 250);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Editar Album");
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
        titleLabel = (JLabel) mainWindow.createJThing(2, "Título");
        titleLabel.setBounds(20, 70, 80, 30);
        dateLabel = (JLabel) mainWindow.createJThing(2, "Fecha");
        dateLabel.setBounds(20, 120, 60, 30);
    }

    private void createJTextArea() {
        idTextArea = (JTextArea) mainWindow.createJThing(3, "");
        idTextArea.setText(String.valueOf(this.id));
        idTextArea.setBounds(100, 20, 170, 30);
        idTextArea.setEditable(false);

        titleTextArea = (JTextArea) mainWindow.createJThing(3, "");
        titleTextArea.setText(this.title);
        titleTextArea.setBounds(100, 70, 170, 30);
    }

    private void createDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        try {
            // Formateo la fecha de la base de datos.
            datePicker.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(this.date));
        } catch (ParseException e) {
            datePicker.setDate(Calendar.getInstance().getTime());
        }
        datePicker.setBounds(100, 120, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 170, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            // handle de excepciones.
            if (StringUtils.isEmpty(titleTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String title = titleTextArea.getText().trim();
                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateTimeFormatter.format(datePicker.getDate());
                if (!Query.updateAlbum(mainWindow.dataSourceConnection.connection, this.id, title, date)) {
                    JOptionPane.showMessageDialog(null, "Error al editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void addStuff() {
        add(idLabel);
        add(titleLabel);
        add(dateLabel);

        add(idTextArea);
        add(titleTextArea);

        add(datePicker);

        add(submitButton);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
