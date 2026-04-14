package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;

public class Image extends UIComponent {
    public Image(String src, String alt) {
        super("img");
        setProperty("src", src);
        setProperty("alt", alt);
        this.setStyle("max-width", "100%");
        this.setStyle("border-radius", "8px");
        this.setStyle("box-shadow", "0 4px 15px rgba(0, 0, 0, 0.5)");
    }

    @Override
    public Image setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Image setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Image setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Image addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Image removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Image setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Image setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Image addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Image add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
