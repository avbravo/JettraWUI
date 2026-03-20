package io.jettra.wui.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu component that can be used anywhere in the application (e.g., Dashboard, Left menu).
 * It comprises child classes that draw the components.
 */
public class Menu {

    private List<MenuBar> menuBars = new ArrayList<>();

    public Menu() {
    }

    public void add(MenuBar menuBar) {
        menuBars.add(menuBar);
    }
    
    public List<MenuBar> getMenuBars() {
        return menuBars;
    }
}
