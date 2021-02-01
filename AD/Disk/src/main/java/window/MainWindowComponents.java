package window;

import components.tables.AlbumTable;
import components.tables.AuthorTable;
import components.tables.SongTable;
import database.BasicDataSourceConnection;
import utils.DBUtils;
import components.TextPaneLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * JFrame principal con botón de {@code connectButton} y {@code disconnectButton}, y el {@code logger}.
 */
public class MainWindowComponents extends JFrame {
    int WIDTH = 1100;
    int HEIGHT = 720;

    private final JScrollPane scrollPane;
    private final JScrollPane albumScrollPane;
    private final JScrollPane songScrollPane;
    private final JScrollPane authorScrollPane;

    private JButton connectButton;
    private JButton disconnectButton;
    private JButton clearLogButton;

    private final TextPaneLogger logger;

    private final AlbumTable albumTable;
    private final SongTable songTable;
    private final AuthorTable authorTable;

    private final BasicDataSourceConnection dataSourceConnection;

    public MainWindowComponents(String url, String name, String user, String pass) {
        logger = new TextPaneLogger();
        scrollPane = new JScrollPane(logger);

        albumTable = new AlbumTable();
        albumScrollPane = new JScrollPane(albumTable);

        songTable = new SongTable();
        songScrollPane = new JScrollPane(songTable);

        authorTable = new AuthorTable();
        authorScrollPane = new JScrollPane(authorTable);

        dataSourceConnection = new BasicDataSourceConnection(url, name, user, pass);
        createJButton();

        addStuff();

        createJFrame();

        componentBounds();
    }

    /**
     * Frame con dimensiones mínimas hardcodeadas.
     */
    public void createJFrame() {
        setBounds(100, 100, WIDTH, HEIGHT);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Ejercicio de Acceso a Datos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().setBackground(Color.WHITE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                componentBounds();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);

                try {
                    if (dataSourceConnection.connection != null && !dataSourceConnection.connection.isClosed()) {
                        dataSourceConnection.close();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                dispose();
                Runtime.getRuntime().halt(0);
            }
        });
    }

    /**
     * Botones de conexión.
     */
    private void createJButton() {
        connectButton = (JButton) createJThing(0, "Conectar");
        connectButton.addActionListener(actionEvent -> DBUtils.connect(dataSourceConnection, logger, albumTable, songTable, authorTable));

        disconnectButton = (JButton) createJThing(0, "Desconectar");
        disconnectButton.addActionListener(actionEvent -> DBUtils.disconnect(dataSourceConnection, logger, albumTable, songTable, authorTable));

        clearLogButton = (JButton) createJThing(0, "Limpiar log");
        clearLogButton.addActionListener(actionEvent -> logger.clearLog());
    }

    /**
     * Añado los componentes al Frame
     */
    private void addStuff() {
        add(connectButton);
        add(disconnectButton);
        add(scrollPane);
        add(albumScrollPane);
        add(songScrollPane);
        add(authorScrollPane);
        add(clearLogButton);
    }

    /**
     * Hago que la ventana se pueda redimensionar.
     */
    private void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        connectButton.setBounds((int) (width / 20), (int) (height / 27), (int) (width / 5.2), (int) (height / 24));
        disconnectButton.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 27), (int) (width / 5.2), (int) (height / 24));

        albumScrollPane.setBounds((int) (width / 20), (int) (height / 20 * 3), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 15)));
        songScrollPane.setBounds((int) (width / 20 * 10.5), (int) (height / 20 * 3), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 15)));
        authorScrollPane.setBounds((int) (width / 20), (int) (height / 20 * 11.5), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 20)));

        scrollPane.setBounds((int) (width / 20), (int) (height / 20) + (int) (height / 24) * 18, (int) (width - (int) (width / 5.2 / 2) * 3), (int) (height - ((height / 24) * 21)));

        clearLogButton.setBounds((int) ((width / 20) * 15.5), (int) (height / 20) + (int) (height / 24) * 19, (int) (width / 5.2), (int) (height / 24));
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
            case 0:
                thing = new JButton();
                ((JButton) thing).setFont(new Font("Arial", Font.BOLD, 15));
                ((JButton) thing).setText(text);
                break;
            case 1:
                thing = new JComboBox<>();
                ((JComboBox<?>) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value %d", type));
        }
        return thing;
    }
}
