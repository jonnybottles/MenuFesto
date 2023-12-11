package jonnybottles.menufesto.menu;

public class ExitMenu extends dev.lpa.Menu {

    private static final String RETURN_TO_PREVIOUS_MENU = "R";
    private static final String QUIT_PROGRAM = "Q";

    public ExitMenu(dev.lpa.Menu parentMenu, String menuName) {
        super(parentMenu, menuName);
    }

    public void quitOrResume() {
        while (true) {
            String userSelection = makeASelection();

            if (userSelection.equals(RETURN_TO_PREVIOUS_MENU)) {
                getParentMenu().start();
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
    protected void handleSelection(String selection) {
        if ("Q".equals(selection)) {
            actuallyExitProgram();
        } else {
            super.handleSelection(selection); // Handle other selections normally
        }
    }


    @Override
    public void start() {
        quitOrResume();
    }

}
