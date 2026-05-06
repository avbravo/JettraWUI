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
            
            if (typeof showJettraContextMenu === 'undefined') {
                window.jettraClipboard = { action: null, file: null };
                
                window.showJettraProgress = function(action, file, callback) {
                    let overlay = document.createElement('div');
                    overlay.style.position = 'fixed';
                    overlay.style.top = '0'; overlay.style.left = '0';
                    overlay.style.width = '100vw'; overlay.style.height = '100vh';
                    overlay.style.background = 'rgba(0,0,0,0.8)';
                    overlay.style.zIndex = '20000';
                    overlay.style.display = 'flex';
                    overlay.style.flexDirection = 'column';
                    overlay.style.justifyContent = 'center';
                    overlay.style.alignItems = 'center';
                    overlay.style.backdropFilter = 'blur(10px)';
                    
                    let title = document.createElement('h3');
                    title.innerText = action + '...';
                    title.style.color = 'var(--jettra-accent)';
                    title.style.fontFamily = 'inherit';
                    
                    let sub = document.createElement('p');
                    sub.innerText = file;
                    sub.style.color = '#fff';
                    sub.style.fontFamily = 'inherit';
                    
                    let barContainer = document.createElement('div');
                    barContainer.style.width = '400px';
                    barContainer.style.height = '10px';
                    barContainer.style.background = 'rgba(255,255,255,0.1)';
                    barContainer.style.borderRadius = '5px';
                    barContainer.style.overflow = 'hidden';
                    barContainer.style.marginTop = '20px';
                    
                    let bar = document.createElement('div');
                    bar.style.width = '0%';
                    bar.style.height = '100%';
                    bar.style.background = 'var(--jettra-accent)';
                    bar.style.boxShadow = '0 0 10px var(--jettra-glow)';
                    bar.style.transition = 'width 0.1s linear';
                    
                    barContainer.appendChild(bar);
                    overlay.appendChild(title);
                    overlay.appendChild(sub);
                    overlay.appendChild(barContainer);
                    document.body.appendChild(overlay);
                    
                    let p = 0;
                    let interval = setInterval(() => {
                        p += Math.random() * 15 + 5;
                        if (p >= 100) {
                            p = 100;
                            clearInterval(interval);
                            setTimeout(() => {
                                document.body.removeChild(overlay);
                                if (callback) callback();
                            }, 500);
                        }
                        bar.style.width = p + '%';
                    }, 200);
                };

                window.showJettraContextMenu = function(e, element, label, fullPath) {
                    e.preventDefault();
                    e.stopPropagation();
                    element.setAttribute('data-path', fullPath);
                    let existing = document.getElementById('j-context-menu');
                    if (existing) existing.remove();
                    
                    let menu = document.createElement('div');
                    menu.id = 'j-context-menu';
                    menu.style.position = 'fixed';
                    menu.style.left = e.clientX + 'px';
                    menu.style.top = e.clientY + 'px';
                    menu.style.background = 'rgba(10, 20, 40, 0.95)';
                    menu.style.border = '1px solid var(--jettra-accent)';
                    menu.style.borderRadius = '8px';
                    menu.style.padding = '5px 0';
                    menu.style.zIndex = '10000';
                    menu.style.boxShadow = '0 0 15px rgba(0,255,255,0.2)';
                    menu.style.backdropFilter = 'blur(10px)';
                    
                    const createOption = (text, icon, action) => {
                        let div = document.createElement('div');
                        div.innerHTML = `<span style="margin-right:10px">${icon}</span>${text}`;
                        div.style.padding = '8px 20px';
                        div.style.cursor = 'pointer';
                        div.style.color = '#fff';
                        div.style.fontSize = '13px';
                        div.style.transition = 'background 0.2s';
                        div.onmouseover = () => div.style.background = 'rgba(0,255,255,0.1)';
                        div.onmouseout = () => div.style.background = 'transparent';
                        div.onclick = () => { action(label, element.getAttribute('data-path')); menu.remove(); };
                        return div;
                    };
                    
                    menu.appendChild(createOption('Copiar', '📋', (l, fp) => { window.jettraClipboard = {action: 'copy', file: fp, label: l}; }));
                    menu.appendChild(createOption('Mover', '✂️', (l, fp) => { window.jettraClipboard = {action: 'move', file: fp, label: l}; }));
                    
                    if (window.jettraClipboard.file) {
                        menu.appendChild(createOption('Pegar (' + window.jettraClipboard.action + ')', '📥', (l, fp) => { 
                            showJettraProgress(window.jettraClipboard.action === 'copy' ? 'Copiando via JettraGRPC' : 'Moviendo', window.jettraClipboard.label + ' -> ' + l, () => {
                                fetch('/api/fs', {
                                    method: 'POST',
                                    headers: {'Content-Type': 'application/json'},
                                    body: JSON.stringify({action: window.jettraClipboard.action, source: window.jettraClipboard.file, target: fp})
                                }).then(res => res.text()).then(txt => {
                                    alert(txt);
                                    window.jettraClipboard = {action: null, file: null, label: null};
                                    window.location.reload();
                                });
                            });
                        }));
                    }
                    
                    menu.appendChild(createOption('Renombrar', '✏️', (l, fp) => {
                        let newName = prompt('Nuevo nombre para: ' + l, l);
                        if (newName) {
                            fetch('/api/fs', { method: 'POST', body: JSON.stringify({action: 'rename', source: fp, target: newName}) })
                            .then(res => res.text()).then(t => { alert(t); window.location.reload(); });
                        }
                    }));
                    
                    menu.appendChild(createOption('Eliminar', '🗑️', (l, fp) => {
                         if (confirm('¿Eliminar ' + l + '?')) {
                             showJettraProgress('Eliminando', l, () => {
                                 fetch('/api/fs', { method: 'POST', body: JSON.stringify({action: 'delete', source: fp}) })
                                 .then(res => res.text()).then(t => { alert(t); window.location.reload(); });
                             });
                         }
                    }));
                    
                    document.body.appendChild(menu);
                    
                    document.addEventListener('click', function closeMenu(ev) {
                        if (!menu.contains(ev.target)) {
                            menu.remove();
                            document.removeEventListener('click', closeMenu);
                        }
                    });
                };
            }
        """));
    }
    
    /**
     * Clears all TreeItems from this Tree without removing the injected scripts.
     */
    public void clearTree() {
        getChildren().removeIf(child -> child instanceof TreeItem);
    }


    public static class TreeItem extends Div {
        private Div childrenContainer;
        private Span toggleBtn;
        private Span labelSpan;

        public TreeItem(String label) {
            this(label, label);
        }

        public TreeItem(String label, String fullPath) {
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
            header.setProperty("oncontextmenu", "showJettraContextMenu(event, this, '" + label.replace("'", "\\'") + "', '" + fullPath.replace("'", "\\'") + "'); return false;");
            
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
