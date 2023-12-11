package jonnybottles.menufesto.menu;
public class ExitMenu extends Menu {

    private static final String RETURN_TO_PREVIOUS_MENU = "R";
    private static final String QUIT_PROGRAM = "Q";

    public ExitMenu(Menu parentMenu, String menuName) {
        super(parentMenu, menuName);
    }

    public void quitOrResume() {
        while (true) {
            String userSelection = makeASelection();

            if (userSelection.equals(RETURN_TO_PREVIOUS_MENU)) {
                break;
            } else if (userSelection.equals(QUIT_PROGRAM)) {
                actuallyExitProgram();
                break;
            }
        }
    }

    private void actuallyExitProgram() {
        System.out.println("Exiting " + getProgramName() + "...");
        System.exit(0);
    }

    @Override
    public void start() {
        quitOrResume();
    }

}
