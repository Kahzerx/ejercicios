package application;

import management.SAX;

public class ButtonActions {
    public static void open(int stat) {
        if (stat == 0) {
            SAX.onOpenSax();
        }
    }
}
