package io.jettra.wui.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.jettra.wui.assets.JettraTheme;
import io.jettra.wui.events.EventRouter;
import io.jettra.wui.mvc.JettraMVC;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Page extends UIComponent implements HttpHandler {
    
    private String title;

    public Page(String title) {
        super("html");
        this.title = title;
        // Initialize ViewModels with @InjectViewModel
        JettraMVC.initializeViewModels(this);
    }

    /**
     * Entry point for JettraServer. 
     */
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
        Map<String, String> formParams = "POST".equalsIgnoreCase(exchange.getRequestMethod()) 
            ? parseRequestBody(exchange) 
            : new HashMap<>();

        Map<String, String> allParams = new HashMap<>(queryParams);
        allParams.putAll(formParams);

        // 1. MVC Sink: Update models from form data
        if (!formParams.isEmpty()) {
            System.out.println("[Page] Updating models from request...");
            JettraMVC.updateModelFromRequest(this, formParams);
        }

        // 2. Lifecycle: Initialize layout
        System.out.println("[Page] Initializing lifecycle (onInit)...");
        onInit(allParams);

        // 3. MVC Event Dispatching
        if (allParams.containsKey("_jtEvent")) {
            System.out.println("[Page] Dispatching event: " + allParams.get("_jtEvent"));
            EventRouter.dispatch(this, allParams);
        }

        // 4. Lifecycle: Custom logic hooks
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            System.out.println("[Page] Calling onPost...");
            onPost(allParams);
        } else {
            System.out.println("[Page] Calling onGet...");
            onGet(allParams);
        }

        // 5. Render Response
        // check if headers were already sent (e.g. by a redirect)
        if (exchange.getResponseCode() == -1) {
            System.out.println("[Page] Rendering final HTML...");
            String html = this.render();
            System.out.println("[Page] HTML length: " + html.length());
            renderResponse(exchange, html);
        }
        System.out.println("[Page] Request completed.");
    }

    protected void onInit(Map<String, String> params) {}
    protected void onGet(Map<String, String> params) {}
    protected void onPost(Map<String, String> params) {}

    protected Map<String, String> parseQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }
        return map;
    }

    protected Map<String, String> parseRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
        }
        return parseFormData(sb.toString());
    }

    private Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        if (formData == null || formData.isEmpty()) return map;
        for (String pair : formData.split("&")) {
            String[] kv = pair.split("=");
            try {
                if (kv.length == 2) map.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8), URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            } catch (Exception e) {}
        }
        return map;
    }

    protected void redirect(HttpExchange exchange, String resolvedPath) throws IOException {
        exchange.getResponseHeaders().set("Location", resolvedPath);
        exchange.sendResponseHeaders(302, -1);
        exchange.getResponseBody().close();
    }

    protected void renderResponse(HttpExchange exchange, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    @Override
    public String render() {
        // Sync models to components before rendering
        JettraMVC.updateViewFromModel(this);
        
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>\n");
        builder.append("<html lang=\"en\">\n<head>\n");
        builder.append("    <meta charset=\"UTF-8\">\n");
        builder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        builder.append("    <title>").append(title).append("</title>\n");
        
        // Inject 3D Futuristic Theme CSS & JS
        builder.append(JettraTheme.getCSS());
        builder.append(JettraTheme.getJS());
        
        builder.append("</head>\n<body>\n");
        
        // Main container
        builder.append("<div class=\"jettra-viewport\">\n");
        
        for (UIComponent child : children) {
            builder.append(child.render());
        }
        
        builder.append("</div>\n");
        builder.append("</body>\n</html>");
        
        return builder.toString();
    }
}
