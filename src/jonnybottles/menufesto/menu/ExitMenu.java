package jonnybottles.menufesto.menu;

public class ExitMenu extends Menu {

    public ExitMenu(Menu parentMenu, String menuName) {
        super(parentMenu, menuName);
    }

    public void quitOrResume() {
        while (true) {
            int userSelection = makeASelection();

            if (userSelection == 1) {
                break;
            } else if (userSelection == 2) {
                actuallyExitProgram();
                break;
            }
        }
    }

    private void actuallyExitProgram() {
        System.out.println("Exiting " + getProgramName() + "...");
        System.exit(0);
    }

    public void start() {
        quitOrResume();
    }

}
