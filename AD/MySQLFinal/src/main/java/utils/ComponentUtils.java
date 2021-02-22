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
            case "categoría":
                action = 1;
                break;
            case "build":
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
                        editCat(mainWindow);
                        break;
                    case 2:
                        deleteCat(mainWindow);
                        break;
                    case 3:
                        insertCat(mainWindow);
                        break;
                }
                break;
            case 2:
                switch (action) {
                    case 1:
                        editBuild(mainWindow);
                        break;
                    case 2:
                        deleteBuild(mainWindow);
                        break;
                    case 3:
                        insertBuild(mainWindow);
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
     * Insertar category.
     */
    public static void insertCat(MainWindow mainWindow) {
        InsertCatWindow albumWindow = new InsertCatWindow(mainWindow);
        mainWindow.switchB(false);
        albumWindow.setVisible(true);
    }

    /**
     * Eliminar category.
     */
    public static void deleteCat(MainWindow mainWindow) {
        String name;

        try {
            // Extraigo los datos de la row seleccionada.
            name = (String) mainWindow.categoryTable.getValueAt(mainWindow.categoryTable.getSelectedRow(), mainWindow.categoryTable.getColumn("name").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ninguna categoría seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name == null || name.equals("")) {
            JOptionPane.showMessageDialog(null, "No hay ninguna categoría seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Desactivo los botones.
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar %s\nSeguro que quieres continuar?", name))) {
            int id = Integer.parseInt((String) mainWindow.categoryTable.getValueAt(mainWindow.categoryTable.getSelectedRow(), mainWindow.categoryTable.getColumn("id").getModelIndex()));
            if (!Query.deleteCategory(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            // Refresco la conexión para tener los datos actualizados.
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }

    /**
     * Editar category.
     */
    public static void editCat(MainWindow mainWindow) {
        String sid;
        String name;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.categoryTable.getValueAt(mainWindow.categoryTable.getSelectedRow(), mainWindow.categoryTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.categoryTable.getValueAt(mainWindow.categoryTable.getSelectedRow(), mainWindow.categoryTable.getColumn("name").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ninguna categoría seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (name == null || name.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ninguna categoría seleccionada", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        EditCatWindow editCatWindow = new EditCatWindow(mainWindow, id, name);
        mainWindow.switchB(false);
        editCatWindow.setVisible(true);
    }

    /**
     * Insertar build.
     */
    public static void insertBuild(MainWindow mainWindow) {
        InsertBuildWindow songWindow = new InsertBuildWindow(mainWindow);
        mainWindow.switchB(false);
        songWindow.setVisible(true);
    }

    /**
     * Eliminar build.
     */
    public static void deleteBuild(MainWindow mainWindow) {
        String sid;
        String type;

        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("id").getModelIndex());
            type = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("type").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún build seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((type == null || type.equals("")) || (sid == null || StringUtils.isNotInt(sid))) {
            JOptionPane.showMessageDialog(null, "No hay ningún build seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        mainWindow.switchB(false);
        if (DBUtils.confirmation(String.format("Vas a eliminar %s con ID %d\nSeguro que quieres continuar?", type, id))) {
            if (!Query.deleteBuild(mainWindow.dataSourceConnection.connection, id)) {
                JOptionPane.showMessageDialog(null, "Error al eliminar!\n", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            DBUtils.refresh(mainWindow.dataSourceConnection, mainWindow.logger, mainWindow);
        }
        mainWindow.switchB(true);
    }

    /**
     * Editar build.
     */
    public static void editBuild(MainWindow mainWindow) {
        String sid;
        String type;
        String sSpeed;
        String category;
        String orientation;
        String sBits;
        String num_system;
        String date;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("id").getModelIndex());
            type = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("type").getModelIndex());
            sSpeed = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("speed").getModelIndex());
            category = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("category").getModelIndex());
            orientation = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("orientation").getModelIndex());
            sBits = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("bits").getModelIndex());
            num_system = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("num_system").getModelIndex());
            date = (String) mainWindow.buildTable.getValueAt(mainWindow.buildTable.getSelectedRow(), mainWindow.buildTable.getColumn("date").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún build seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (type == null || type.equals(""))
                || (sSpeed == null || StringUtils.isNotInt(sSpeed) || (category == null || category.equals(""))
                || (orientation == null || orientation.equals(""))) || (sBits == null || StringUtils.isNotInt(sBits))
                || (num_system == null || num_system.equals("")) || (date == null || date.equals(""))) {
            JOptionPane.showMessageDialog(null, "No hay ningún build seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        int speed = Integer.parseInt(sSpeed);
        int bits = Integer.parseInt(sBits);
        EditBuildWindow editBuildWindow = new EditBuildWindow(mainWindow, id, type, speed, category, orientation, bits, num_system, date);
        mainWindow.switchB(false);
        editBuildWindow.setVisible(true);
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
        String sBuildID;
        try {
            // Extraigo los datos de la row seleccionada.
            sid = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("id").getModelIndex());
            name = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("name").getModelIndex());
            sBuildID = (String) mainWindow.authorTable.getValueAt(mainWindow.authorTable.getSelectedRow(), mainWindow.authorTable.getColumn("buildID").getModelIndex());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((sid == null || StringUtils.isNotInt(sid)) || (name == null || name.equals("")) || (sBuildID == null || StringUtils.isNotInt(sBuildID))) {
            JOptionPane.showMessageDialog(null, "No hay ningún autor seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(sid);
        int buildID = Integer.parseInt(sBuildID);
        EditAuthorWindow authorWindow = new EditAuthorWindow(mainWindow, id, name, buildID);
        mainWindow.switchB(false);
        authorWindow.setVisible(true);
    }
}
