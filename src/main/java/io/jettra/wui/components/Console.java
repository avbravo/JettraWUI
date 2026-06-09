package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * A Console component that acts as an in-browser log viewer.
 * Exposes JavaScript functions to print messages interactively:
 * - jtConsoleLog(id, msg, type)
 * - jtConsoleClear(id)
 */
public class Console extends UIComponent {

    private static final ConcurrentHashMap<String, Queue<String>> messageQueues = new ConcurrentHashMap<>();

    public static void addMessage(String consoleId, String msg, String type) {
        if (consoleId == null) return;
        messageQueues.computeIfAbsent(consoleId, k -> new ConcurrentLinkedQueue<>())
                     .offer(type + "|" + msg);
    }

    public static Queue<String> getQueue(String consoleId) {
        if (consoleId == null) return null;
        return messageQueues.get(consoleId);
    }


    public Console() {
        super("div");
        this.addClass("j-console-component");
        this.setStyle("background-color", "#1e1e1e");
        this.setStyle("color", "#d4d4d4");
        this.setStyle("font-family", "monospace");
        this.setStyle("padding", "10px");
        this.setStyle("border-radius", "4px");
        this.setStyle("height", "300px");
        this.setStyle("overflow-y", "auto");
        this.setStyle("border", "1px solid #333");
    }

    public Console(String id) {
        this();
        this.setId(id);
    }

    @Override
    public String render() {
        String baseHtml = super.render();
        String idStr = this.getId() != null ? this.getId() : "";
        String js = "<script>\n" +
            "  if (!window.jtConsoleLog) {\n" +
            "    window.jtConsoleLog = function(id, msg, type) {\n" +
            "      var el = document.getElementById(id);\n" +
            "      if (el) {\n" +
            "        var line = document.createElement('div');\n" +
            "        line.style.marginBottom = '4px';\n" +
            "        line.style.wordBreak = 'break-all';\n" +
            "        if (type === 'error') { line.style.color = '#f44336'; }\n" +
            "        else if (type === 'warn') { line.style.color = '#ff9800'; }\n" +
            "        else if (type === 'info') { line.style.color = '#2196f3'; }\n" +
            "        else { line.style.color = '#d4d4d4'; }\n" +
            "        line.innerText = '> ' + msg;\n" +
            "        el.appendChild(line);\n" +
            "        el.scrollTop = el.scrollHeight;\n" +
            "      }\n" +
            "    };\n" +
            "    window.jtConsoleClear = function(id) {\n" +
            "      var el = document.getElementById(id);\n" +
            "      if (el) { el.innerHTML = ''; }\n" +
            "    };\n" +
            "  }\n";

        if (!idStr.isEmpty()) {
            js += "  if (!window['jtConsolePoll_' + '" + idStr + "']) {\n" +
                  "    window['jtConsolePoll_' + '" + idStr + "'] = true;\n" +
                  "    setInterval(function() {\n" +
                  "      var el = document.getElementById('" + idStr + "');\n" +
                  "      if (!el) return;\n" +
                  "      var sep = window.location.search ? '&' : '?';\n" +
                  "      fetch(window.location.pathname + window.location.search + sep + '_jtConsolePoll=" + idStr + "')\n" +
                  "        .then(function(r) { return r.json(); })\n" +
                  "        .then(function(msgs) {\n" +
                  "           if (msgs && msgs.length > 0) {\n" +
                  "             msgs.forEach(function(m) { window.jtConsoleLog('" + idStr + "', m.msg, m.type); });\n" +
                  "           }\n" +
                  "        }).catch(function(e){});\n" +
                  "    }, 2000);\n" +
                  "  }\n";
        }

        js += "</script>\n";
        return js + baseHtml;
    }
}
