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

@SuppressWarnings("unchecked")
public class EditSongWindow extends JFrame {
    private final MainWindow mainWindow;
    private final int id;
    private final String title;
    private final String album;
    private final String duration;
    private final String year;

    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel albumLabel;
    private JLabel dateLabel;
    private JLabel lengthLabel;
    private JLabel decimalLabel;

    private JTextArea idTextArea;
    private JTextArea titleTextArea;
    private JTextArea minuteTextArea;
    private JTextArea secondTextArea;

    private JXDatePicker datePicker;

    private JButton submitButton;

    private JComboBox<String> albumList;

    public EditSongWindow(MainWindow mainWindow, int id, String title, String album, String duration, String year) {
        this.mainWindow = mainWindow;
        this.id = id;
        this.title = title;
        this.album = album;
        this.duration = duration;
        this.year = year;

        createJLabel();

        createJTextArea();

        createDatePicker();

        createJButton();

        createJComboBox();

        addStuff();

        createJFrame();
    }

    public void createJFrame() {
        setBounds(100, 100, 300, 350);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Editar Canción");
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
        albumLabel = (JLabel) mainWindow.createJThing(2, "Álbum");
        albumLabel.setBounds(20, 120, 80, 30);
        lengthLabel = (JLabel) mainWindow.createJThing(2, "Duración");
        lengthLabel.setBounds(20, 170, 80, 30);
        decimalLabel = (JLabel) mainWindow.createJThing(2, ":");
        decimalLabel.setBounds(183, 170, 10, 30);
        dateLabel = (JLabel) mainWindow.createJThing(2, "Fecha");
        dateLabel.setBounds(20, 220, 80, 30);
    }

    private void createJTextArea() {
        idTextArea = (JTextArea) mainWindow.createJThing(3, "");
        idTextArea.setText(String.valueOf(this.id));
        idTextArea.setBounds(100, 20, 170, 30);
        idTextArea.setEditable(false);
        titleTextArea = (JTextArea) mainWindow.createJThing(3, "");
        titleTextArea.setText(this.title);
        titleTextArea.setBounds(100, 70, 170, 30);
        minuteTextArea = (JTextArea) mainWindow.createJThing(3, "");
        minuteTextArea.setText(duration.split("\\.")[0]);
        minuteTextArea.setBounds(100, 170, 80, 30);
        secondTextArea = (JTextArea) mainWindow.createJThing(3, "");
        secondTextArea.setText(duration.split("\\.")[1]);
        secondTextArea.setBounds(190, 170, 80, 30);
    }

    private void createDatePicker() {
        datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("yyyy"));
        try {
            datePicker.setDate(new SimpleDateFormat("yyyy").parse(this.year));
        } catch (ParseException e) {
            datePicker.setDate(Calendar.getInstance().getTime());
        }
        datePicker.setBounds(100, 220, 170, 30);
    }

    private void createJButton() {
        submitButton = (JButton) mainWindow.createJThing(0, "Editar");
        submitButton.setBounds(100, 270, 110, 30);
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
                if (!Query.updateSong(mainWindow.dataSourceConnection.connection, id, title, album, dur, year)) {
                    JOptionPane.showMessageDialog(null, "Error al Editar!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
                close();
            }
        });
    }

    private void createJComboBox() {
        albumList = (JComboBox<String>) mainWindow.createJThing(1, "");
        albumList.setBounds(100, 120, 170, 30);
        List<String> albums = Query.getAlbumNames(mainWindow.dataSourceConnection.connection);
        assert albums != null;
        for (String album : albums) {
            albumList.addItem(album);
        }
        albumList.setSelectedItem(this.album);
    }

    private void addStuff() {
        add(idLabel);
        add(titleLabel);
        add(albumLabel);
        add(lengthLabel);
        add(dateLabel);
        add(decimalLabel);

        add(idTextArea);
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
