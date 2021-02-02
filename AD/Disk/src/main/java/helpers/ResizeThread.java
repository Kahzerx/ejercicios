package helpers;

import windows.MainWindow;

public class ResizeThread extends Thread {

    public boolean isRunning;
    private int tick = 0;
    MainWindow mainWindow;

    public ResizeThread(String name, boolean isRunning, MainWindow mainWindow) {
        super(name);
        this.isRunning = isRunning;
        this.mainWindow = mainWindow;
    }

    @Override
    public void run() {
        while (isRunning) {
            this.tick++;
            if (this.tick % 250_000_000 == 0) {
                try {
                    mainWindow.componentBounds();
                    mainWindow.revalidate();
                    mainWindow.repaint();
                } catch (Exception ignored) { }
                this.tick = 0;
            }
        }
    }
}
