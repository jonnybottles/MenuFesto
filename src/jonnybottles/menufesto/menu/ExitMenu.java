package jonnybottles.menufesto.menu;
public class ExitMenu extends Menu {

    private static final String RETURN_TO_PREVIOUS_MENU = "R";
    private static final String QUIT_PROGRAM = "Q";

    // Constructor for ExitMenu
    public ExitMenu(Menu parentMenu, String menuName) {
        super(parentMenu.getProgramName(), menuName);
        // Add option to return to the parent menu with the parent menu's name
        if (parentMenu != null) {
            this.parentMenu = parentMenu;
            menuOptions.put(RETURN_TO_PREVIOUS_MENU, parentMenu);
        }

        // Add option to exit the program
        menuOptions.put(QUIT_PROGRAM, this); // Here 'this' refers to the ExitMenu itself
    }

    // Method to actually exit the program
    private void actuallyExitProgram() {
        System.out.println("Exiting " + getProgramName() + "...");
        System.exit(0);
    }

    // Override the start method to handle the exit menu logic
    @Override
    public void start() {
        makeASelection("Please select an option");
    }


    @Override
    protected void displayMenuOptions(String msg) {
        // Display the option to return to the parent menu, if it exists
        if (parentMenu != null) {
            System.out.println("(" + RETURN_TO_PREVIOUS_MENU + ") Return to " + parentMenu.getMenuName());
        }
        // Display the option to exit the program
        System.out.println("(" + QUIT_PROGRAM + ") Exit " + Utilities.capitalize(getProgramName()));

        System.out.println("\n" + msg);
    }

    // Override handleSelection to handle specific actions for the ExitMenu
    @Override
    protected void handleSelection(String selection) {
        if (QUIT_PROGRAM.equals(selection)) {
            actuallyExitProgram(); // Call the method to exit the program if 'Q' is selected
        } else {
            super.handleSelection(selection); // Use the default handling for other selections
        }
    }

    // No additional methods or logic are required for the ExitMenu class as of this implementation.
}
