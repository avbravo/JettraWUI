package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.ArrayList;
import java.util.List;

public class Organigram extends UIComponent {

    private OrgNode root;

    public Organigram() {
        super("div");
        addClass("j-organigram");
        setStyle("overflow-x", "auto");
        setStyle("padding", "20px");
        setStyle("text-align", "center");
    }
    
    public void setRoot(String title, String subtitle) {
        this.root = new OrgNode(title, subtitle);
    }
    
    public OrgNode getRoot() {
        return root;
    }
    
    @Override
    public String render() {
        if (root == null) return "<div>No root node set</div>";
        
        StringBuilder html = new StringBuilder();
        // Base global CSS for simple org chart
        html.append("<style>\n")
            .append(".org-chart { display: flex; justify-content: center; }\n")
            .append(".org-node { text-align: center; margin: 0 10px; position: relative; padding-top: 20px; }\n")
            .append(".org-box { display: inline-block; padding: 10px 15px; background: rgba(0,255,255,0.05); border: 1px solid var(--jettra-accent); border-radius: 6px; box-shadow: 0 4px 6px rgba(0,0,0,0.3); min-width: 120px; z-index: 2; position:relative; }\n")
            .append(".org-children { display: flex; justify-content: center; padding-top: 20px; position: relative; }\n")
            .append(".org-children::before { content: ''; position: absolute; top: 0; left: 50%; border-left: 2px solid var(--jettra-border); width: 0; height: 20px; }\n")
            .append(".org-node::before { content: ''; position: absolute; top: 0; right: 50%; border-top: 2px solid var(--jettra-border); width: 50%; height: 20px; }\n")
            .append(".org-node::after { content: ''; position: absolute; top: 0; left: 50%; border-top: 2px solid var(--jettra-border); width: 50%; height: 20px; }\n")
            .append(".org-node:first-child::before, .org-node:last-child::after { border: 0 none; }\n")
            .append(".org-node:last-child::before { border-right: 2px solid var(--jettra-border); border-radius: 0 5px 0 0; }\n")
            .append(".org-node:first-child::after { border-left: 2px solid var(--jettra-border); border-radius: 5px 0 0 0; }\n")
            .append(".org-node:only-child::after, .org-node:only-child::before { display: none; }\n")
            .append(".org-node:only-child { padding-top: 0; }\n")
            .append("</style>\n");
            
        html.append("<div class='org-chart'>");
        html.append(renderNode(root, true));
        html.append("</div>");
        
        setContent(html.toString());
        return super.render();
    }
    
    private String renderNode(OrgNode n, boolean isRoot) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='org-node' ").append(isRoot ? "style='padding-top:0'" : "").append(">");
        
        // The Box
        sb.append("<div class='org-box' ")
          .append(n.onClickJs != null && !n.onClickJs.isEmpty() ? "style='cursor:pointer;' onclick=\"" + n.onClickJs.replace("\"", "&quot;") + "\"" : "")
          .append(">")
          .append("<div style='font-weight:bold; color:var(--jettra-accent);'>").append(n.title).append("</div>")
          .append("<div style='font-size:0.8rem; color:var(--jettra-text);'>").append(n.subtitle).append("</div>")
          .append("</div>");
          
        // Children
        if (!n.children.isEmpty()) {
            sb.append("<div class='org-children'>");
            for (OrgNode child : n.children) {
                sb.append(renderNode(child, false));
            }
            sb.append("</div>");
        }
        
        sb.append("</div>");
        return sb.toString();
    }
    
    public static class OrgNode {
        public String title, subtitle, onClickJs;
        public List<OrgNode> children = new ArrayList<>();
        
        public OrgNode(String title, String subtitle) {
            this.title = title; this.subtitle = subtitle; this.onClickJs = "";
        }

        public OrgNode(String title, String subtitle, String onClickJs) {
            this.title = title; this.subtitle = subtitle; this.onClickJs = onClickJs;
        }
        
        public OrgNode addChild(String t, String s) {
            OrgNode child = new OrgNode(t, s);
            this.children.add(child);
            return child;
        }

        public OrgNode addChild(String t, String s, String onClickJs) {
            OrgNode child = new OrgNode(t, s, onClickJs);
            this.children.add(child);
            return child;
        }
    }
}
