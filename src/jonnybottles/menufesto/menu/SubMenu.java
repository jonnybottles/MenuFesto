package jonnybottles.menufesto.menu;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SubMenu extends Menu {

    private Menu parentMenu; // The parent menu of the current menu

    public SubMenu(String menuName, Menu parentMenu, String... menuItems) {
        super(menuName, parentMenu.getProgramName(), menuItems);
        this.parentMenu = parentMenu;
        setSubMenu(true);
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    // Prints menu name centered to the console width with dashes on each side of menu name
    @Override
    public void printMenuName() {
        Utilities.clearScreen();

        int consoleWidth = getConsoleWidth(); // Obtains the console width
        String formattedMenuName = centerText(getMenuName(), consoleWidth); // Centers the menu name with dashes

        System.out.print(formattedMenuName); // Prints the centered menu name with dashes
    }

    @Override
    public int makeASelection() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                for (int i = 0; i < getHighestMenuItemNum(); i++) {
                    System.out.println((i + 1) + ") " + getMenuItems().get(i));
                }

                System.out.println(getHighestMenuItemNum() + ") Return to " + this.parentMenu.getMenuName());


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

}
