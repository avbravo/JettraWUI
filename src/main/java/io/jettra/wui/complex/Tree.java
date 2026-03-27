package io.jettra.wui.complex;

import io.jettra.wui.components.Div;
import io.jettra.wui.components.Span;
import io.jettra.wui.core.UIComponent;
import java.util.UUID;

public class Tree extends Div {
    public Tree() {
        super();
        this.addClass("j-tree");
        this.setStyle("font-family", "inherit").setStyle("color", "var(--jettra-text)");
    }

    public static class TreeItem extends Div {
        private Div childrenContainer;
        private Span toggleBtn;
        private Span labelSpan;

        public TreeItem(String label) {
            super();
            this.addClass("j-tree-item");
            
            Div header = new Div();
            header.addClass("j-tree-header");
            header.setStyle("display", "flex").setStyle("align-items", "center").setStyle("cursor", "pointer").setStyle("padding", "5px").setStyle("transition", "all 0.2s");
            
            toggleBtn = new Span("▶");
            toggleBtn.addClass("j-tree-toggle");
            toggleBtn.setStyle("margin-right", "8px").setStyle("transition", "transform 0.2s").setStyle("width", "16px").setStyle("display", "inline-block").setStyle("font-size", "10px").setStyle("color", "var(--jettra-accent)");
            
            labelSpan = new Span(label);
            
            header.add(toggleBtn).add(labelSpan);
            
            childrenContainer = new Div();
            childrenContainer.addClass("j-tree-content");
            childrenContainer.setStyle("padding-left", "25px").setStyle("display", "none");
            
            this.add(header).add(childrenContainer);
            
            String id = "tree-item-" + UUID.randomUUID().toString().substring(0, 8);
            this.setId(id);
            
            header.setProperty("onclick", "toggleTreeItem('" + id + "')");
        }

        public TreeItem addItem(TreeItem item) {
            childrenContainer.add(item);
            return this;
        }

        @Override
        public UIComponent add(UIComponent component) {
           if (component instanceof TreeItem) {
               return addItem((TreeItem) component);
           }
           childrenContainer.add(component);
           return this;
        }

        public Span getLabel() {
            return labelSpan;
        }

        public TreeItem setExpanded(boolean expanded) {
            if (expanded) {
                childrenContainer.setStyle("display", "block");
                toggleBtn.setStyle("transform", "rotate(90deg)");
            } else {
                childrenContainer.setStyle("display", "none");
                toggleBtn.setStyle("transform", "rotate(0deg)");
            }
            return this;
        }
    }
}
