package io.jettra.wui.complex;

import io.jettra.wui.core.Page;
import java.io.IOException;
import java.util.Map;

public class SwaggerUIPage extends Page {

    public static String openApiUrl = "/openapi.json"; 

    public SwaggerUIPage() {
        super("Swagger UI");
    }

    @Override
    protected void onGet(Map<String, String> params) {
        String resolvedUrl = io.jettra.server.JettraServer.resolvePath(openApiUrl);
        
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\" />\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "  <meta name=\"description\" content=\"SwaggerUI\" />\n" +
                "  <title>SwaggerUI</title>\n" +
                "  <link rel=\"stylesheet\" href=\"https://unpkg.com/swagger-ui-dist@5.9.0/swagger-ui.css\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"swagger-ui\"></div>\n" +
                "<script src=\"https://unpkg.com/swagger-ui-dist@5.9.0/swagger-ui-bundle.js\" crossorigin></script>\n" +
                "<script>\n" +
                "  window.onload = () => {\n" +
                "    window.ui = SwaggerUIBundle({\n" +
                "      url: '" + resolvedUrl + "',\n" +
                "      dom_id: '#swagger-ui',\n" +
                "      operationsSorter: 'alpha',\n" +
                "      tagsSorter: 'alpha',\n" +
                "    });\n" +
                "  };\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";

        try {
            renderResponse(currentExchange, html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
