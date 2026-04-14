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

    @Override
    public Menu setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Menu setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Menu setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Menu addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Menu removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Menu setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Menu setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Menu addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Menu add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
