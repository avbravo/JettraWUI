package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Script extends UIComponent {
    public Script(String jsCode) {
        super("script");
        setContent(jsCode);
    }
}
