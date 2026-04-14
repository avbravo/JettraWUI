package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;

public class Menu extends UIComponent {
    public Menu() {
        super("nav");
        this.initialClasses = "j-menu j-component";
        this.setStyle("display", "flex");
        this.setStyle("gap", "20px");
        this.setStyle("padding", "15px 20px");
        this.setStyle("border-radius", "8px");
    }

    public void addItem(String label, String link) {
        UIComponent a = new UIComponent("a"){};
        a.setContent(label);
        a.setProperty("href", link);
        a.setStyle("color", "var(--jettra-text)");
        a.setStyle("text-decoration", "none");
        a.setStyle("font-weight", "bold");
        a.setStyle("transition", "color 0.3s, text-shadow 0.3s");
        a.setProperty("onmouseover", "this.style.color='var(--jettra-accent)'; this.style.textShadow='0 0 10px var(--jettra-glow)'");
        a.setProperty("onmouseout", "this.style.color='var(--jettra-text)'; this.style.textShadow='none'");
        add(a);
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
