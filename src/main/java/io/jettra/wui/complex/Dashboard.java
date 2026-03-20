package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;
public class Dashboard extends UIComponent {
    private Top top;
    private Left left;
    private Center center;
    private Footer footer;

    public Dashboard() {
        super("div");
        this.initialClasses = "j-dashboard";
    }

    public Top getTop() { return top; }
    public void setTop(Top top) { this.top = top; add(top); }

    public Left getLeft() { return left; }
    public void setLeft(Left left) { this.left = left; add(left); }

    public Center getCenter() { return center; }
    public void setCenter(Center center) { this.center = center; add(center); }

    public Footer getFooter() { return footer; }
    public void setFooter(Footer footer) { this.footer = footer; add(footer); }
}
