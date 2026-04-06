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
}
