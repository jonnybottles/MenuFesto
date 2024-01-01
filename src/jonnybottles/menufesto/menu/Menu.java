package jonnybottles.menufesto.menu;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Menu {

    protected String programName; // The program name.
    protected Menu parentMenu; // The parent menu object.
    protected String menuName; // The menu name
    protected String menuOptionDescription; // Description displayed next to menu option
    protected LinkedHashMap<String, Menu> menuOptions; // The menu options
    protected boolean isMainMenu; // Used to check if the menu is a main or submenu
    protected String optionSelection;


    // Constructor for MainMenu objects with menu options
    public Menu(String programName, String menuName, LinkedHashMap<String, Menu> menuOptions) {
        this.programName = programName.toUpperCase();
        this.menuName = Utilities.capitalize(menuName); // Capitalizes first letter of each word in menu name.
        this.menuOptionDescription = this.menuName; // Initialize menuOptionDescription with menuName
        this.menuOptions = menuOptions;
        this.isMainMenu = true;

        // Check for reserved options "Q" and "R"
        if (menuOptions.containsKey("Q") || menuOptions.containsKey("R")) {
            handleInvalidOption("Option selections 'Q' and 'R' are reserved.");
            return;
        }
        // Adds "Exit Menu" as last option for main menus.
        addExtraMenuOptions();
    }

    // Constructor for MainMenu objects without menu options
    public Menu(String programName, String menuName) {
        this(programName, menuName, new LinkedHashMap<>());
    }

    // Constructor for SubMenu objects with menu options
    public Menu(Menu parentMenu, String menuName, LinkedHashMap<String, Menu> menuOptions) {
        this(parentMenu.programName, menuName, menuOptions);
        this.parentMenu = parentMenu;
        this.isMainMenu = false;

        // Adds "Return to [parent menu]" as second to last option for submenus.
        // Adds "Exit Menu" as last option for submenus.
        addExtraMenuOptions();
    }

    // Constructor for SubMenu objects without menu options
    public Menu(Menu parentMenu, String menuName) {
        this(parentMenu, menuName, new LinkedHashMap<>());
    }

    public String getProgramName() {
        return programName;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public String getMenuName() {
        return menuName;
    }

    // Method to set the menu option description
    public void setMenuOptionDescription(String menuOptionDescription) {
        this.menuOptionDescription = menuOptionDescription;
    }

    public String getMenuOptionDescription() {
        return menuOptionDescription;
    }

    // Checks to make sure that none of the reserved menu option characters are passed as a menu option
    private boolean isValidMenuOption(String optionSelection) {
        return !(optionSelection.equals("Q") || optionSelection.equals("R"));
    }

    // Prints error message and exits program if invalid menu options are passed.
    private void handleInvalidOption(String option, String... errorMessages) {
        System.out.println("Invalid menu option format: \"" + option + "\".");
        for (String msg : errorMessages) {
            System.out.println(msg);
        }
        System.exit(0);
    }

    // Method to add a menu option dynamically
    public void addMenuOption(String optionSelection, Menu menu) {
        if (isValidMenuOption(optionSelection)) {
            menuOptions.put(optionSelection, menu);
        } else {
            handleInvalidOption(optionSelection, "Invalid option selection.");
        }
    }

    // Adds "Exit Program" to the end of all menu options and adds "Return to [parent menu]"
    // as the second to last menu option for all submenus.
    private void addExtraMenuOptions() {
        if (!isMainMenu) {
            // Removes "Exit Program" from submenu to allow for proper placement as it is placed as the last element
            // by calling the parent constructor of a main menu
            this.menuOptions.remove("Q");
            // Add "Return to [parent menu]" option as the second-to-last option
            this.menuOptions.put("R", parentMenu);
        }

        // Add "Exit Program" as the last option for all menus, excluding the ExitMenu itself
        if (!this.getClass().equals(ExitMenu.class)) {
            this.menuOptions.put("Q", new ExitMenu(this, "Exit Menu"));
        }
    }

    // Instantiates an exitMenu object
    public void exitProgram() {
        ExitMenu theExitMenu = new ExitMenu(this, "Exit Menu");
        theExitMenu.start();
    }

    // TODO modify code to use printPrompt and inputPrompt from here
    // https://chat.openai.com/c/00075186-1067-462e-b520-9c4a3b58c148

    // Prints menu name centered to the console width with dashes on each side of menu name
    public void printMenuName() {
        Utilities.clearScreen();

        // Obtains the console width
        int consoleWidth = getConsoleWidth();

        // Centers the menu name with dashes
        String formattedMenuName = centerText(menuName, consoleWidth);

        // If this menu is the main menu, prints the program name.
        if (isMainMenu) {
            System.out.println(centerText(programName, consoleWidth));

        }
        // Prints the centered menu name with dashes
        System.out.println(formattedMenuName + "\n");
    }


    // Prints a list of menu options for the user to select from, obtains input and instantiates // calls
// selected menu.
    public void makeASelection(String msg) {
        Scanner scanner = new Scanner(System.in);
        String userSelection;

        printMenuName();
        while (true) {
            displayCustomContent();
            displayMenuOptions(msg);

            try {
                userSelection = scanner.nextLine().trim().toUpperCase();
                if (userSelection.equals("R") || userSelection.equals("Q")) {
                    handleSelection(userSelection);
                } else if (menuOptions.containsKey(userSelection)) {
                    // Call the start method of the selected menu
                    Menu selectedMenu = menuOptions.get(userSelection);
                    selectedMenu.start();
                    return; // Return after the selected menu completes
                } else {
                    Utilities.clearScreen();
                    printMenuName();
                    System.out.println("Invalid selection, please enter a valid option.\n ");
                }
                // If Ctrl+D is pressed
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
        }
    }


    // Method that can be overridden by subclasses when there is a need to display custom content
    // in makeASelection after clearing the screen, but before displaying menu optons
    protected void displayCustomContent() {
        // Empty by default
    }

    protected void displayMenuOptions(String msg) {
        menuOptions.forEach((key, value) -> {
            if (!key.equals("Q")) { // Skip displaying the "Exit Menu" option
                if ("R".equals(key)) {
                    Menu parent = value.getParentMenu();  // Get the parent menu
                    // Check if parent menu is not null before getting its description
                    String parentMenuDescription = (parent != null) ? parent.getMenuOptionDescription() : "Main Menu";
                    System.out.println("(" + key + ") Return to " + parentMenuDescription);
                } else {
                    System.out.println("(" + key + ") " + value.getMenuOptionDescription());
                }
            }
        });

        System.out.println("\n" + msg);  // Print the message passed to the method
    }



    // Method to handle the user's selection
    protected void handleSelection(String selection) {
        // Implement actions based on the selection here
        // For example, if the selection is "Q", you may want to quit the program
        if ("Q".equals(selection)) {
            exitProgram();  // Always call exitProgram for "Q", regardless of whether it's a main or submenu
        } else if ("R".equals(selection) && !isMainMenu) {
            parentMenu.start();  // Return to parent menu for "R" in submenus
        } else {
            // Handle other selections
            Menu selectedMenu = menuOptions.get(selection);
            if (selectedMenu != null) {
                selectedMenu.start();
            } else {
                System.out.println("Invalid selection, please enter a valid option.\n");
            }
        }
    }





    // Obtains a single string from the user.
    public String getString(String msg) {
        Scanner scanner = new Scanner(System.in);

        printMenuName();
        while (true) {
            System.out.println(msg);
            try {

                String userString = scanner.nextLine();
                if (isValidString(userString)) {
                    return userString;
                } else {
                    System.out.println("Please enter a valid response.");
                    try {
                        // Sleep for 1 second
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        exitProgram();

                    }
                    Utilities.clearScreen();
                    printMenuName();
                }

            } catch (NoSuchElementException e) {
                exitProgram();
            }

        }

    }


    // Obtains multiple strings from a user and returns a list of strings
    public List<String> getStrings(String msg) {
        Scanner scanner = new Scanner(System.in);
        List<String> userStrings = new ArrayList<>();

        while (true) {
            try {
                System.out.println(msg);
                String response = scanner.nextLine();

                if (response.isEmpty()) {
                    break;
                }

                userStrings.add(response);
            } catch (NoSuchElementException e) {
                exitProgram();
            }
        }

        return userStrings;
    }

    // Gets a single int from a user.
    public int getInt(String msg) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(msg);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            } catch (NoSuchElementException e) {
                exitProgram();
            }
        }
    }


    public void start() {
    }

    private static boolean isValidString(String userString) {
        // Trim the string to remove leading and trailing whitespaces to include \n\t\r etc.
        String trimmedString = userString.trim();

        // Check if the trimmed string is empty
        return !trimmedString.isEmpty();
    }


    // TODO figure out how to have handleCtrlD work in both sub and parent menus
    // TODO when Ctrl + D is detected while receiving input (e.g. in make a selection / getString)
    // TODO it currently identifies a parent / sub menu correctly. When pressing ctrld
    // TODO from a MainMenu it will exit appropriately. When doing so from a submenu
    // TODO it returns the previous menu and gets caught in an infinite loop
    public void handleCtrlD() {
        if (isMainMenu) {
            System.out.println("Exiting " + getProgramName());
            System.exit(0);
        }
        getParentMenu().start();


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




