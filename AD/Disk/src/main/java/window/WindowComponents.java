package window;

import database.BasicDataSourceConnection;
import database.DriverManagerConnection;
import utils.LogLevel;
import utils.TextAreaLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class WindowComponents extends JFrame{
    private JButton connectButton;
    private JButton disconnectButton;

    private final TextAreaLogger logger;

    private JComboBox<String> connectionTypeBox;

    private final DriverManagerConnection driverManagerConnection;
    private final BasicDataSourceConnection dataSourceConnection;

    public WindowComponents(String url, String user, String pass) {
        logger = new TextAreaLogger();
        createJButton();
        createComboBox();

        addStuff();

        createJFrame();

        componentBounds();

        driverManagerConnection = new DriverManagerConnection(url, user, pass);
        dataSourceConnection = new BasicDataSourceConnection(url, user, pass);
    }

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

    private void createJButton() {
        connectButton = (JButton) createJThing(2, "Conectar");
        connectButton.addActionListener(actionEvent -> connect());

        disconnectButton = (JButton) createJThing(2, "Desconectar");
        disconnectButton.addActionListener(actionEvent -> disconnect());
    }

    private void connect() {
        int type = Objects.equals(connectionTypeBox.getSelectedItem(), "BasicDataSource") ? 0 : 1;
        try {
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

    private void disconnect() {
        int type = Objects.equals(connectionTypeBox.getSelectedItem(), "BasicDataSource") ? 1 : 0;
        try {
            shouldClose(type);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createComboBox() {
        connectionTypeBox = (JComboBox<String>) createJThing(3, "");
        connectionTypeBox.addItem("BasicDataSource");
        connectionTypeBox.addItem("DriverManager");
    }

    private void addStuff() {
        add(connectButton);
        add(logger);
        add(disconnectButton);

        add(connectionTypeBox);
    }

    private void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        connectButton.setBounds((int) (width / 20), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));
        disconnectButton.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

        connectionTypeBox.setBounds((int) (width - (int) (width / 20) - (int) (width / 5.2)), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

        logger.setBounds((int) (width / 20), (int) (height / 20) + (int) (height / 24) + 40, (int) (width - (int) ((width / 5.2) * 1.8)), (int) (height - ((height / 24) * 6)));
    }

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

    private void connectAndCreateDriverManager() {
        logger.log(LogLevel.INFORMATION, "Estableciendo conexión...");
        driverManagerConnection.connect();
        if (driverManagerConnection.connection == null) {
            logger.log(LogLevel.ERROR, "Error al establecer conexión.");
            return;
        } else {
            logger.log(LogLevel.SUCCESS, "Conexión establecida.");
        }

        logger.log(LogLevel.INFORMATION, "Eliminando Bases de datos anteriores...");
        driverManagerConnection.deleteTables();
        logger.log(LogLevel.SUCCESS, "Tablas eliminadas.");

        logger.log(LogLevel.INFORMATION, "Creando tablas...");
        driverManagerConnection.createTables();
        logger.log(LogLevel.SUCCESS, "Tablas creadas.");

        logger.log(LogLevel.INFORMATION, "Añadiendo nuevos datos...");
        driverManagerConnection.addRows();
        logger.log(LogLevel.SUCCESS, "Datos añadidos.");
    }

    private void connectAndCreateBasicDataSource() {
        logger.log(LogLevel.INFORMATION, "Estableciendo conexión...");
        dataSourceConnection.connect();
        if (dataSourceConnection.connection == null) {
            logger.log(LogLevel.ERROR, "Error al establecer conexión.");
            return;
        } else {
            logger.log(LogLevel.SUCCESS, "Conexión establecida.");
        }

        logger.log(LogLevel.INFORMATION, "Eliminando Bases de datos anteriores...");
        dataSourceConnection.deleteTables();
        logger.log(LogLevel.SUCCESS, "Tablas eliminadas.");

        logger.log(LogLevel.INFORMATION, "Creando tablas...");
        dataSourceConnection.createTables();
        logger.log(LogLevel.SUCCESS, "Tablas creadas.");

        logger.log(LogLevel.INFORMATION, "Añadiendo nuevos datos...");
        dataSourceConnection.addRows();
        logger.log(LogLevel.SUCCESS, "Datos añadidos.");
    }

    private void shouldClose(int type) throws SQLException {
        if (type == 0 && driverManagerConnection.connection != null && !driverManagerConnection.connection.isClosed()) {
            logger.log(LogLevel.INFORMATION, "Cerrando DriverManagerConnection...");
            closeDriverManager();
            logger.log(LogLevel.SUCCESS, "DriverManagerConnection cerrado.");
        }
        else if (type == 1 && dataSourceConnection.connection != null && !dataSourceConnection.connection.isClosed()) {
            logger.log(LogLevel.INFORMATION, "Cerrando BasicDataSourceConnection...");
            closeBasicDataSource();
            logger.log(LogLevel.SUCCESS, "BasicDataSourceConnection cerrado.");
        }
    }

    private void closeDriverManager() {
        driverManagerConnection.close();
    }

    private void closeBasicDataSource() {
        dataSourceConnection.close();
    }
}
