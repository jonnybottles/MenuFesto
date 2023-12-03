package jonnybottles.menufesto.menu;

import java.util.NoSuchElementException;

public class ExitMenu extends SubMenu {

    public ExitMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu, "Exit Program");
    }

    public void quitOrResume() {

        while (true) {
            try {
                int userSelection = makeASelection();
                if (userSelection == 1) {
                    actuallyExitProgram();
                } else {
                    break;
                }
            } catch  (NoSuchElementException e) {
                actuallyExitProgram();
            }
        }

    }

    private void actuallyExitProgram() {
        System.out.println("Exiting " + getProgramName() + "...");
        System.exit(0);
    }

    // Any other methods specific to QuitMenu
    public void start() {
        quitOrResume();
    }
}
