package window;

import database.BasicDataSourceConnection;
import utils.DBUtils;
import utils.TextPaneLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * JFrame principal con botón de {@code connectButton} y {@code disconnectButton}, y el {@code logger}.
 */
public class WindowComponents extends JFrame {
    private final JScrollPane scrollPane;

    private JButton connectButton;
    private JButton disconnectButton;

    private final TextPaneLogger logger;

    private final BasicDataSourceConnection dataSourceConnection;

    public WindowComponents(String url, String user, String pass) {
        logger = new TextPaneLogger();
        scrollPane = new JScrollPane(logger);
        createJButton();

        addStuff();

        createJFrame();

        componentBounds();

        dataSourceConnection = new BasicDataSourceConnection(url, user, pass);
    }

    /**
     * Frame con dimensiones mínimas hardcodeadas.
     */
    private void createJFrame() {
        setBounds(100, 100, 720, 800);
        setLayout(new GroupLayout(getContentPane()));
        setTitle("Ejercicio de Acceso a Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 800));
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
        connectButton.addActionListener(actionEvent -> DBUtils.connect(dataSourceConnection, logger));

        disconnectButton = (JButton) createJThing(2, "Desconectar");
        disconnectButton.addActionListener(actionEvent -> DBUtils.disconnect(dataSourceConnection, logger));
    }

    /**
     * Añado los componentes al Frame
     */
    private void addStuff() {
        add(connectButton);
        add(disconnectButton);
        add(scrollPane);
    }

    /**
     * Hago que la ventana se pueda redimensionar.
     */
    private void componentBounds() {
        float width = getWidth();
        float height = getHeight();

        connectButton.setBounds((int) (width / 20), (int) (height / 20), (int) (width / 5.2), (int) (height / 24));
        disconnectButton.setBounds((int) (width / 20) + (int) (width / 5.2) + 20, (int) (height / 20), (int) (width / 5.2), (int) (height / 24));

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
}
