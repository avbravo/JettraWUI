package io.jettra.wui.core;

import com.jettra.server.JettraServer;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.wui.complex.Center;
import io.jettra.wui.complex.Dashboard;
import io.jettra.wui.complex.Footer;
import io.jettra.wui.complex.Left;
import io.jettra.wui.complex.Top;
import io.jettra.wui.complex.Avatar;
import io.jettra.wui.components.Div;
import io.jettra.wui.components.SelectOneIcon;


import java.io.IOException;
import java.util.Map;

/**
 * Base Dashboard class providing integrated layout logic for Top, Left, Footer.
 * Framework clients just need to extend this class and override initCenter and optionally setupLeft.
 */
public abstract class JettraDashboardPage extends Page {

    protected Dashboard dashboard;
    protected Top top;
    protected Left left;
    protected Footer footer;
    protected Center center;
    protected HttpExchange currentExchange;
    
    // Extracted categories HTML builder to simplify Menu building in implementations
    protected StringBuilder menuHtmlBuilder = new StringBuilder();

    public JettraDashboardPage(String title) {
        super(title);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.currentExchange = exchange;
        super.handle(exchange);
    }

    @Override
    protected void onInit(Map<String, String> params) {
        String loggedUser = getLoggedUser(currentExchange);
        // Authentication check
        if ("Guest".equals(loggedUser) || loggedUser.isEmpty()) {
            try {
                redirect(currentExchange, JettraServer.resolvePath("/"));
            } catch (Exception e) {
                // Ignore
            }
            return;
        }

        this.children.clear();
        initLayout(loggedUser, params);
    }

    protected void initLayout(String username, Map<String, String> params) {
        dashboard = new Dashboard();

        // 1. Setup Top
        top = new Top();
        setupTop(top, username, params);
        dashboard.setTop(top);

        // 2. Setup Left (Menu)
        left = new Left();
        setupLeft(left, username);
        dashboard.setLeft(left);

        // 3. Setup Footer
        footer = new Footer();
        setupFooter(footer);
        dashboard.setFooter(footer);

        // 4. Setup Center (to be filled by subclasses)
        center = new Center();
        initCenter(center, username);
        dashboard.setCenter(center);

        add(dashboard);
        
        // Additional common components
        setupAdditionalComponents();
    }

    protected void setupTop(Top top, String username, Map<String, String> params) {
        top.setStyle("display", "flex")
           .setStyle("justify-content", "space-between")
           .setStyle("align-items", "center")
           .setStyle("width", "100%")
           .setStyle("padding", "2px 10px")
           .setStyle("box-sizing", "border-box")
           .setStyle("overflow", "visible");

        String fullTitle = com.jettra.server.config.JettraConfig.getProperty("app.title");
        String shortTitle = com.jettra.server.config.JettraConfig.getProperty("app.shorttitle");
        if (fullTitle == null) fullTitle = "Jettra Global Dashboard";
        if (shortTitle == null) shortTitle = fullTitle.substring(0, 1);

        io.jettra.wui.components.Button menuBtn = new io.jettra.wui.components.Button("\u2630");
        menuBtn.addClass("j-menu-toggle").addClass("show-on-mobile");
        menuBtn.setProperty("type", "button");
        menuBtn.setProperty("onclick", "toggleMobileMenu()");
        top.add(menuBtn);

        io.jettra.wui.components.Header title = new io.jettra.wui.components.Header(2, "");
        title.setContent("<span class='hide-on-low-res'>" + fullTitle + "</span><span class='show-on-low-res'>" + shortTitle + "</span>");
        title.setStyle("margin", "0").setStyle("font-size", "1.1rem");
        top.add(title);
        
        io.jettra.wui.components.Div rightSection = new io.jettra.wui.components.Div();
        rightSection.addClass("j-top-right");
        rightSection.setStyle("display", "flex").setStyle("align-items", "center").setStyle("gap", "6px").setStyle("flex-wrap", "nowrap").setStyle("overflow", "visible");
        
        String defaultConfigLang = com.jettra.server.config.JettraConfig.getProperty("app.language");
        if (defaultConfigLang == null) defaultConfigLang = "en";
        defaultConfigLang = defaultConfigLang.trim();
        
        String defaultConfigTheme = com.jettra.server.config.JettraConfig.getProperty("app.theme");
        if (defaultConfigTheme == null) defaultConfigTheme = "3d";
        defaultConfigTheme = defaultConfigTheme.trim();

        // Language Selector
        SelectOneIcon langSelect = new SelectOneIcon("lang","");
        langSelect.setShowLabelInTrigger(false);
        langSelect.addOption("en", "", "\uD83C\uDDFA\uD83C\uDDF8");
        langSelect.addOption("es", "", "\uD83C\uDDEA\uD83C\uDDF8");
        
        String currentLang = params.getOrDefault("lang", defaultConfigLang).trim();
        if ("es".equals(currentLang)) {
            langSelect.setSelectedValue("es", "", "\uD83C\uDDEA\uD83C\uDDF8");
        } else {
            langSelect.setSelectedValue("en", "", "\uD83C\uDDFA\uD83C\uDDF8");
        }
        
        // Theme Selector
        SelectOneIcon themeSelect = new SelectOneIcon("theme","");
        themeSelect.setShowLabelInTrigger(false);
        themeSelect.addOption("3d", "", "\uD83D\uDE80");
        themeSelect.addOption("cyberpunk", "", "\uD83E\uDDEC");
        themeSelect.addOption("neon", "", "\uD83E\uDDEA");
        themeSelect.addOption("glass", "", "\uD83E\uDDC3");
        themeSelect.addOption("dark", "", "\uD83C\uDF19");
        themeSelect.addOption("white", "", "\u2600\uFE0F");
        themeSelect.addOption("material", "", "\uD83C\uDFA8");
        themeSelect.addOption("futuristic", "", "\u2728");
        themeSelect.addOption("modern", "", "\uD83D\uDD33");
        
        String themeVal = defaultConfigTheme.toLowerCase();
        String themeIcon = "\uD83D\uDE80";
        if (themeVal.equals("cyberpunk")) themeIcon = "\uD83E\uDDEC";
        else if (themeVal.equals("neon")) themeIcon = "\uD83E\uDDEA";
        else if (themeVal.equals("glass")) themeIcon = "\uD83E\uDDC3";
        else if (themeVal.equals("dark")) themeIcon = "\uD83C\uDF19";
        else if (themeVal.equals("white")) themeIcon = "\u2600\uFE0F";
        else if (themeVal.equals("material")) themeIcon = "\uD83C\uDFA8";
        else if (themeVal.equals("futuristic")) themeIcon = "\u2728";
        else if (themeVal.equals("modern")) themeIcon = "\uD83D\uDD33";
        
        themeSelect.setSelectedValue(themeVal, "", themeIcon);
            
        rightSection.add(themeSelect).add(langSelect);
        
        // Avatar Menu
        Div userWrapper = new Div();
        userWrapper.addClass("j-avatar-wrapper");
        userWrapper.setProperty("onclick", "toggleAvatarMenu()");
        
        String initials = getInitials(username);
        Avatar userAvatar = Avatar.label(initials, "var(--jettra-accent)")
                .setShape(Avatar.Shape.CIRCLE)
                .setSize(Avatar.Size.MD);
        userWrapper.add(userAvatar);
        
        Div dropdown = new Div();
        dropdown.setProperty("id", "user-avatar-dropdown");
        dropdown.addClass("j-avatar-dropdown");
        
        Div animItem = new Div();
        animItem.addClass("j-avatar-dropdown-item")
                .setStyle("display", "flex")
                .setStyle("align-items", "center")
                .setStyle("gap", "10px")
                .setStyle("padding", "10px 15px")
                .setStyle("cursor", "default")
                .setProperty("onclick", "event.stopPropagation()"); 
        
        io.jettra.wui.components.Span animLabel = new io.jettra.wui.components.Span("Animations");
        animLabel.setStyle("font-size", "0.9rem").setStyle("color", "var(--jettra-text)").setStyle("margin-right", "auto");
        
        io.jettra.wui.components.ToggleSwitch animCB = new io.jettra.wui.components.ToggleSwitch("anim-toggle", "animated", "true");
        animCB.setLabels("ON", "OFF");
        animCB.setProperty("onchange", "toggleJettraAnimation(this.checked)");
        
        String animatedValue = com.jettra.server.config.JettraConfig.getProperty("app.animated");
        boolean isAnimatedConfig = animatedValue == null || animatedValue.trim().equalsIgnoreCase("true");
        if (isAnimatedConfig) {
            animCB.setProperty("checked", "checked");
        }
        
        io.jettra.wui.components.Script configScript = new io.jettra.wui.components.Script(
            "if(" + !isAnimatedConfig + ") {\n" +
            "  window.jettraAnimated = false;\n" +
            "  localStorage.setItem('jettra-animated', 'false');\n" +
            "}\n" +
            "window.addEventListener('DOMContentLoaded', () => {\n" +
            "  if(" + !isAnimatedConfig + ") { \n" +
            "     let cb = document.getElementById('anim-toggle');\n" +
            "     if(cb) cb.checked = false;\n" +
            "  }\n" +
            "});\n"
        );
        top.add(configScript);
        
        animItem.add(animLabel).add(animCB);
        dropdown.add(animItem);
        
        UIComponent logoutLink = new UIComponent("a") {};
        logoutLink.setProperty("href", JettraServer.resolvePath("/logout"));
        logoutLink.setContent("<svg width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4'></path><polyline points='16 17 21 12 16 7'></polyline><line x1='21' y1='12' x2='9' y2='12'></line></svg> <span>Logout</span>");
        dropdown.add(logoutLink);

        userWrapper.add(dropdown);
        rightSection.add(userWrapper);
        top.add(rightSection);
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "U";
        String[] parts = name.split("[^a-zA-Z0-9]+");
        StringBuilder init = new StringBuilder();
        for (int i = 0; i < Math.min(parts.length, 2); i++) {
            if (parts[i].length() > 0) {
                init.append(parts[i].substring(0, 1).toUpperCase());
            }
        }
        return init.length() > 0 ? init.toString() : "U";
    }

    // Default left menu implementation that can be overridden by subclasses
    protected void setupLeft(Left left, String username) {
        // App defaults to empty if not overridden
    }

    // Menu builder helper methods
    protected void initMenuBuilder() {
        menuHtmlBuilder = new StringBuilder();
        menuHtmlBuilder.append("<div style='padding:10px; font-family:sans-serif;'>");
    }

    protected void finishMenuBuilder(Left left) {
        menuHtmlBuilder.append("<script>\n")
                .append("  function toggleCategory(el) {\n")
                .append("    const content = el.nextElementSibling;\n")
                .append("    const arrow = el.querySelector('span:last-child');\n")
                .append("    if (content.style.display === 'none') {\n")
                .append("      content.style.display = 'block';\n")
                .append("      arrow.innerHTML = '\u25BC';\n")
                .append("      el.style.opacity = '1';\n")
                .append("    } else {\n")
                .append("      content.style.display = 'none';\n")
                .append("      arrow.innerHTML = '\u25B6';\n")
                .append("      el.style.opacity = '0.6';\n")
                .append("    }\n")
                .append("  }\n")
                .append("  window.jtUpdate = function(ids) {\n")
                .append("    const idList = ids.split(/[\\s,]+/);\n")
                .append("    idList.forEach(id => {\n")
                .append("      const el = document.getElementById(id);\n")
                .append("      if(el) {\n")
                .append("        el.classList.add('j-refresh-effect');\n")
                .append("        setTimeout(() => el.classList.remove('j-refresh-effect'), 500);\n")
                .append("      }\n")
                .append("    });\n")
                .append("  };\n")
                .append("  document.addEventListener('click', e => {\n")
                .append("    const target = e.target.closest('[data-update]');\n")
                .append("    if(target) window.jtUpdate(target.getAttribute('data-update'));\n")
                .append("  });\n")
                .append("</script>");
        menuHtmlBuilder.append("</div>");
        left.setContent(menuHtmlBuilder.toString());
    }

    protected void addCategory(String name, String[] comps, String compIcon) {
        String categoryStyle = "color:#0ff; margin:15px 0 8px 0; font-weight:600; text-transform:uppercase; font-size:12px; letter-spacing:1px; cursor:pointer; display:flex; justify-content:space-between; align-items:center; opacity:0.8; transition:opacity 0.2s;";
        menuHtmlBuilder.append("<div style='").append(categoryStyle).append("' onclick='toggleCategory(this)'><span>").append(name).append("</span> <span>\u25BC</span></div>");
        menuHtmlBuilder.append("<div class='menu-category-content' style='display:none;'>");
        for (String comp : comps) {
            appendMenuItem(comp, "/" + comp.toLowerCase(), compIcon);
        }
        menuHtmlBuilder.append("</div>");
    }

    protected void appendMenuItem(String text, String path, String icon) {
        String menuClass = "display:flex; align-items:center; padding:8px 12px; margin-bottom:4px; background:rgba(20,30,50,0.5); border-left:3px solid transparent; border-radius:4px; color:#fff; cursor:pointer; text-decoration:none; font-size:13px; transition:all 0.3s;";
        String hoverEvents = "onmouseover=\"this.style.background='rgba(0,255,255,0.1)'; this.style.borderLeftColor='#0ff'\" onmouseout=\"this.style.background='rgba(20,30,50,0.5)'; this.style.borderLeftColor='transparent'\"";
        menuHtmlBuilder.append("<div style='").append(menuClass).append("' ").append(hoverEvents).append(" onclick='window.location.href=\"").append(JettraServer.resolvePath(path)).append("\"'>")
            .append("<div style='margin-right:10px;'>").append(icon).append("</div>")
            .append("<span>").append(text).append("</span></div>");
    }

    protected void setupFooter(Footer footer) {
        footer.setContent("<p>JettraStack \u00A9 2026. Data connection secure.</p>");
    }

    protected void setupAdditionalComponents() {
        String timeoutValue = com.jettra.server.config.JettraConfig.getProperty("server.session.timeout");
        int timeoutMinutes = (timeoutValue != null && !timeoutValue.isBlank()) ? Integer.parseInt(timeoutValue.trim()) : 0;
        add(new io.jettra.wui.components.SessionTimeoutDialog(timeoutMinutes, 60));

        Div globalModal = new Div();
        globalModal.setProperty("id", "global-3d-message-modal");
        globalModal.setStyle("display", "none")
                   .setStyle("position", "fixed")
                   .setStyle("z-index", "10000")
                   .setStyle("left", "0")
                   .setStyle("top", "0")
                   .setStyle("width", "100%")
                   .setStyle("height", "100%")
                   .setStyle("background-color", "rgba(0,0,0,0.5)")
                   .setStyle("backdrop-filter", "blur(10px)");
        
        String modalHtml = "<style>" +
                           "  @keyframes dashModalAppear {" +
                           "    from { opacity:0; transform:translate(-50%,-45%); }" +
                           "    to { opacity:1; transform:translate(-50%,-50%); }" +
                           "  }" +
                           "  .dash-modal-3d {" +
                           "    animation: dashModalAppear 0.3s ease-out;" +
                           "    background: #1e293b;" +
                           "    border: 1px solid var(--jettra-accent);" +
                           "    border-radius: 12px;" +
                           "    box-shadow: 0 25px 50px -12px rgba(0,0,0,0.5);" +
                           "  }" +
                           "  .dash-btn-3d {" +
                           "    background: var(--jettra-accent);" +
                           "    color: #000; border: none; padding: 8px 20px; border-radius: 6px; font-weight: bold; cursor: pointer;" +
                           "    transition: background 0.2s;" +
                           "  }" +
                           "  .dash-btn-3d:hover { background: #0891b2; }" +
                           "  .dash-btn-3d:active { transform: translateY(1px); }" +
                           "</style>" +
                           "<div class='dash-modal-3d' style='position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); padding:25px; min-width:320px; color:#fff; text-align:center;'>" +
                           "<h3 id='global-3d-title' style='margin-top:0; color:var(--jettra-accent); font-weight:700;'>Message</h3>" +
                           "<p id='global-3d-body' style='margin-bottom:25px; font-size:15px; color:#cbd5e1;'></p>" +
                           "<div><button class='dash-btn-3d' onclick='document.getElementById(\"global-3d-message-modal\").style.display=\"none\"'>Aceptar</button></div>" +
                           "</div>" +
                           "<script>" +
                           " window.show3DMessage = function(title, body) {" +
                           "   document.getElementById('global-3d-title').innerText = title;" +
                           "   document.getElementById('global-3d-body').innerText = body;" +
                           "   document.getElementById('global-3d-message-modal').style.display = 'block';" +
                           " };" +
                           "</script>";
        globalModal.setContent(modalHtml);
        add(globalModal);
    }

    protected abstract void initCenter(Center center, String username);

    protected String getLoggedUser(HttpExchange exchange) {
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookies != null) {
            for (String cookie : cookies.split(";")) {
                cookie = cookie.trim();
                if (cookie.startsWith("username=") && cookie.length() > "username=".length()) {
                    return cookie.substring("username=".length());
                }
            }
        }
        return "Guest";
    }

    protected boolean checkAuth(HttpExchange exchange, String loggedUser) throws IOException {
        if ("Guest".equals(loggedUser) || loggedUser.isEmpty()) {
            exchange.getResponseHeaders().set("Location", JettraServer.resolvePath("/"));
            exchange.sendResponseHeaders(302, -1);
            exchange.getResponseBody().close();
            return false;
        }
        return true;
    }
}
