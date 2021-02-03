package utils;

import database.Query;
import windows.*;

import javax.swing.*;

public class ComponentUtils {
    /**
     * Hacer que los botones estén clickables o no.
     * @param enabled Si los quiero habilitados.
     * @param buttons Botones a los que afecto.
     */
    public static void switchButtons(boolean enabled, JButton... buttons) {
        for (JButton button : buttons) {
            button.setEnabled(enabled);
        }
    }

    /**
     * Acción de botones de editar, eliminar o insertar.
     * @param what Qué acción.
     * @return La acción en forma de integer.
     */
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

    /**
     * Tabla hacia donde va la acción anterior.
     * @param what Qué tabla.
     * @return La tabla en forma de integer.
     */
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

    /**
     * Decodificar lo anterior.
     * @param action Acción de editar, eliminar o insertar.
     * @param table Tabla a la que afecta.
     * @param mainWindow Ventana principal.
     */
    public static void decodeWindow(int action, int table, MainWindow mainWindow) {
        // Yo creo que se entiende la matrix los métodos son autodescriptivos, ¯\_(ツ)_/¯.
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
                        editSong(mainWindow);
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
                        editAuthor(mainWindow);
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

    /**
     * Insertar álbum.
     */
    public static void insertAlbum(MainWindow mainWindow) {
        InsertAlbumWindow albumWindow = new InsertAlbumWindow(mainWindow);
        mainWindow.switchB(false);
        albumWindow.setVisible(true);
    }

    /**
     * Eliminar álbum.
     */
    public static void deleteAlbum(MainWindow mainWindow) {
        String name;

        try {
            // Extraigo los datos de la row seleccionada.
            name = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("title").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name == null || name.equals("")) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Desactivo los botones.
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar el album %s\nSeguro que quieres continuar?", name))) {
            int id = Integer.parseInt((String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("id").getModelIndex()));
            if (!Query.deleteAlbum(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            // Refresco la conexión para tener los datos actualizados.
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }

    /**
     * Editar álbum.
     */
    public static void editAlbum(MainWindow mainWindow) {
        String sid;
        String title;
        String date;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("id").getModelIndex());
            title = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("title").getModelIndex());
            date = (String) mainWindow.albumTable.getValueAt(mainWindow.albumTable.getSelectedRow(), mainWindow.albumTable.getColumn("date").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (title == null || title.equals("")) || (date == null || date.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ningún álbum seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        EditAlbumWindow editAlbumWindow = new EditAlbumWindow(mainWindow, id, title, date);
        mainWindow.switchB(false);
        editAlbumWindow.setVisible(true);
    }

    /**
     * Insertar canción.
     */
    public static void insertSong(MainWindow mainWindow) {
        InsertSongWindow songWindow = new InsertSongWindow(mainWindow);
        mainWindow.switchB(false);
        songWindow.setVisible(true);
    }

    /**
     * Eliminar canción.
     */
    public static void deleteSong(MainWindow mainWindow) {
        String sid;
        String name;

        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("title").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ninguna canción seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((name == null || name.equals("")) || (sid == null || StringUtils.isNotInt(sid))) {
            JOptionPane.showMessageDialog(null, "No hay ninguna canción seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Editar canción.
     */
    public static void editSong(MainWindow mainWindow) {
        String sid;
        String title;
        String album;
        String duration;
        String year;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("id").getModelIndex());
            title = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("title").getModelIndex());
            album = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("album").getModelIndex());
            duration = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("duration").getModelIndex());
            year = (String) mainWindow.songTable.getValueAt(mainWindow.songTable.getSelectedRow(), mainWindow.songTable.getColumn("year").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ninguna canción seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (title == null || title.equals("")) || (album == null || album.equals("")) || (duration == null || duration.equals("")) || (year == null || year.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ninguna canción seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        EditSongWindow editSongWindow = new EditSongWindow(mainWindow, id, title, album, duration, year);
        mainWindow.switchB(false);
        editSongWindow.setVisible(true);
    }

    /**
     * Insertar autor.
     */
    public static void insertAuthor(MainWindow mainWindow) {
        InsertAuthorWindow authorWindow = new InsertAuthorWindow(mainWindow);
        mainWindow.switchB(false);
        authorWindow.setVisible(true);
    }

    /**
     * Eliminar autor.
     */
    public static void deleteAuthor(MainWindow mainWindow) {
        String sid;
        String name;

        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("name").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((name == null || name.equals("")) || (sid == null || StringUtils.isNotInt(sid))) {
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

    /**
     * Editar autor.
     */
    public static void editAuthor(MainWindow mainWindow) {
        String sid;
        String name;
        String album;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("name").getModelIndex());
            album = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("album").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (name == null || name.equals("")) || (album == null || album.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        EditAuthorWindow authorWindow = new EditAuthorWindow(mainWindow, id, name, album);
        mainWindow.switchB(false);
        authorWindow.setVisible(true);
    }
}
