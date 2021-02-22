package windows;

import components.tables.CategoryTable;
import components.tables.AuthorTable;
import components.tables.BuildTable;
import database.BasicDataSourceConnection;
import helpers.ResizeThread;
import utils.ComponentUtils;
import utils.DBUtils;
import components.TextPaneLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * JFrame principal con botón de {@code connectButton} y {@code disconnectButton}, y el {@code logger}.
 */
public class MainWindow extends JFrame {
    int WIDTH = 1100;
    int HEIGHT = 720;

    private final JScrollPane scrollPane;
    private final JScrollPane catScrollPane;
    private final JScrollPane buildScrollPane;
    private final JScrollPane authorScrollPane;

    public JButton connectButton;
    public JButton disconnectButton;
    private JButton clearLogButton;
    public JButton insertCatButton;
    public JButton deleteCatButton;
    public static JButton genericButton1;
    public static JButton genericButton2;
    public static JButton genericButton3;

    public final TextPaneLogger logger;

    public final CategoryTable categoryTable;
    public final BuildTable buildTable;
    public final AuthorTable authorTable;

    private JLabel catLabel;
    private JLabel buildLabel;
    private JLabel authorLabel;
    public static JLabel genericLabel1;
    public static JLabel genericLabel2;
    public static JLabel genericLabel3;

    public final BasicDataSourceConnection dataSourceConnection;

    public MainWindow(String url, String name, String user, String pass) {
        logger = new TextPaneLogger();
        scrollPane = new JScrollPane(logger);

        categoryTable = new CategoryTable();
        catScrollPane = new JScrollPane(categoryTable);

        buildTable = new BuildTable();
        buildScrollPane = new JScrollPane(buildTable);

        authorTable = new AuthorTable();
        authorScrollPane = new JScrollPane(authorTable);

        dataSourceConnection = new BasicDataSourceConnection(url, name, user, pass);
        createJButton();

        createJLabel();

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
        setMinimumSize(new Dimension(WIDTH - 200, HEIGHT - 125));
        getContentPane().setBackground(Color.WHITE);

        ResizeThread resize = new ResizeThread("resizeComponents", true, this);
        resize.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                resize.isRunning = false;
                if (resize.isAlive()) {
                    try {
                        resize.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
        connectButton.addActionListener(actionEvent -> DBUtils.connect(dataSourceConnection, logger, this));

        disconnectButton = (JButton) createJThing(0, "Desconectar");
        disconnectButton.addActionListener(actionEvent -> DBUtils.disconnect(dataSourceConnection, logger, this));

        clearLogButton = (JButton) createJThing(0, "Limpiar log");
        clearLogButton.addActionListener(actionEvent -> logger.clearLog());

        insertCatButton = (JButton) createJThing(0, "Insertar categoría");
        insertCatButton.setEnabled(false);
        insertCatButton.addActionListener(actionEvent -> ComponentUtils.decodeWindow(3, 1, this));

        deleteCatButton = (JButton) createJThing(0, "Eliminar Seleccionado");
        deleteCatButton.setEnabled(false);
        deleteCatButton.addActionListener(actionEvent -> ComponentUtils.decodeWindow(2, 1, this));

        genericButton1 = (JButton) createJThing(0, "");
        genericButton1.setVisible(false);
        genericButton1.addActionListener(actionEvent -> {
            String what = genericLabel1.getText().split(" ")[0];
            String what2 = genericLabel1.getText().split(" ")[1];
            int action = ComponentUtils.getAction(what);
            int table = ComponentUtils.getTable(what2);

            if (action == 0 || table == 0) return;
            ComponentUtils.decodeWindow(action, table, this);
        });

        genericButton2 = (JButton) createJThing(0, "");
        genericButton2.setVisible(false);
        genericButton2.addActionListener(actionEvent -> {
            String what = genericLabel2.getText().split(" ")[0];
            String what2 = genericLabel2.getText().split(" ")[1];
            int action = ComponentUtils.getAction(what);
            int table = ComponentUtils.getTable(what2);

            if (action == 0 || table == 0) return;
            ComponentUtils.decodeWindow(action, table, this);
        });

        genericButton3 = (JButton) createJThing(0, "");
        genericButton3.setVisible(false);
        genericButton3.addActionListener(actionEvent -> {
            String what = genericLabel3.getText().split(" ")[0];
            String what2 = genericLabel3.getText().split(" ")[1];
            int action = ComponentUtils.getAction(what);
            int table = ComponentUtils.getTable(what2);

            if (action == 0 || table == 0) return;
            ComponentUtils.decodeWindow(action, table, this);
        });
    }

    private void createJLabel() {
        catLabel = (JLabel) createJThing(2, "Categorías");
        buildLabel = (JLabel) createJThing(2, "Builds");
        authorLabel = (JLabel) createJThing(2, "Autores");

        genericLabel1 = (JLabel) createJThing(2, "");
        genericLabel2 = (JLabel) createJThing(2, "");
        genericLabel3 = (JLabel) createJThing(2, "");
    }

    /**
     * Añado los componentes al Frame
     */
    private void addStuff() {
        add(connectButton);
        add(disconnectButton);
        add(clearLogButton);
        add(insertCatButton);
        add(deleteCatButton);
        add(genericButton1);
        add(genericButton2);
        add(genericButton3);

        add(scrollPane);
        add(catScrollPane);
        add(buildScrollPane);
        add(authorScrollPane);

        add(catLabel);
        add(buildLabel);
        add(authorLabel);
        add(genericLabel1);
        add(genericLabel2);
        add(genericLabel3);
    }

    /**
     * Hago que la ventana se pueda redimensionar.
     */
    public void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        // No tengo ni idea de qué está pasando aquí.
        connectButton.setBounds((int) (width / 20), (int) (height / 27), (int) (width / 5.2), (int) (height / 24));
        disconnectButton.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 27), (int) (width / 5.2) + 10, (int) (height / 24));

        insertCatButton.setBounds((int) (width / 20 * 10.5), (int) (height / 27), (int) (width / 5.6), (int) (height / 24));
        deleteCatButton.setBounds((int) (width / 20 * 10.5) + (int) (width / 5.2), (int) (height / 27), (int) (width - (int) (width / 5.2) * 4), (int) (height / 24));

        clearLogButton.setBounds((int) ((width / 20) * 15.5), (int) (height / 20) + (int) (height / 24) * 19, (int) (width / 5.2), (int) (height / 24));

        genericButton1.setBounds((int) (width / 20 * 10.5) + (int) (width / 5.2) + 70, (int) (height / 20 * 11.5), (int) (width - (int) (width / 5.2) * 4.5), (int) (height / 24));
        genericButton2.setBounds((int) (width / 20 * 10.5) + (int) (width / 5.2) + 70, (int) (height / 20 * 11.5) + (int) (width / 27.5), (int) (width - (int) (width / 5.2) * 4.5), (int) (height / 24));
        genericButton3.setBounds((int) (width / 20 * 10.5) + (int) (width / 5.2) + 70, (int) (height / 20 * 11.5) + (int) (width / 13.5), (int) (width - (int) (width / 5.2) * 4.5), (int) (height / 24));

        catLabel.setBounds((int) (width / 20 * 4.7), (int) (height / 20 * 2.2), (int) (width / 5.2), (int) (height / 24));
        buildLabel.setBounds((int) (width / 20 * 14), (int) (height / 20 * 2.2), (int) (width / 5.2), (int) (height / 24));
        authorLabel.setBounds((int) (width / 20 * 4.7), (int) (height / 20 * 10.8), (int) (width / 5.2), (int) (height / 24));

        genericLabel1.setBounds((int) (width / 20 * 10.5), (int) (height / 20 * 11.5), (int) (width / 5.2 * 3), (int) (height / 24));
        genericLabel2.setBounds((int) (width / 20 * 10.5), (int) (height / 20 * 11.5) + (int) (width / 27.5), (int) (width / 5.2 * 3), (int) (height / 24));
        genericLabel3.setBounds((int) (width / 20 * 10.5), (int) (height / 20 * 11.5) + (int) (width / 13.5), (int) (width / 5.2 * 3), (int) (height / 24));

        catScrollPane.setBounds((int) (width / 20), (int) (height / 20 * 3), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 15)));
        buildScrollPane.setBounds((int) (width / 20 * 10.5), (int) (height / 20 * 3), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 15)));
        authorScrollPane.setBounds((int) (width / 20), (int) (height / 20 * 11.5), (int) (width - (int) (width / 5.2) * 3), (int) (height - ((height / 24) * 20)));

        scrollPane.setBounds((int) (width / 20), (int) (height / 20) + (int) (height / 24) * 18, (int) (width - (int) (width / 5.2 / 2) * 3), (int) (height - ((height / 24) * 21)));

    }

    /**
     * Conseguir los objetos de los componentes fácilmente.
     * @param type tipo de objeto que necesito.
     * @param text texto que quiero que aparezca en el botón por ejemplo.
     * @return objeto preparado para castear.
     */
    public Object createJThing(int type, String text) {
        Object thing;
        switch (type) {
            case 0:
                thing = new JButton();
                ((JButton) thing).setFont(new Font("Arial", Font.BOLD, 14));
                ((JButton) thing).setText(text);
                break;
            case 1:
                thing = new JComboBox<>();
                ((JComboBox<?>) thing).setFont(new Font("Arial", Font.PLAIN, 15));
                break;
            case 2:
                thing = new JLabel();
                ((JLabel) thing).setFont(new Font("Arial", Font.BOLD, 14));
                ((JLabel) thing).setText(text);
                break;
            case 3:
                thing = new JTextArea();
                ((JTextArea) thing).setFont(new Font("Arial", Font.PLAIN, 14));
                ((JTextArea) thing).setMargin(new Insets(6, 6, 6, 6));
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value %d", type));
        }
        return thing;
    }

    public void switchB(boolean enable) {
        ComponentUtils.switchButtons(enable, insertCatButton, connectButton, disconnectButton, deleteCatButton, genericButton1, genericButton2, genericButton3);
    }
}
