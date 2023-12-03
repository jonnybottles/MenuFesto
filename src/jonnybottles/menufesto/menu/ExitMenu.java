package jonnybottles.menufesto.menu;

import java.util.NoSuchElementException;

public class ExitMenu extends Menu {

    public ExitMenu(Menu parentMenu, String menuName) {
        super(parentMenu, menuName, "Exit Program");
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
