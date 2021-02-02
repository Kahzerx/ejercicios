package windows;

import database.Query;
import org.jdesktop.swingx.JXDatePicker;
import utils.DBUtils;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class InsertSongWindow extends JFrame{
    private final MainWindow mainWindow;

    private JLabel titleLabel;
    private JLabel albumLabel;
    private JLabel dateLabel;
    private JLabel lengthLabel;
    private JLabel decimalLabel;

    private JTextArea titleTextArea;
    private JTextArea minuteTextArea;
    private JTextArea secondTextArea;

    private JXDatePicker datePicker;

    private JButton submitButton;

    private JComboBox<String> albumList;

    public InsertSongWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        createJLabel();

        createJTextArea();

        createDatePicker();

        createJButton();

        createJComboBox();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 300);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Insertar Canción");
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
        titleLabel = (JLabel) mainWindow.createJThing(2, "Título");
        titleLabel.setBounds(20, 20, 80, 30);
        albumLabel = (JLabel) mainWindow.createJThing(2, "Álbum");
        albumLabel.setBounds(20, 70, 80, 30);
        lengthLabel = (JLabel) mainWindow.createJThing(2, "Duración");
        lengthLabel.setBounds(20, 120, 80, 30);
        decimalLabel = (JLabel) mainWindow.createJThing(2, ":");
        decimalLabel.setBounds(183, 120, 10, 30);
        dateLabel = (JLabel) mainWindow.createJThing(2, "Fecha");
        dateLabel.setBounds(20, 170, 80, 30);
    }

    private void createJTextArea() {
        titleTextArea = (JTextArea) mainWindow.createJThing(3, "");
        titleTextArea.setBounds(100, 20, 170, 30);
        minuteTextArea = (JTextArea) mainWindow.createJThing(3, "");
        minuteTextArea.setBounds(100, 120, 80, 30);
        secondTextArea = (JTextArea) mainWindow.createJThing(3, "");
        secondTextArea.setBounds(190, 120, 80, 30);
    }

    private void createDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setDate(Calendar.getInstance().getTime());
        datePicker.setFormats(new SimpleDateFormat("yyyy"));
        datePicker.setBounds(100, 170, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Añadir");
        submitButton.setBounds(100, 220, 110, 30);
        submitButton.addActionListener(actionEvent -> {
            if (StringUtils.isEmpty(titleTextArea.getText(), minuteTextArea.getText(), secondTextArea.getText())) {
                JOptionPane.showMessageDialog(null, "No puede haber campos en blanco!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (!StringUtils.isInt(minuteTextArea.getText(), secondTextArea.getText())){
                JOptionPane.showMessageDialog(null, "Pon solo números en la duración!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String title = titleTextArea.getText().trim();
                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy");
                int year = Integer.parseInt(dateTimeFormatter.format(datePicker.getDate()));
                String album = (String) albumList.getSelectedItem();
                int min = Integer.parseInt(minuteTextArea.getText().trim());
                int sec = Integer.parseInt(secondTextArea.getText().trim());
                if (sec >= 60) {
                    JOptionPane.showMessageDialog(null, "Demasiados segundos...", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (minuteTextArea.getText().length() >= 5) {
                    JOptionPane.showMessageDialog(null, "Demasiados minutos...", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                float dur = Float.parseFloat(String.format("%d.%d", min, sec));
                if (!Query.insertSong(mainWindow.dataSourceConnection.connection, title, album, dur, year)) {
                    JOptionPane.showMessageDialog(null, "Error al insertar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void createJComboBox() {
        albumList = (JComboBox<String>) mainWindow.createJThing(1, "");
        albumList.setBounds(100, 70, 170, 30);
        List<String> albums = Query.getAlbumNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        for (String album : albums) {
            albumList.addItem(album);
        }
    }

    private void addStuff() {
        add(titleLabel);
        add(albumLabel);
        add(lengthLabel);
        add(dateLabel);
        add(decimalLabel);

        add(titleTextArea);
        add(minuteTextArea);
        add(secondTextArea);

        add(datePicker);

        add(submitButton);

        add(albumList);
    }

    private void close() {
        dispose();
        mainWindow.switchB(true);
    }
}
