package window;

import database.BasicDataSourceConnection;
import database.DriverManagerConnection;
import utils.LogLevel;
import utils.TextPaneLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Objects;

/**
 * JFrame principal con botón de {@code connectButton} y {@code disconnectButton}, y el {@code logger}.
 */
@SuppressWarnings("unchecked")
public class WindowComponents extends JFrame {
    private final JScrollPane scrollPane;

    private JButton connectButton;
    private JButton disconnectButton;

    private final TextPaneLogger logger;

    private JComboBox<String> connectionTypeBox;

    private final DriverManagerConnection driverManagerConnection;
    private final BasicDataSourceConnection dataSourceConnection;

    public WindowComponents(String url, String user, String pass) {
        logger = new TextPaneLogger();
        scrollPane = new JScrollPane(logger);
        createJButton();
        createComboBox();

        addStuff();

        createJFrame();

        componentBounds();

        driverManagerConnection = new DriverManagerConnection(url, user, pass);
        dataSourceConnection = new BasicDataSourceConnection(url, user, pass);
    }

    /**
     * Frame con dimensiones mínimas hardcodeadas.
     */
    private void createJFrame() {
        setBounds(100, 100, 800, 720);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Ejercicio de Acceso a Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 720));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                componentBounds();
            }
        });
    }

    /**
     * Botones de conexión.
     */
    private void createJButton() {
        connectButton = (JButton) createJThing(2, "Conectar");
        connectButton.addActionListener(actionEvent -> connect());

        disconnectButton = (JButton) createJThing(2, "Desconectar");
        disconnectButton.addActionListener(actionEvent -> disconnect());
    }

    /**
     * Conectarse dependiendo del método que quieras usar.
     */
    private void connect() {
        int type = Objects.equals(connectionTypeBox.getSelectedItem(), "BasicDataSource") ? 0 : 1;
        try {
            // Check de si debería cerrar la conexión porque ya está abierta con otro método.
            shouldClose(type);
            if (type == 0 && (dataSourceConnection.connection == null || dataSourceConnection.connection.isClosed())) {
                connectAndCreateBasicDataSource();
            }
            else if (type == 1 && (driverManagerConnection.connection == null || driverManagerConnection.connection.isClosed())) {
                connectAndCreateDriverManager();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Cierro la conexión con el close() de {@link database.GenericConnection}.
     */
    private void disconnect() {
        int type = Objects.equals(connectionTypeBox.getSelectedItem(), "BasicDataSource") ? 1 : 0;
        try {
            shouldClose(type);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Menú desplegable de distintos métodos de establecer conexión.
     */
    private void createComboBox() {
        connectionTypeBox = (JComboBox<String>) createJThing(3, "");
        connectionTypeBox.addItem("BasicDataSource");
        connectionTypeBox.addItem("DriverManager");
    }

    /**
     * Añado los componentes al Frame
     */
    private void addStuff() {
        add(connectButton);
        add(scrollPane);
        add(disconnectButton);

        add(connectionTypeBox);
    }

    /**
     * Hago que la ventana se pueda redimensionar.
     */
    private void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        connectButton.setBounds((int) (width / 20), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));
        disconnectButton.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

        connectionTypeBox.setBounds((int) (width - (int) (width / 20) - (int) (width / 5.2)), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

        scrollPane.setBounds((int) (width / 20), (int) (height / 20) + (int) (height / 24) * 18, (int) (width - (int) (width / 5.2 / 2)), (int) (height - ((height / 24) * 21)));
    }

    /**
     * Conseguir los objetos de los componentes fácilmente.
     * @param type tipo de objeto que necesito.
     * @param text texto que quiero que aparezca en el botón por ejemplo.
     * @return objeto preparado para castear.
     */
    private Object createJThing(int type, String text) {
        Object thing;
        switch (type) {
            case 2:
                thing = new JButton();
                ((JButton) thing).setFont(new Font("Arial", Font.BOLD, 15));
                ((JButton) thing).setText(text);
                break;
            case 3:
                thing = new JComboBox<>();
                ((JComboBox<?>) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value %d", type));
        }
        return thing;
    }

    /**
     * Conexión con la base de datos y queries genéricas usando {@link java.sql.DriverManager}.
     */
    private void connectAndCreateDriverManager() {
        logger.log("DM", LogLevel.INFORMATION, "Estableciendo conexión...");
        driverManagerConnection.connect();
        if (driverManagerConnection.connection != null) {
            logger.log("DM", LogLevel.SUCCESS, "Conexión establecida.");
        } else {
            logger.log("DM", LogLevel.ERROR, "Error al establecer conexión.");
            return;
        }

        logger.log("DM", LogLevel.INFORMATION, "Eliminando Bases de datos anteriores...");
        if (driverManagerConnection.deleteTables()) {
            logger.log("DM", LogLevel.SUCCESS, "Tablas eliminadas.");
        } else {
            logger.log("DM", LogLevel.ERROR, "No ha sido posible eliminar las tablas.");
            return;
        }

        logger.log("DM", LogLevel.INFORMATION, "Creando tablas...");
        if (driverManagerConnection.createTables()) {
            logger.log("DM", LogLevel.SUCCESS, "Tablas creadas.");
        } else {
            logger.log("DM", LogLevel.ERROR, "No ha sido posible crear las tablas.");
            return;
        }

        logger.log("DM", LogLevel.INFORMATION, "Añadiendo nuevos datos...");
        if (driverManagerConnection.addRows()) {
            logger.log("DM", LogLevel.SUCCESS, "Datos añadidos.");
        } else {
            logger.log("DM", LogLevel.ERROR, "No ha sido posible añadir los datos.");
        }
    }

    /**
     * Conexión con la base de datos y queries genéricas usando {@link org.apache.commons.dbcp.BasicDataSource}.
     */
    private void connectAndCreateBasicDataSource() {
        logger.log("BDS", LogLevel.INFORMATION, "Estableciendo conexión...");
        dataSourceConnection.connect();
        if (dataSourceConnection.connection != null) {
            logger.log("BDS", LogLevel.SUCCESS, "Conexión establecida.");
        } else {
            logger.log("BDS", LogLevel.ERROR, "Error al establecer conexión.");
            return;
        }

        logger.log("BDS", LogLevel.INFORMATION, "Eliminando Bases de datos anteriores...");
        if (dataSourceConnection.deleteTables()) {
            logger.log("BDS", LogLevel.SUCCESS, "Tablas eliminadas.");
        } else {
            logger.log("BDS", LogLevel.ERROR, "No ha sido posible eliminar las tablas.");
            return;
        }

        logger.log("BDS", LogLevel.INFORMATION, "Creando tablas...");
        if (dataSourceConnection.createTables()) {
            logger.log("BDS", LogLevel.SUCCESS, "Tablas creadas.");
        } else {
            logger.log("BDS", LogLevel.ERROR, "No ha sido posible crear las tablas.");
            return;
        }

        logger.log("BDS", LogLevel.INFORMATION, "Añadiendo nuevos datos...");
        if (dataSourceConnection.addRows()) {
            logger.log("BDS", LogLevel.SUCCESS, "Datos añadidos.");
        } else {
            logger.log("BDS", LogLevel.ERROR, "No ha sido posible añadir los datos.");
        }
    }

    /**
     * Cerrar la conexión.
     * @param type tipo de conexión que quieres cerrar.
     * @throws SQLException Puede que intentes cerrar una conexión que ya esté cerrada o que sea null.
     */

    private void shouldClose(int type) throws SQLException {
        if (type == 0 && driverManagerConnection.connection != null && !driverManagerConnection.connection.isClosed()) {
            logger.log("DM", LogLevel.INFORMATION, "Cerrando conexión...");
            driverManagerConnection.close();
            logger.log("DM", LogLevel.SUCCESS, "conexión cerrada.");
        } else if (type == 1 && dataSourceConnection.connection != null && !dataSourceConnection.connection.isClosed()) {
            logger.log("BDS", LogLevel.INFORMATION, "Cerrando conexión...");
            dataSourceConnection.close();
            logger.log("BDS", LogLevel.SUCCESS, "Conexión cerrada.");
        }
    }
}
