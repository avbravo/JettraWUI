package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * FolderSelector component for selecting directories and managing content references.
 */
public class FolderSelector extends Div {
    private UIComponent folderInput;
    private String referenceLocation;
    private String referenceContent;

    public FolderSelector(String id) {
        super();
        this.setId(id);
        this.addClass("j-folder-selector");
        this.setStyle("display", "inline-flex").setStyle("gap", "10px").setStyle("align-items", "center");

        this.folderInput = new UIComponent("input") {};
        this.folderInput.setProperty("type", "file");
        this.folderInput.setProperty("webkitdirectory", "true"); // Fixed: should be string "true" or empty
        this.folderInput.setProperty("directory", "true");
        this.folderInput.setId(id + "_input");
        this.folderInput.addClass("j-input");
        this.folderInput.setStyle("margin-bottom", "0");
        this.add(folderInput);
    }

    public UIComponent getFolderInput() {
        return folderInput;
    }

    public FolderSelector setReferenceLocation(String location) {
        this.referenceLocation = location;
        this.setProperty("data-location", location);
        return this;
    }

    public String getReferenceLocation() {
        return referenceLocation;
    }

    public FolderSelector setReferenceContent(String content) {
        this.referenceContent = content;
        this.setProperty("data-content", content);
        return this;
    }

    public String getReferenceContent() {
        return referenceContent;
    }

    private boolean confirmUpload = false;
    private String confirmTitle = "Confirm Upload";
    private String confirmMessage = "Are you sure you want to upload these files?";

    public FolderSelector setConfirmUpload(boolean confirm, String title, String message) {
        this.confirmUpload = confirm;
        if (title != null) this.confirmTitle = title;
        if (message != null) this.confirmMessage = message;
        return this;
    }

    public FolderSelector style3D() {
        this.setStyle("background", "rgba(20, 35, 55, 0.8)")
            .setStyle("border", "1px solid cyan")
            .setStyle("box-shadow", "0 0 15px cyan")
            .setStyle("border-radius", "10px")
            .setStyle("padding", "10px")
            .setStyle("color", "white");
        
        if (folderInput != null) {
            folderInput.setStyle("background", "#0f172a")
                       .setStyle("border", "1px solid cyan")
                       .setStyle("color", "cyan")
                       .setStyle("border-radius", "4px");
        }
        return this;
    }

    private String treeTargetId = "";

    public FolderSelector setTreeTarget(String id) {
        this.treeTargetId = id;
        return this;
    }

    public FolderSelector setTree(io.jettra.wui.complex.Tree tree) {
        this.treeTargetId = tree.getId();
        return this;
    }

    @Override
    public FolderSelector setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public String render() {
        if (confirmUpload) {
            String id = folderInput.getProperties().get("id");
            String originalOnChange = folderInput.getProperties().get("onchange");
            
            // Generate a script that overrides the onchange to show a confirmation with a TREE VIEW
            String script = "<script>" +
                            " (function() {" +
                            "   var input = document.getElementById('" + id + "');" +
                            "   if(!input) return;" +
                            "   var triggerChange = function() { " + 
                            (treeTargetId != null && !treeTargetId.isEmpty() ? "if(window.jtUpdateTree) window.jtUpdateTree('" + treeTargetId + "', input.files); " : "") +
                            (originalOnChange != null ? originalOnChange : "") + 
                            " };" +
                            "   input.onchange = function(e) {" +
                            "     var files = e.target.files;" +
                            "     var tree = {};" +
                            "     for(var i=0; i<files.length; i++) {" +
                            "       var path = files[i].webkitRelativePath || files[i].name;" +
                            "       var parts = path.split('/');" +
                            "       var curr = tree;" +
                            "       for(var j=0; j<parts.length; j++) {" +
                            "         var part = parts[j];" +
                            "         if(!curr[part]) curr[part] = { _files: [] };" +
                            "         curr = curr[part];" +
                            "       }" +
                            "     }" +
                            "     function renderNested(node, name, depth) {" +
                            "       var html = '<div style=\"padding-left:' + (depth*15) + 'px; color:var(--jettra-accent); font-size:13px; line-height:1.5;\">';" +
                            "       html += (Object.keys(node).length > 1 || (Object.keys(node).length === 1 && !node._files) ? '📂 ' : '📄 ') + name + '</div>';" +
                            "       for(var key in node) if(key !== \"_files\") html += renderNested(node[key], key, depth+1);" +
                            "       return html;" +
                            "     }" +
                                "     var treeHtml = '<div style=\"max-height:250px; overflow-y:auto; text-align:left; background:rgba(0,0,0,0.4); padding:15px; border-radius:15px; border:1px solid rgba(0,255,255,0.2); margin:15px 0;\">';" +
                                "     for(var root in tree) treeHtml += renderNested(tree[root], root, 0);" +
                                "     treeHtml += '</div>';" +
                                "     var displayMsg = '" + confirmMessage + "';" +
                                "     var overlay = document.createElement('div');" +
                                "     overlay.style.cssText = 'position:fixed; top:0; left:0; width:100vw; height:100vh; background:rgba(0,0,10,0.8); backdrop-filter:blur(8px); display:flex; justify-content:center; align-items:center; z-index:99999; opacity:0; transition:opacity 0.3s ease;';" +
                                "     var modal = document.createElement('div');" +
                                "     modal.style.cssText = 'background:#1e293b; border:1px solid cyan; box-shadow:0 25px 50px rgba(0,0,0,0.5); border-radius:12px; padding:25px; width:450px; max-width:90%; color:white; font-family:sans-serif; transform:scale(0.9); transition:transform 0.3s ease;';" +
                                "     modal.innerHTML = '<h3 style=\"margin-top:0; color:cyan; border-bottom:1px solid rgba(0,255,255,0.3); padding-bottom:10px;\">" + confirmTitle + "</h3>' + " +
                                "                       '<p style=\"font-size:16px; margin:20px 0;\">' + displayMsg + '</p>' + " +
                                "                       treeHtml + " +
                                "                       '<div style=\"display:flex; justify-content:flex-end; gap:15px; margin-top:25px;\">' + " +
                                "                         '<button id=\"btnCancel\" style=\"background:rgba(255,255,255,0.05); border:1px solid rgba(255,255,255,0.2); color:white; padding:10px 20px; border-radius:5px; cursor:pointer; transition:all 0.2s;\" onmouseover=\"this.style.background=\\'rgba(255,255,255,0.1)\\'\" onmouseout=\"this.style.background=\\'rgba(255,255,255,0.05)\\'\">Cancelar</button>' + " +
                                "                         '<button id=\"btnConfirm\" style=\"background:rgba(0,255,255,0.2); border:1px solid cyan; color:cyan; box-shadow:0 0 10px rgba(0,255,255,0.4); padding:10px 20px; border-radius:5px; cursor:pointer; font-weight:bold; transition:all 0.2s;\" onmouseover=\"this.style.background=\\'rgba(0,255,255,0.4)\\';this.style.boxShadow=\\'0 0 20px cyan\\'\" onmouseout=\"this.style.background=\\'rgba(0,255,255,0.2)\\';this.style.boxShadow=\\'0 0 10px rgba(0,255,255,0.4)\\'\">Subir Archivos</button>' + " +
                                "                       '</div>';" +
                                "     overlay.appendChild(modal);" +
                                "     document.body.appendChild(overlay);" +
                                "     requestAnimationFrame(() => {" +
                                "       overlay.style.opacity = '1';" +
                                "       modal.style.transform = 'scale(1)';" +
                                "     });" +
                                "     var closeDialog = function() {" +
                                "       overlay.style.opacity = '0';" +
                                "       modal.style.transform = 'scale(0.9)';" +
                                "       setTimeout(() => overlay.remove(), 300);" +
                                "     };" +
                                "     modal.querySelector('#btnCancel').onclick = function() {" +
                                "       closeDialog();" +
                                "       input.value = '';" +
                                "     };" +
                                "     modal.querySelector('#btnConfirm').onclick = function() {" +
                                "       closeDialog();" +
                                "       triggerChange.call(input);" +
                                "     };" +
                                "   };" +

                            " })();" +
                            "</script>";
            return super.render() + script;
        }
        return super.render();
    }



    private boolean excludeTarget = false;
    public FolderSelector excludeTarget(boolean exclude) {
        this.excludeTarget = exclude;
        this.setProperty("data-exclude-target", String.valueOf(exclude));
        return this;
    }
    public boolean isExcludeTarget() {
        return excludeTarget;
    }

    @Override
    public FolderSelector setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public FolderSelector setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public FolderSelector setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public FolderSelector addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public FolderSelector removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public FolderSelector setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public FolderSelector addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public FolderSelector add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}


