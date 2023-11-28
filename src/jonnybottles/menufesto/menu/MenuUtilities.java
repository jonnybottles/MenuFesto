package jonnybottles.menufesto.menu;

import java.io.IOException;

public class MenuUtilities {

    public static void clearScreen() {
        try {
            // Obtain the name of the operating system
            String operatingSystem = System.getProperty("os.name");

            ProcessBuilder pb;
            if (operatingSystem.contains("Windows")) {
                // Execute 'cls' command for Windows
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                // Execute 'clear' command for Unix/Linux systems
                pb = new ProcessBuilder("clear");
            }

            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor(); // Wait for the command to complete

        } catch(IOException | InterruptedException e) {
            // Print the stack trace in case of an exception
            e.printStackTrace();
        }
    }
}
