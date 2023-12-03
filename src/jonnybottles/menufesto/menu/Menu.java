package jonnybottles.menufesto.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Menu {

    private String programName; // The program name
    private String menuName; // The menu name
    private List<String> menuItems; // A list of menu selection items
    private int lowestMenuItemNum;
    private int highestMenuItemNum;
    private boolean isSubMenu;


    // Constructor for MainMenu objects, as they will have no parent menu.
    public Menu(String menuName, String programName, String... menuItems) {
        this.menuName = menuName;
        this.programName = programName.toUpperCase();
        this.menuItems = new ArrayList<>(Arrays.asList(menuItems));
        this.lowestMenuItemNum = 1;
        this.isSubMenu = false;
        // Sets all menu classes highestMenuItem + 1 to accoutn for starting menu items at 1
        // Sets all submenu classes highestMenuItem to and additional (+2) to account for the additional selection
        // to return to the parent menu
        this.highestMenuItemNum = (isSubMenu) ? this.menuItems.size()  + 2: this.menuItems.size() +1;

    }

    public String getProgramName() {
        return programName;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getLowestMenuItemNum() {
        return lowestMenuItemNum;
    }

    public int getHighestMenuItemNum() {
        return highestMenuItemNum;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    // Add a setter method for isSubMenu to allow subclasses to set it.
    protected void setSubMenu(boolean isSubMenu) {
        this.isSubMenu = isSubMenu;
    }

    // Prints menu name centered to the console width with dashes on each side of menu name
    public void printMenuName() {
        Utilities.clearScreen();

        int consoleWidth = getConsoleWidth(); // Obtains the console width
        String formattedMenuName = centerText(menuName, consoleWidth); // Centers the menu name with dashes

        System.out.println(centerText(programName, consoleWidth)); // Prints the program name for main menu

        System.out.print(formattedMenuName); // Prints the centered menu name with dashes
    }


    public int makeASelection() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                for (int i = 0; i < getHighestMenuItemNum(); i++) {
                    System.out.println((i + 1) + ") " + menuItems.get(i));
                }

                System.out.println("Please make a selection:");
                String inputLine = scanner.nextLine();

                int userSelection = Integer.parseInt(inputLine);
                if (isValidSelection(userSelection)) {
                    return userSelection;
                } else {
                    System.out.println("Invalid selection, please try again.");
                }

            } catch (NoSuchElementException e) {
                exitProgram();
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer value.");
            }
        }
    }

    protected void exitProgram() {
        ExitMenu theExitMenu = new ExitMenu("Exit Menu", this);
        theExitMenu.start();
    }

    // Determines if the users selection is within the bounds of the menuItems selections
    protected boolean isValidSelection(int userSelection) {
        return userSelection >= getLowestMenuItemNum()  && userSelection <= getHighestMenuItemNum();
    }

    // Centers text within a specified width by adding padding with dashes '-' on both sides.
    protected String centerText(String text, int width) {
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




