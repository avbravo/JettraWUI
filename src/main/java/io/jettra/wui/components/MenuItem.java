package io.jettra.wui.components;

/**
 * MenuItem represents elements that are added to a MenuBar.
 * It handles events such as click, double click, mouse over, etc.
 */
public class MenuItem {

    private String label;
    private Icon icon;

    public MenuItem(String label) {
        this.label = label;
    }

    public MenuItem(String label, Icon icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
