package jonnybottles.menufesto.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private String programName; // The program name
    private String menuName; // The menu name
    private Menu parentMenu; // The parent menu of the current menu
    private Boolean isMainMenu; // Used to determine if this the main menu
    private List<String> menuItems; // A list of menu selection items

    // Constructor for all sub / non-main menu objects
    public Menu(String menuName, Menu parentMenu,  Boolean isMainMenu, String... menuItems) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.isMainMenu = isMainMenu;
        this.menuItems = new ArrayList<>(Arrays.asList(menuItems));
    }

    // Constructor for MainMenu objects, as they will have no parent menu.
    public Menu(String menuName, String programName, String... menuItems) {
        this(menuName, null, true, menuItems);
        this.programName = programName.toUpperCase();

    }

    // Prints menu name centered to the console width with dashes on each side of menu name
    public void printMenuName() {
        Utilities.clearScreen();

        int consoleWidth = getConsoleWidth(); // Obtains the console width
        String formattedMenuName = centerText(menuName, consoleWidth); // Centers the menu name with dashes

        if (isMainMenu) {
            System.out.println(centerText( programName, consoleWidth)); // Prints the program name for main menu
        }

        System.out.print(formattedMenuName); // Prints the centered menu name with dashes
    }


    public int makeASelection() {
        Scanner scanner = new Scanner(System.in);

        while (true ) {
            try {
                for (int i = 0; i < menuItems.size(); i++) {
                    System.out.println(i + ")" + menuItems.get(i));
                }

                // If this is a sub menu, provide aan option to return to the main menu
                if (!isMainMenu) {
                    System.out.println(menuItems.size() + 1 + ")" + "Return to " + this.parentMenu.menuName);
                }

                System.out.println("Please make a selection");
                int userSelection = scanner.nextInt();
                int validSelection = isValidSelection(int userSelection);

            } catch ()
        }

    }

    private boolean isValidSelection(int userSelection) {
        return
    }

    // Centers text within a specified width by adding padding with dashes '-' on both sides.
    private String centerText(String text, int width) {
        // Defines the padding character as a dash '-'
        String dash = "-";
        // Add a space on each side of the text to ensure spacing between text and padding
        text = " " + text + " ";

        // Calculates the length of the modified text
        int textLength = text.length();
        // Calculates the padding required to center the text
        int padding = (width - textLength) / 2;

        // Ensures that padding is non-negative (in case text is longer than the console width)
        if (padding < 0) {
            padding = 0;
        }

        // Creates the left-padding with dashes
        String leftPadding = dash.repeat(padding);

        // Calculates the right-padding, taking into account the length of the text and left-padding
        String rightPadding = dash.repeat(width - textLength - leftPadding.length());

        // Concatenates the left-padding, text, and right-padding to center the text within the specified width
        return leftPadding + text + rightPadding;
    }


    // Obtains the console width
    public static int getConsoleWidth() {
        // Determines the current operating system by querying the system property "os.name" and converting it to
        // lowercase.
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        String command;

        // Check if the operating system contains "win" (indicating it's a Windows system).
        if (operatingSystem.contains("win")) {
            // If it's a Windows system, set the command to "mod con" to obtain console width.
            command = "cmd.exe /c mode con";
        } else {
            // If it's not a Windows system, assume it's a Unix-based system (including Linux and macOS).
            // Set the command to query the console width using the "tput cols" command in a Bash shell.
            // The "2> /dev/tty" part is used to redirect any error output to the terminal.
            command = "bash -c tput cols 2> /dev/tty";
        }
        // Call the method executeCommandToGetConsoleWidth with the determined command and return the result.
        return executeCommandToGetConsoleWidth(command);
    }


    // Executes a system command to obtain the console width.
    private static int executeCommandToGetConsoleWidth(String command) {
        // Default console width (80 columns)
        int width = 80;
        try {
            // Executes the specified command as a system process.
            Process p = Runtime.getRuntime().exec(command);
            // Waits for the process to complete.
            p.waitFor();
            // Creates a reader to read the command's output.
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // If the command is "mode con" (Windows), and the output line contains "Columns," extract the width.
                if (command.contains("mode con") && line.contains("Columns")) {
                    width = Integer.parseInt(line.replaceAll("\\D", ""));
                }
                // If it's not a "mode con" command (Unix-based), parse the line as an integer to get the width.
                else if (!command.contains("mode con")) {
                    width = Integer.parseInt(line.trim());
                }
            }
        } catch (Exception e) {
            // Handles any exceptions that may occur during command execution.
            e.printStackTrace();
        }
        // Returns the obtained or default console width.
        return width;
    }

}




