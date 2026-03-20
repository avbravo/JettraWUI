package io.jettra.wui.components;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuBar represents menu bars that can be used in any part of the application.
 */
public class MenuBar {

    private String label;
    private List<Object> items = new ArrayList<>(); // can contain MenuItem or Separator

    public MenuBar(String label) {
        this.label = label;
    }

    public void add(MenuItem menuItem) {
        items.add(menuItem);
    }
    
    public void add(Separator separator) {
        items.add(separator);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Object> getItems() {
        return items;
    }
}
