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
        this.folderInput.setProperty("webkitdirectory", "");
        this.folderInput.setProperty("directory", "");
        this.folderInput.setId(id + "_input");
        this.folderInput.addClass("j-input");
        this.folderInput.setStyle("margin-bottom", "0");
        this.add(folderInput);
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
                            "     var displayMsg = '¿Quieres subir ' + files.length + ' archivos a este sitio web?';" +
                            "     if(window.show3DConfirm) {" +
                            "       window.show3DConfirm(\"" + confirmTitle + "\", displayMsg + treeHtml, triggerChange);" +
                            "     } else if(confirm(displayMsg)) {" +
                            "       triggerChange();" +
                            "     }" +
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
}


