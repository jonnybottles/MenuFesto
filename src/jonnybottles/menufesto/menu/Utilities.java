package jonnybottles.menufesto.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilities {

    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");

            ProcessBuilder pb;
            if (operatingSystem.contains("Windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
