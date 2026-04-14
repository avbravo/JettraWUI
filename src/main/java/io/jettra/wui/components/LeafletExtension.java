package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LeafletExtension extends UIComponent {
    private static String cachedLeafletJs = null;
    private static String cachedLeafletCss = null;

    public LeafletExtension() {
        super("div"); // Wrapper for styles and scripts
        if (cachedLeafletJs == null || cachedLeafletCss == null) {
            loadLeaflet();
        }
        setContent("<style>\n" + cachedLeafletCss + "\n</style>\n<script>\n" + cachedLeafletJs + "\n</script>");
        setStyle("display", "none");
    }

    private synchronized static void loadLeaflet() {
        if (cachedLeafletJs != null && cachedLeafletCss != null) return;
        try (InputStream inJS = LeafletExtension.class.getResourceAsStream("/assets/leaflet.js")) {
            if (inJS != null) {
                try (Scanner scanner = new Scanner(inJS, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
                    cachedLeafletJs = scanner.hasNext() ? scanner.next() : "";
                }
            } else {
                cachedLeafletJs = "console.error('leaflet.js no se encontro en /assets/');";
            }
        } catch (Exception e) {
            e.printStackTrace();
            cachedLeafletJs = "console.error('Error cargando leaflet.js offline');";
        }
        
        try (InputStream inCSS = LeafletExtension.class.getResourceAsStream("/assets/leaflet.css")) {
            if (inCSS != null) {
                try (Scanner scanner = new Scanner(inCSS, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
                    cachedLeafletCss = scanner.hasNext() ? scanner.next() : "";
                }
            } else {
                cachedLeafletCss = "/* leaflet.css no se encontro */";
            }
        } catch (Exception e) {
            e.printStackTrace();
            cachedLeafletCss = "/* Error cargando leaflet.css offline */";
        }
    }

    public static String getInlineScript() {
        if (cachedLeafletJs == null || cachedLeafletCss == null) {
            loadLeaflet();
        }
        return "<style>\n" + cachedLeafletCss + "\n</style>\n<script>\n" + cachedLeafletJs + "\n</script>\n";
    }

    @Override
    public LeafletExtension setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public LeafletExtension setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public LeafletExtension setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public LeafletExtension addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public LeafletExtension removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public LeafletExtension setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public LeafletExtension setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public LeafletExtension addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public LeafletExtension add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
