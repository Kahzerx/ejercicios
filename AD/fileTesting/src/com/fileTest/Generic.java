package com.fileTest;

import java.io.File;

public class Generic {
    public static void tryCreateDir(String dirName) {
        boolean tryCreateDir = new File(dirName).mkdirs();
        if (tryCreateDir) System.out.printf("Dir %s created.%n", dirName);
    }
}
