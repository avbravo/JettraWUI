package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;

/**
 * Container for multiple Avatar components, displaying them in a group with overlap.
 */
public class AvatarGroup extends UIComponent {
    public AvatarGroup() {
        super("div");
        addClass("j-avatar-group");
    }

    @Override
    public AvatarGroup setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public AvatarGroup setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public AvatarGroup setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public AvatarGroup addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public AvatarGroup removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public AvatarGroup setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public AvatarGroup setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public AvatarGroup addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public AvatarGroup add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
