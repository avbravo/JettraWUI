package io.jettra.wui.events;

import io.jettra.wui.core.UIComponent;
import java.util.Map;

public class EventRouter {
    /**
     * Scans the component tree and fires any listeners matching the event target ID.
     */
    public static void dispatch(UIComponent root, Map<String, String> params) {
        String targetId = params.get("_jtEvent");
        if (targetId == null || targetId.isEmpty()) return;
        
        findAndFire(root, targetId);
    }

    private static void findAndFire(UIComponent component, String targetId) {
        if (targetId.equals(component.getId())) {
            for (ClickListener listener : component.getClickListeners()) {
                listener.onClick();
            }
        }
        for (UIComponent child : component.getChildren()) {
            findAndFire(child, targetId);
        }
    }
}
