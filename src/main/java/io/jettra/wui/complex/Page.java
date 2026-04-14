package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;

/**
 * Base class for drawing custom components in the application.
 * Lifecycle annotations such as @Init, @PostConstructor, @PreDestroy, and @OnEvent
 * should be used on methods within subclasses.
 */
public abstract class Page extends UIComponent {

    public Page(String tag) {
        super(tag);
    }

    @Override
    public Page setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Page setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Page setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Page addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Page removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Page setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Page setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Page addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Page add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
