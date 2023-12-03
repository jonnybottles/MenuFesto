package jonnybottles.menufesto.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Menu {

    protected String programName; // The program name
    protected Menu parentMenu;
    protected String menuName; // The menu name
    protected List<String> menuItems; // A list of menu selection items
    protected boolean isMainMenu;
    protected int numSelections;

    // Sets all menu classes highestMenuItem + 1 to accoutn for starting menu items at 1
    // Sets all submenu classes highestMenuItem to and additional (+2) to account for the additional selection
    // to return to the parent menu

    // Constructor for MainMenu objects
    public Menu(String programName, String menuName, String... menuItems) {
        this.programName = programName.toUpperCase();
        this.menuName = menuName;
        this.menuItems = new ArrayList<>(Arrays.asList(menuItems));
        this.isMainMenu = true;
        // Add "Exit Program" as the last item for main menu
        this.menuItems.add("Exit Program");
        this.numSelections = this.menuItems.size();
    }

    // Constructor for SubMenu objects
    public Menu(Menu parentMenu, String menuName, String... menuItems) {
        this(parentMenu.getProgramName(), menuName, menuItems);
        this.parentMenu = parentMenu;
        this.isMainMenu = false;
        // Add "Return to Parent Menu" before "Exit Program"
        int exitIndex = this.menuItems.indexOf("Exit Program");
        if (exitIndex != -1) {
            this.menuItems.add(exitIndex, "Return to " + parentMenu.getMenuName());
        } else {
            this.menuItems.add("Return to " + parentMenu.getMenuName());
            this.menuItems.add("Exit Program");
        }
        this.numSelections = this.menuItems.size();
    }



    public String getProgramName() {
        return programName;
    }

    public String getMenuName() {
        return menuName;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    public int getNumSelections() {
        return numSelections;
    }

    // Prints menu name centered to the console width with dashes on each side of menu name
    public void printMenuName() {
        Utilities.clearScreen();

        int consoleWidth = getConsoleWidth(); // Obtains the console width
        String formattedMenuName = centerText(menuName, consoleWidth); // Centers the menu name with dashes

        if (isMainMenu) {
            System.out.println(centerText(programName, consoleWidth)); // Prints the program name for main menu

        }

        System.out.println(formattedMenuName + "\n"); // Prints the centered menu name with dashes
    }


    public int makeASelection() {
        Scanner scanner = new Scanner(System.in); // Do not close this scanner
        while (true) {
            displayMenuOptions();
            System.out.println("\nPlease make a selection:");
            try {
                String inputLine = scanner.nextLine();
                int userSelection = Integer.parseInt(inputLine);
                if (isValidSelection(userSelection)) {
                    return userSelection;
                } else {
                    Utilities.clearScreen();
                    System.out.println("Invalid selection, please try again.");
                }
            } catch (NumberFormatException e) {
                Utilities.clearScreen();
                System.out.println("Please enter a valid integer.");
            } catch (NoSuchElementException e) {
                exitProgram();
            }
        }
        // Do not include a scanner.close() statement here or anywhere with System.in
    }

    public void exitProgram() {
        System.out.println("Redirecting to Exit Menu...");
        ExitMenu theExitMenu = new ExitMenu(this, "Exit Menu");
        theExitMenu.start();
    }


    private void displayMenuOptions() {
        for (int i = 0; i < getNumSelections(); i++) {
            System.out.println(i + 1 + ") " + menuItems.get(i));

        }

    }


    // Determines if the users selection is within the bounds of the menuItems selections
    protected boolean isValidSelection(int userSelection) {
        return userSelection >= 1  && userSelection <= getNumSelections();
    }

    // Centers text within a specified width by adding padding with dashes '-' on both sides.
    protected String centerText(String text, int width) {
        // Add space around the text for better readability
        text = " " + text + " ";

        // Calculates the lengths for left and right padding
        int[] paddingLengths = calculatePaddingLengths(text.length(), width);

        // Creates the left and right padding strings
        String leftPadding = createPaddingString('-', paddingLengths[0]);
        String rightPadding = createPaddingString('-', paddingLengths[1]);

        // Concatenates the left-padding, text, and right-padding to center the text within the specified width
        return leftPadding + text + rightPadding;
    }

    // Calculates the left and right padding lengths required to center the text
    private int[] calculatePaddingLengths(int textLength, int totalWidth) {
        // Calculates the total padding required
        int padding = (totalWidth - textLength) / 2;
        padding = Math.max(padding, 0); // Ensure padding is not negative

        // Calculates the right padding, taking into account the left padding and text length
        int rightPadding = totalWidth - textLength - padding;

        // Returns an array containing the left and right padding lengths
        return new int[]{padding, rightPadding};
    }

    // Creates a string of a specified length using a given padding character
    private String createPaddingString(char paddingChar, int length) {
        // Creates a string filled with the padding character of the specified length
        return new String(new char[length]).replace('\0', paddingChar);
    }


    // Obtains the console width
    protected static int getConsoleWidth() {
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
    protected static int executeCommandToGetConsoleWidth(String command) {
        // Default console width (80 columns)
        int defaultWidth = 80;
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
                    defaultWidth = Integer.parseInt(line.replaceAll("\\D", ""));
                }
                // If it's not a "mode con" command (Unix-based), parse the line as an integer to get the width.
                else if (!command.contains("mode con")) {
                    defaultWidth = Integer.parseInt(line.trim());
                }
            }
        } catch (Exception e) {
            // Handles any exceptions that may occur during command execution.
            // Instead of printing the stack trace, it prints a user-friendly message and uses a default width.
            System.out.println("Error obtaining console width. Using default width: " + defaultWidth);
        }
        // Returns the obtained or default console width.
        return defaultWidth;
    }


}




