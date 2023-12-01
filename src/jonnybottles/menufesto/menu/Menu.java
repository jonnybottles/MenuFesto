package jonnybottles.menufesto.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private String programName;
    private String menuName;
    private Menu parentMenu;
    private Boolean isMainMenu;
    private List<String> menuItems;

    // Constructor with all member variables
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

    public void printMenuName() {
        Utilities.clearScreen(); // Clear the screen first

        int consoleWidth = getConsoleWidth(); // Get the console width
        String formattedMenuName = centerText(menuName, consoleWidth); // Center the menu name with dashes

        if (isMainMenu) {
            System.out.println(centerText( programName, consoleWidth)); // Print the program name for main menu
        }

        System.out.print(formattedMenuName); // Print the centered menu name with dashes
    }



private String centerText(String text, int width) {
    String dash = "-";
    // Add one space on each side of the text
    text = " " + text + " ";
    int textLength = text.length();
    int padding = (width - textLength) / 2;
    if (padding < 0) padding = 0; // In case text is longer than the console width

    // Adjust the padding to account for the spaces
    String leftPadding = dash.repeat(padding);
    String rightPadding = dash.repeat(width - textLength - leftPadding.length());

    return leftPadding + text + rightPadding;
}


    public static int getConsoleWidth() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        String command;

        if (operatingSystem.contains("win")) {
            command = "cmd.exe /c mode con";
        } else {
            command = "bash -c tput cols 2> /dev/tty";
        }

        return executeCommandToGetConsoleWidth(command);
    }

    private static int executeCommandToGetConsoleWidth(String command) {
        int width = 80; // Default width
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (command.contains("mode con") && line.contains("Columns")) {
                    width = Integer.parseInt(line.replaceAll("\\D", ""));
                } else if (!command.contains("mode con")) {
                    width = Integer.parseInt(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }
}


//    private void printMenu() {
//        System.out.printf("*** %s ***%n", getMenuName());
//        for (int i = 0; i < menuItems.size(); i++) {
//            System.out.println(i + ")" + menuItems.get(i));
//        }
//    }




