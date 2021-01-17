package window;

import database.BasicDataSourceConnection;
import database.DriverManagerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class WindowComponents extends JFrame{
    private JButton connectButton;
    private JButton disconnect;

    private JComboBox<String> connectionTypeBox;

    private final DriverManagerConnection driverManagerConnection;
    private final BasicDataSourceConnection dataSourceConnection;

    public WindowComponents(String url, String user, String pass) {
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

        disconnect = (JButton) createJThing(2, "Desconectar");
        disconnect.addActionListener(actionEvent -> disconnect());
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
        add(disconnect);

        add(connectionTypeBox);
    }

    private void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        connectButton.setBounds((int) (width / 20), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));
        disconnect.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

        connectionTypeBox.setBounds((int) (width - (int) (width / 20) - (int) (width / 5.2)), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));
    }

    private Object createJThing(int type, String text) {
        Object thing;
        switch (type) {
            case 0:
                thing = new JTextField();
                ((JTextField) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                ((JTextField) thing).setMargin(new Insets(1, 1, 1, 1));
                break;
            case 1:
                thing = new JLabel();
                ((JLabel) thing).setFont(new Font("Arial", Font.BOLD, 15));
                ((JLabel) thing).setText(text);
                break;
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
        driverManagerConnection.connect();
        driverManagerConnection.deleteTables();
        driverManagerConnection.createTables();
        driverManagerConnection.addRows();
    }

    private void connectAndCreateBasicDataSource() {
        dataSourceConnection.connect();
        dataSourceConnection.deleteTables();
        dataSourceConnection.createTables();
        dataSourceConnection.addRows();
    }

    private void shouldClose(int type) throws SQLException {
        if (type == 0 && driverManagerConnection.connection != null && !driverManagerConnection.connection.isClosed()) {
            System.out.println("Cerrando DriverManagerConnection");
            closeDriverManager();
        }
        else if (type == 1 && dataSourceConnection.connection != null && !dataSourceConnection.connection.isClosed()) {
            System.out.println("Cerrando BasicDataSourceConnection");
            closeBasicDataSource();
        }
    }

    private void closeDriverManager() {
        driverManagerConnection.close();
    }

    private void closeBasicDataSource() {
        dataSourceConnection.close();
    }
}
