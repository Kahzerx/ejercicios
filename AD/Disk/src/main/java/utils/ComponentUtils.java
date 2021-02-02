package utils;

import database.Query;
import windows.InsertAlbumWindow;
import windows.InsertSongWindow;
import windows.MainWindow;

import javax.swing.*;

public class ComponentUtils {
    public static void switchButtons(boolean enabled, JButton... buttons) {
        for (JButton button : buttons) {
            button.setEnabled(enabled);
        }
    }

    public static int getAction(String what) {
        int action = 0;
        switch (what) {
            case "Editar":
                action = 1;
                break;
            case "Eliminar":
                action = 2;
                break;
            case "Insertar":
                action = 3;
                break;
        }
        return action;
    }

    public static int getTable(String what) {
        int action = 0;
        switch (what) {
            case "álbum":
                action = 1;
                break;
            case "canción":
                action = 2;
                break;
            case "autor":
                action = 3;
                break;
        }
        return action;
    }

    public static void decodeWindow(int action, int table, MainWindow mainWindow) {
        switch (table) {
            case 1:
                switch (action) {
                    case 1:
                        System.out.println("editar album");
                        break;
                    case 2:
                        deleteAlbum(mainWindow);
                        break;
                    case 3:
                        insertAlbum(mainWindow);
                        break;
                }
                break;
            case 2:
                switch (action) {
                    case 1:
                        System.out.println("editar cancion");
                        break;
                    case 2:
                        System.out.println("eliminar cancion");
                        break;
                    case 3:
                        insertSong(mainWindow);
                        break;
                }
                break;
            case 3:
                switch (action) {
                    case 1:
                        System.out.println("editar autor");
                        break;
                    case 2:
                        System.out.println("eliminar autor");
                        break;
                    case 3:
                        System.out.println("insertar autor");
                        break;
                }
                break;
        }
    }

    public static void insertAlbum(MainWindow mainWindow) {
        InsertAlbumWindow albumWindow = new InsertAlbumWindow(mainWindow);
        mainWindow.switchB(false);
        albumWindow.setVisible(true);
    }

    public static void deleteAlbum(MainWindow mainWindow) {
        String name = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("title").getModelIndex());
        if (name == null || name.equals("")) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar el album %s\nSeguro que quieres continuar?", name))) {
            int id = Integer.parseInt((String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("id").getModelIndex()));
            if (!Query.deleteAlbum(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }

    public static void insertSong(MainWindow mainWindow) {
        InsertSongWindow songWindow = new InsertSongWindow(mainWindow);
        mainWindow.switchB(false);
        songWindow.setVisible(true);
    }
}
