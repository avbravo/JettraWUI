package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * IndexUI is a component that generates the <indexui> tag.
 */
public class IndexUI extends UIComponent {

    public IndexUI() {
        super("indexui");
        this.initialClasses = "j-indexui";
    }

    @Override
    public IndexUI setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public IndexUI setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public IndexUI setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public IndexUI addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public IndexUI removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public IndexUI setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public IndexUI setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public IndexUI addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public IndexUI add(UIComponent child) {
        super.add(child);
        return this;
    }
}
