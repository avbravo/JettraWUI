package io.jettra.wui.complex;

/**
 * Main dashboard class representing a 3D-styled UI.
 * It is composed of Top, Left, Center, and Footer regions.
 */
public class Dashboard {
    
    private Top top;
    private Left left;
    private Center center;
    private Footer footer;

    public Dashboard() {
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public Left getLeft() {
        return left;
    }

    public void setLeft(Left left) {
        this.left = left;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
