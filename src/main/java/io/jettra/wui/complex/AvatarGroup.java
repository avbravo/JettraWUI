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
}
