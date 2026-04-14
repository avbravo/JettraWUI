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

    @Override
    public Dashboard setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Dashboard setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Dashboard setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Dashboard addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Dashboard removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Dashboard setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Dashboard setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Dashboard addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Dashboard add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
