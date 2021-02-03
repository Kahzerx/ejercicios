package helpers;

import windows.MainWindow;

/**
 * Esto es un Thread bastante prescindible con el único propósito de redimensionar la ventana principal.
 */
public class ResizeThread extends Thread {
    public boolean isRunning;  // Main flag de continuar la ejecución del thread.
    private int tick = 0;
    MainWindow mainWindow;

    public ResizeThread(String name, boolean isRunning, MainWindow mainWindow) {
        super(name);
        this.isRunning = isRunning;
        this.mainWindow = mainWindow;
    }

    /**
     * Bucle principal, cada 250M de ticks se redimensionará, parece mucho pero no.
     */
    @Override
    public void run() {
        while (isRunning) {
            this.tick++;
            if (this.tick % 250_000_000 == 0) {
                try {
                    mainWindow.componentBounds();  // Ajustar las dimensiones de los componentes.
                    mainWindow.revalidate();  // Ajusta el interior de los componentes entre otras cosas.
                    mainWindow.repaint();  // Dibujar de nuevo en el Frame.
                } catch (Exception ignored) { }
                this.tick = 0;
            }
        }
    }
}
