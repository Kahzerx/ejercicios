package components.tables;

import database.Query;
import utils.CustomTableFormat;
import windows.MainWindow;

import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tabla que muestra los álbumes, extiendo de una clase para tener funciones en común con las demás tablas.
 */
public class AlbumTable extends GenericTable {
    public SongTable songTable;
    public AuthorTable authorTable;
    private Connection connection;

    /**
     * Acciones al establecer conexión con esta tabla.
     * @param connection Conexión para hacer las queries de las demás tablas.
     * @param table Aquí no hace nada pero lo necesito como parámetro en las otras tablas del extends.
     */
    @Override
    public void onConnect(Connection connection, GenericTable table) {
        super.onConnect(connection, this);
        this.connection = connection;
        try {
            CustomTableFormat tbl = Query.getAlbums(connection);
            for (String column : tbl.columnNames) {
                super.model.addColumn(column);
            }

            for (String[] row : tbl.rows) {
                super.model.addRow(row);
            }

            // Funciones de álbumes a demás de activar las otras tablas.
            if (super.model.getRowCount() > 0) {
                super.selectFirst();
                authorTable.onConnect(connection, this);
                songTable.onConnect(connection, this);
                MainWindow.genericLabel1.setText("Editar álbum seleccionado");
                MainWindow.genericLabel3.setText("Insertar canción");
                MainWindow.genericLabel2.setText("Insertar autor");

                MainWindow.genericButton1.setText("Editar");
                MainWindow.genericButton1.setVisible(true);
                MainWindow.genericButton2.setText("Insertar");
                MainWindow.genericButton2.setVisible(true);
                MainWindow.genericButton3.setText("Insertar");
                MainWindow.genericButton3.setVisible(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Reseteo al cerrar las conexiones.
     */
    @Override
    public void onClosed() {
        super.onClosed();
        if (songTable != null) {
            songTable = null;
        }

        if (authorTable != null) {
            authorTable = null;
        }
    }

    /**
     * Acciones al pulsar en algún álbum.
     * @param listSelectionEvent el Event Listener.
     */
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        super.valueChanged(listSelectionEvent);
        if (listSelectionEvent.getValueIsAdjusting()) {  // Da dos pulsos, uno al presionar el click y otro al soltarlo, este es el fix.
            if (getSelectedRow() == -1) return;
            if (songTable != null && getSelectedRow() != -1) {
                songTable.onClosed();
                songTable.onConnect(connection, this);  // Refresco las conexiones cada vez que selecciono un nuevo album.
            }

            if (authorTable != null && getSelectedRow() != -1) {
                authorTable.onClosed();
                authorTable.onConnect(connection, this);
            }
        }
    }

    /**
     * A diferencia del de arriba, este solo cambia las acciones cuando detecta que cambias de tabla.
     * @param i parámetros
     * @param i1 raros
     * @param b del
     * @param b1 override
     */
    @Override
    public void changeSelection(int i, int i1, boolean b, boolean b1) {
        super.changeSelection(i, i1, b, b1);
        if (getSelectedRow() == -1) return;

        MainWindow.genericLabel1.setText("Editar álbum seleccionado");
        MainWindow.genericLabel3.setText("Insertar canción");
        MainWindow.genericLabel2.setText("Insertar autor");

        MainWindow.genericButton1.setText("Editar");
        MainWindow.genericButton1.setVisible(true);
        MainWindow.genericButton2.setText("Insertar");
        MainWindow.genericButton2.setVisible(true);
        MainWindow.genericButton3.setText("Insertar");
        MainWindow.genericButton3.setVisible(true);
    }
}
