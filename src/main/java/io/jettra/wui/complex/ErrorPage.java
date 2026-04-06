package io.jettra.wui.complex;

import io.jettra.wui.core.Page;
import io.jettra.wui.components.Div;
import io.jettra.wui.components.Header;
import io.jettra.wui.components.Paragraph;
import io.jettra.wui.components.Button;

import java.util.Map;

/**
 * ErrorPage: Displays server errors or 404s cleanly with a 3D effect.
 */
public class ErrorPage extends Page {

    private String errorTitle = "Error Petición";
    private String errorDetail = "Se ha producido un error inesperado al procesar la solicitud.";
    private String errorOrigin = "Servidor";

    public ErrorPage() {
        super("🚨 Jettra - Error");
    }

    @Override
    protected void onGet(Map<String, String> params) {
        if (params.containsKey("title")) {
            this.errorTitle = params.get("title");
        }
        if (params.containsKey("detail")) {
            this.errorDetail = params.get("detail");
        }
        if (params.containsKey("origin")) {
            this.errorOrigin = params.get("origin");
        }
        
        buildUI();
    }

    private void buildUI() {
        // Container with 3D Effect
        Div container = new Div();
        container.setStyle("display", "flex")
                 .setStyle("align-items", "center")
                 .setStyle("justify-content", "center")
                 .setStyle("height", "100vh")
                 .setStyle("background", "radial-gradient(circle at center, #1e293b 0%, #0f172a 100%)");

        Div card = new Div();
        card.setStyle("background", "linear-gradient(145deg, rgba(30, 41, 59, 0.9), rgba(15, 23, 42, 0.9))")
            .setStyle("backdrop-filter", "blur(20px)")
            .setStyle("border", "1px solid rgba(255, 68, 68, 0.3)")
            .setStyle("border-radius", "20px")
            .setStyle("padding", "50px")
            .setStyle("width", "600px")
            .setStyle("box-shadow", "0 30px 60px rgba(0, 0, 0, 0.8), inset 0 2px 2px rgba(255, 68, 68, 0.1), 0 0 40px rgba(255, 68, 68, 0.2)")
            .setStyle("transform-style", "preserve-3d")
            .setStyle("perspective", "1000px")
            .setStyle("text-align", "center");

        // Error Icon
        Div icon = new Div();
        icon.setContent("⚠️");
        icon.setStyle("font-size", "80px")
            .setStyle("line-height", "1")
            .setStyle("margin-bottom", "20px")
            .setStyle("text-shadow", "0 10px 20px rgba(255, 68, 68, 0.5)")
            .setStyle("transform", "translateZ(30px)");

        Header title = new Header(2, this.errorTitle);
        title.setStyle("color", "#ff4444")
             .setStyle("margin", "0 0 15px 0")
             .setStyle("font-weight", "800")
             .setStyle("letter-spacing", "2px")
             .setStyle("transform", "translateZ(20px)");

        Paragraph detail = new Paragraph(this.errorDetail);
        detail.setStyle("color", "#cbd5e1")
              .setStyle("font-size", "18px")
              .setStyle("margin-bottom", "20px")
              .setStyle("line-height", "1.6")
              .setStyle("transform", "translateZ(10px)");

        // Origin Badge
        Div originBadge = new Div();
        originBadge.setContent("Origen del error: " + this.errorOrigin);
        originBadge.setStyle("display", "inline-block")
                   .setStyle("background", "rgba(255, 68, 68, 0.1)")
                   .setStyle("border", "1px dashed rgba(255, 68, 68, 0.4)")
                   .setStyle("color", "#ff8888")
                   .setStyle("padding", "8px 15px")
                   .setStyle("border-radius", "8px")
                   .setStyle("font-family", "monospace")
                   .setStyle("font-size", "14px")
                   .setStyle("margin-bottom", "40px")
                   .setStyle("transform", "translateZ(10px)");

        Button btnBack = new Button("Volver al Inicio");
        btnBack.setProperty("onclick", "window.location.href='/'");
        btnBack.setStyle("background", "linear-gradient(135deg, #ff4444, #cc0000)")
               .setStyle("color", "white")
               .setStyle("border", "none")
               .setStyle("padding", "12px 30px")
               .setStyle("border-radius", "8px")
               .setStyle("font-size", "16px")
               .setStyle("font-weight", "700")
               .setStyle("cursor", "pointer")
               .setStyle("box-shadow", "0 10px 20px rgba(204, 0, 0, 0.4), inset 0 2px 0 rgba(255,255,255,0.2)")
               .setStyle("transition", "all 0.2s")
               .setStyle("transform", "translateZ(20px)");
               
        Div cssHover = new Div();
        cssHover.setContent("<style>.btn-error:hover { transform: translateY(-3px) translateZ(30px); box-shadow: 0 15px 25px rgba(204, 0, 0, 0.6), inset 0 2px 0 rgba(255,255,255,0.3); }</style>");
        btnBack.addClass("btn-error");

        card.add(cssHover);
        card.add(icon);
        card.add(title);
        card.add(detail);
        card.add(originBadge);
        
        Div btnContainer = new Div();
        btnContainer.add(btnBack);
        card.add(btnContainer);

        container.add(card);
        this.add(container);
    }
}
