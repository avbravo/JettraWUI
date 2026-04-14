package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * ChartJSExtension
 * Embebe la librería completa de Chart.js offline sin depender del CDN externo.
 */
public class ChartJSExtension extends UIComponent {
    private static String cachedChartJs = null;

    public ChartJSExtension() {
        super("script");
        if (cachedChartJs == null) {
            loadChartJs();
        }
        setContent(cachedChartJs);
    }

    private synchronized static void loadChartJs() {
        if (cachedChartJs != null) return;
        try (InputStream in = ChartJSExtension.class.getResourceAsStream("/assets/chart.min.js")) {
            if (in != null) {
                try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
                    String baseCode = scanner.hasNext() ? scanner.next() : "";
                    cachedChartJs = "\nif (typeof Chart === 'undefined') {\n" + baseCode + "\n}\n";
                }
            } else {
                cachedChartJs = "console.error('chart.min.js no se encontro en /assets/');";
            }
        } catch (Exception e) {
            e.printStackTrace();
            cachedChartJs = "console.error('Error cargando chart.min.js offline');";
        }
    }

    public static String getInlineScript() {
        if (cachedChartJs == null) {
            loadChartJs();
        }
        return "<script>\n" + cachedChartJs + "\n</script>\n";
    }

    @Override
    public ChartJSExtension setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartJSExtension setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartJSExtension setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartJSExtension addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartJSExtension removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartJSExtension setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartJSExtension setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartJSExtension addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartJSExtension add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
