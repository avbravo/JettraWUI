package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class SessionTimeoutDialog extends UIComponent {
    
    private int timeoutMinutes;
    private int warningSeconds;

    public SessionTimeoutDialog(int timeoutMinutes, int warningSeconds) {
        super("div");
        this.timeoutMinutes = timeoutMinutes;
        this.warningSeconds = warningSeconds;
    }

    @Override
    public String render() {
        if (timeoutMinutes <= 0) {
            return ""; // No timeout configured
        }
        
        long totalSeconds = ((long) timeoutMinutes) * 60;
        
        StringBuilder builder = new StringBuilder();
        
        // CSS for Modal
        builder.append("<style>\n");
        builder.append(".j-session-modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0, 0, 0, 0.8); z-index: 9999; justify-content: center; align-items: center; backdrop-filter: blur(5px); }\n");
        builder.append(".j-session-modal-content { background: #0f172a; border: 2px solid cyan; border-radius: 10px; padding: 30px; text-align: center; color: white; width: 350px; box-shadow: 0 0 20px cyan; font-family: 'Courier New', monospace; }\n");
        builder.append(".j-session-modal-content h3 { color: cyan; margin-top: 0; }\n");
        builder.append(".j-session-btn { background: cyan; color: #0f172a; padding: 10px 20px; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; margin-top: 20px; box-shadow: 0 0 10px cyan; transition: all 0.2s ease; }\n");
        builder.append(".j-session-btn:hover { background: #fff; box-shadow: 0 0 20px #fff; }\n");
        builder.append("</style>\n");
        
        // HTML Modal Structure
        builder.append("<div id='j-session-warning-modal' class='j-session-modal-overlay'>\n");
        builder.append("  <div class='j-session-modal-content'>\n");
        builder.append("    <h3>⚠️ Advertencia de Sesión</h3>\n");
        builder.append("    <p>Tu sesión expirará pronto por inactividad.</p>\n");
        builder.append("    <p style='font-size:1.5rem; color:red' id='j-session-timer'></p>\n");
        builder.append("    <button class='j-session-btn' onclick='extendSession()'>Mantener Sesión Activa</button>\n");
        builder.append("  </div>\n");
        builder.append("</div>\n");
        
        // JS Logic
        builder.append("<script>\n");
        builder.append("let sessionTotalSecs = ").append(totalSeconds).append(";\n");
        builder.append("let sessionWarningSecs = ").append(warningSeconds).append(";\n");
        builder.append("let currentSecs = sessionTotalSecs;\n");
        builder.append("let sessionTimer = null;\n");
        
        builder.append("function countdownSession() {\n");
        builder.append("  currentSecs--;\n");
        builder.append("  if (currentSecs <= sessionWarningSecs && currentSecs > 0) {\n");
        builder.append("    document.getElementById('j-session-warning-modal').style.display = 'flex';\n");
        builder.append("    document.getElementById('j-session-timer').innerText = currentSecs + ' s';\n");
        builder.append("  } else if (currentSecs <= 0) {\n");
        builder.append("    clearInterval(sessionTimer);\n");
        builder.append("    alert('Sesión finalizada por inactividad.');\n");
        builder.append("    window.location.href = '/logout';\n");
        builder.append("  }\n");
        builder.append("}\n");

        builder.append("function startSessionTimer() {\n");
        builder.append("  if (sessionTimer) clearInterval(sessionTimer);\n");
        builder.append("  currentSecs = sessionTotalSecs;\n");
        builder.append("  document.getElementById('j-session-warning-modal').style.display = 'none';\n");
        builder.append("  sessionTimer = setInterval(countdownSession, 1000);\n");
        builder.append("}\n");

        builder.append("function extendSession() {\n");
        // User clicks to stay
        builder.append("  startSessionTimer();\n");
        builder.append("}\n");
        
        // Extend session on user activity implicitly (optional, leaving commented out so it only extends on explicit click)
        // builder.append("window.addEventListener('mousemove', () => { if(document.getElementById('j-session-warning-modal').style.display !== 'flex') { currentSecs = sessionTotalSecs; } });\n");

        builder.append("startSessionTimer();\n");
        builder.append("</script>\n");
        
        return builder.toString();
    }
}
