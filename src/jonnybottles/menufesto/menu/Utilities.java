package jonnybottles.menufesto.menu;
import java.io.IOException;


public class Utilities {

    //TODO create prompt / auto coloring for error logs

    // Clears the screen based upon OS type
    public static void clearScreen() {
        // Attempt to clear the screen for terminal environments
        try {
            // Obtain OS name
            String operatingSystem = System.getProperty("os.name");

            // Checks for OS type and runs appropriate clear screen command.
            if (operatingSystem.contains("Windows")) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
            //TODO modify if else to look for nix. Also find a way to figure out if program is being ran in IDE
            //TODO as right now when i run this in the IDE, it detects windows and then doesn't catch the exception
            //TODO below.
        } catch (IOException | InterruptedException e) {
            // Fallback to printing new lines if the clear screen command fails
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split("\\s+"); // Split the string into words
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // Capitalize the first letter and append the rest of the word
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                capitalizedString.append(capitalizedWord).append(" ");
            }
        }

        return capitalizedString.toString().trim(); // Return the string, trimming trailing space
    }


}