package jonnybottles.menufesto.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

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
        this.printMenuName();
    }

    // Constructor for MainMenu objects, as they will have no parent menu.
    public Menu(String menuName, String... menuItems) {
        this(menuName, null, true, menuItems);
    }

    public void printMenuName() {
        System.out.println(menuName);
    }

//    public static int getInt() {
//        try
//    }

    private void printMenu() {
        System.out.printf("*** %s ***%n", getMenuName());
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println(i + ")" + menuItems.get(i));
        }
    }

}


