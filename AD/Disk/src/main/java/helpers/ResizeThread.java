package helpers;

import windows.MainWindowComponents;

public class ResizeThread extends Thread {

    public boolean isRunning;
    private int tick = 0;
    MainWindowComponents mainWindowComponents;

    public ResizeThread(String name, boolean isRunning, MainWindowComponents mainWindowComponents) {
        super(name);
        this.isRunning = isRunning;
        this.mainWindowComponents = mainWindowComponents;
    }

    @Override
    public void run() {
        while (isRunning) {
            this.tick++;
            if (this.tick % 250_000_000 == 0) {
                mainWindowComponents.componentBounds();
                mainWindowComponents.revalidate();
                mainWindowComponents.repaint();
                this.tick = 0;
            }
        }
    }
}
