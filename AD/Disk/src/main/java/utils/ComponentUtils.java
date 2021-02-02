package utils;

import database.Query;
import windows.*;

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
                        editAlbum(mainWindow);
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
                        deleteSong(mainWindow);
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
                        deleteAuthor(mainWindow);
                        break;
                    case 3:
                        insertAuthor(mainWindow);
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
        String name;

        try {
            name = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("title").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

    public static void editAlbum(MainWindow mainWindow) {
        String sid;
        String title;
        String date;
        try {
            sid = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("id").getModelIndex());
            title = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("title").getModelIndex());
            date = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("date").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || !StringUtils.isInt(sid)) || (title == null || title.equals("")) || (date == null || date.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        EditAlbumWindow editAlbumWindow = new EditAlbumWindow(mainWindow, id, title, date);
        mainWindow.switchB(false);
        editAlbumWindow.setVisible(true);
    }

    public static void insertSong(MainWindow mainWindow) {
        InsertSongWindow songWindow = new InsertSongWindow(mainWindow);
        mainWindow.switchB(false);
        songWindow.setVisible(true);
    }

    public static void deleteSong(MainWindow mainWindow) {
        String sid;
        String name;

        try {
            sid = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("title").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((name == null || name.equals("")) || (sid == null || !StringUtils.isInt(sid))) {
            JOptionPane.showMessageDialog(null, "No hay ningúna canción seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar la canción %s con ID %d\nSeguro que quieres continuar?", name, id))) {
            if (!Query.deleteSong(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }

    public static void insertAuthor(MainWindow mainWindow) {
        InsertAuthorWindow authorWindow = new InsertAuthorWindow(mainWindow);
        mainWindow.switchB(false);
        authorWindow.setVisible(true);
    }

    public static void deleteAuthor(MainWindow mainWindow) {
        String sid;
        String name;

        try {
            sid = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("name").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((name == null || name.equals("")) || (sid == null || !StringUtils.isInt(sid))) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar el autor %s con ID %d\nSeguro que quieres continuar?", name, id))) {
            if (!Query.deleteAuthor(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }
}
