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
        
        // Add global updateTree helper
        this.add(new io.jettra.wui.components.Script("""
            if (typeof jtUpdateTree === 'undefined') {
                window.jtUpdateTree = function(targetId, files) {
                    const target = document.getElementById(targetId);
                    if (!target) return;
                    
                    const treeData = {};
                    for (let i = 0; i < files.length; i++) {
                        const path = files[i].webkitRelativePath || files[i].name;
                        const parts = path.split('/');
                        let curr = treeData;
                        for (let j = 0; j < parts.length; j++) {
                            const part = parts[j];
                            if (!curr[part]) curr[part] = {};
                            curr = curr[part];
                        }
                    }

                    function renderToTree(node, name) {
                        const keys = Object.keys(node);
                        const isFile = keys.length === 0;
                        const id = 'js-tree-' + Math.random().toString(36).substring(2, 9);
                        
                        let html = `
                            <div class="j-tree-item" id="${id}">
                                <div class="j-tree-header" style="display:flex; align-items:center; cursor:pointer; padding:5px; transition:all 0.2s; border-radius:4px" onclick="window.toggleTreeItem('${id}')">
                                    <span class="j-tree-toggle" style="margin-right:8px; transition:transform 0.2s; width:16px; display:inline-block; font-size:10px; color:var(--jettra-accent); ${isFile ? 'opacity:0' : ''}">▶</span>
                                    <span style="font-size:13px">${isFile ? '📄' : '📂'} ${name}</span>
                                </div>
                                <div class="j-tree-content" style="padding-left:25px; display:none; border-left:1px solid rgba(0,255,255,0.1)">
                        `;
                        
                        for (const key of keys) {
                            html += renderToTree(node[key], key);
                        }
                        html += `</div></div>`;
                        return html;
                    }

                    let finalHtml = "";
                    for (const root in treeData) {
                        finalHtml += renderToTree(treeData[root], root);
                    }
                    target.innerHTML = finalHtml;
                };
            }
        """));
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
            header.setStyle("display", "flex").setStyle("align-items", "center").setStyle("cursor", "pointer").setStyle("padding", "5px").setStyle("transition", "all 0.2s").setStyle("border-radius", "4px");
            
            toggleBtn = new Span("▶");
            toggleBtn.addClass("j-tree-toggle");
            toggleBtn.setStyle("margin-right", "8px").setStyle("transition", "transform 0.2s").setStyle("width", "16px").setStyle("display", "inline-block").setStyle("font-size", "10px").setStyle("color", "var(--jettra-accent)");
            
            labelSpan = new Span(label);
            labelSpan.setStyle("font-size", "13px");
            
            header.add(toggleBtn).add(labelSpan);
            
            childrenContainer = new Div();
            childrenContainer.addClass("j-tree-content");
            childrenContainer.setStyle("padding-left", "25px").setStyle("display", "none").setStyle("border-left", "1px solid rgba(0,255,255,0.1)");
            
            super.add(header);
            super.add(childrenContainer);
            
            String id = "tree-item-" + UUID.randomUUID().toString().substring(0, 8);
            this.setId(id);
            
            header.setProperty("onclick", "toggleTreeItem('" + id + "')");
            
            // Add global toggle function if not exists
            io.jettra.wui.components.Script script = new io.jettra.wui.components.Script("""
                if (typeof toggleTreeItem === 'undefined') {
                    window.toggleTreeItem = function(id) {
                        const item = document.getElementById(id);
                        const content = item.querySelector(':scope > .j-tree-content');
                        const toggle = item.querySelector(':scope > .j-tree-header > .j-tree-toggle');
                        if (content.style.display === 'none') {
                            content.style.display = 'block';
                            toggle.style.transform = 'rotate(90deg)';
                        } else {
                            content.style.display = 'none';
                            toggle.style.transform = 'rotate(0deg)';
                        }
                    };
                }
            """);
            super.add(script);
        }

        public TreeItem addItem(TreeItem item) {
            childrenContainer.add(item);
            return this;
        }

        @Override
        public TreeItem add(io.jettra.wui.core.UIComponent component) {
           if (component instanceof TreeItem) {
               return addItem((TreeItem) component);
           }
           if (component instanceof io.jettra.wui.components.Script) {
               super.add(component);
               return this;
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

        @Override
        public TreeItem setId(String id) {
            super.setId(id);
            return this;
        }

        @Override
        public TreeItem setProperty(String key, String value) {
            super.setProperty(key, value);
            return this;
        }

        @Override
        public TreeItem setStyle(String key, String value) {
            super.setStyle(key, value);
            return this;
        }

        @Override
        public TreeItem addClass(String className) {
            super.addClass(className);
            return this;
        }

        @Override
        public TreeItem removeClass(String className) {
            super.removeClass(className);
            return this;
        }

        @Override
        public TreeItem setContent(String content) {
            super.setContent(content);
            return this;
        }

        @Override
        public TreeItem setUpdate(String ids) {
            super.setUpdate(ids);
            return this;
        }

        @Override
        public TreeItem addClickListener(io.jettra.wui.events.ClickListener listener) {
            super.addClickListener(listener);
            return this;
        }
    }

    @Override
    public Tree setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Tree setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Tree setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Tree addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Tree removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Tree setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Tree setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Tree addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Tree add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
