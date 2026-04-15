package io.jettra.wui.components;

/**
 * FormGroup component that extends Div and automatically adds the "form-group" class.
 */
public class FormGroup extends Div {

    public FormGroup() {
        super();
        this.addClass("form-group");
    }

    @Override
    public FormGroup setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public FormGroup setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public FormGroup setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public FormGroup addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public FormGroup removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public FormGroup setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public FormGroup setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public FormGroup addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public FormGroup add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
