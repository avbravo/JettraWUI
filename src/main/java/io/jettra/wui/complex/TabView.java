package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;
import io.jettra.wui.components.Div;
import io.jettra.wui.components.Header;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TabView extends UIComponent {
        public enum Orientation { TOP, BOTTOM, LEFT, RIGHT }

    private String title;
    private Orientation orientation = Orientation.TOP;
    private List<Tab> tabs = new ArrayList<>();
    private String tvId;

    public static class Tab {
        private String title;
        private UIComponent content;
        private String id;

        public Tab(String title, UIComponent content) {
            this.title = title;
            this.content = content;
            this.id = "tab-" + UUID.randomUUID().toString().substring(0, 8);
        }

        public String getTitle() { return title; }
        public UIComponent getContent() { return content; }
        public String getId() { return id; }
    }

    public TabView(String title) {
        super("div");
        this.title = title;
        this.tvId = "tv-" + UUID.randomUUID().toString().substring(0, 8);
        this.addClass("j-tabview-container j-component");
        this.setStyle("display", "flex");
        this.setStyle("flex-direction", "column");
        this.setStyle("gap", "15px");
    }

    public TabView setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public TabView addTab(String title, UIComponent content) {
        this.tabs.add(new Tab(title, content));
        return this;
    }

    @Override
    public String render() {
        this.children.clear();
        
        // Main Title
        if (title != null && !title.isEmpty()) {
            Header h = new Header(3, title);
            h.setStyle("margin", "0").setStyle("color", "var(--jettra-accent)");
            add(h);
        }

        Div layout = new Div();
        layout.addClass("j-tabview-layout");
        layout.setStyle("display", "flex");
        
        Div tabList = new Div();
        tabList.addClass("j-tabview-tabs");
        tabList.setStyle("display", "flex");
        tabList.setStyle("gap", "5px");

        Div contentArea = new Div();
        contentArea.addClass("j-tabview-content");
        contentArea.setStyle("flex", "1").setStyle("padding", "15px").setStyle("background", "rgba(0,0,0,0.2)").setStyle("border-radius", "8px");

        // Apply orientation logic
        switch (orientation) {
            case TOP:
                layout.setStyle("flex-direction", "column");
                tabList.setStyle("flex-direction", "row").setStyle("border-bottom", "1px solid var(--jettra-border)");
                layout.add(tabList).add(contentArea);
                break;
            case BOTTOM:
                layout.setStyle("flex-direction", "column-reverse");
                tabList.setStyle("flex-direction", "row").setStyle("border-top", "1px solid var(--jettra-border)");
                layout.add(tabList).add(contentArea);
                break;
            case LEFT:
                layout.setStyle("flex-direction", "row");
                tabList.setStyle("flex-direction", "column").setStyle("min-width", "150px").setStyle("border-right", "1px solid var(--jettra-border)");
                layout.add(tabList).add(contentArea);
                break;
            case RIGHT:
                layout.setStyle("flex-direction", "row-reverse");
                tabList.setStyle("flex-direction", "column").setStyle("min-width", "150px").setStyle("border-left", "1px solid var(--jettra-border)");
                layout.add(tabList).add(contentArea);
                break;
        }

        for (int i = 0; i < tabs.size(); i++) {
            Tab tab = tabs.get(i);
            Div tabBtn = new Div();
            tabBtn.addClass("j-tab-btn");
            if (i == 0) tabBtn.addClass("active");
            tabBtn.setContent(tab.getTitle());
            tabBtn.setProperty("data-target", tab.getId());
            tabBtn.setProperty("onclick", "jettraSwitchTab(this, '" + tvId + "')");
            tabBtn.setStyle("padding", "10px 20px").setStyle("cursor", "pointer").setStyle("transition", "all 0.3s");
            tabList.add(tabBtn);

            Div tabContent = new Div();
            tabContent.setId(tab.getId());
            tabContent.addClass("j-tab-content");
            if (i != 0) tabContent.setStyle("display", "none");
            tabContent.add(tab.getContent());
            contentArea.add(tabContent);
        }

        add(layout);

        String css = "<style>\n" +
            ".j-tab-btn { color: var(--jettra-text); border: 1px transparent; }\n" +
            ".j-tab-btn:hover { background: rgba(0,255,255,0.1); color: var(--jettra-accent); }\n" +
            ".j-tab-btn.active { color: var(--jettra-accent); border-color: var(--jettra-accent); background: rgba(0,255,255,0.05); font-weight: bold; }\n" +
            "</style>\n";

        String js = "<script>\n" +
            "function jettraSwitchTab(btn, tvId) {\n" +
            "   const container = btn.closest('.j-tabview-layout');\n" +
            "   const tabs = container.querySelectorAll('.j-tab-btn');\n" +
            "   const contents = container.querySelectorAll('.j-tab-content');\n" +
            "   const target = btn.getAttribute('data-target');\n" +
            "   \n" +
            "   tabs.forEach(t => t.classList.remove('active'));\n" +
            "   contents.forEach(c => c.style.display = 'none');\n" +
            "   \n" +
            "   btn.classList.add('active');\n" +
            "   const activeContent = document.getElementById(target);\n" +
            "   if(activeContent) activeContent.style.display = 'block';\n" +
            "}\n" +
            "</script>\n";

        return super.render() + css + js;
    }

    @Override
    public TabView setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public TabView setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public TabView setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public TabView addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public TabView removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public TabView setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public TabView setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public TabView addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public TabView add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
