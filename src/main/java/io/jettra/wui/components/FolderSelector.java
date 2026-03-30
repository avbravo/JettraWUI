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
                            "   var triggerChange = function() { " + (originalOnChange != null ? originalOnChange : "") + " };" +
                            "   input.onchange = function(e) {" +
                            "     var files = e.target.files;" +
                            "     var treeHtml = '<div style=\"max-height:200px; overflow-y:auto; text-align:left; background:rgba(0,0,0,0.3); padding:10px; border-radius:8px; margin:10px 0; font-family:monospace; font-size:12px;\">';" +
                            "     var tree = {};" +
                            "     for(var i=0; i<Math.min(files.length, 50); i++) {" +
                            "       var path = files[i].webkitRelativePath || files[i].name;" +
                            "       treeHtml += '<div>📄 ' + path + '</div>';" +
                            "     }" +
                            "     if(files.length > 50) treeHtml += '<div>... and ' + (files.length-50) + ' more files</div>';" +
                            "     treeHtml += '</div>';" +
                            "     if(window.show3DConfirm) {" +
                            "       window.show3DConfirm('" + confirmTitle + "', treeHtml, triggerChange);" +
                            "     } else if(confirm('" + confirmMessage + "')) {" +
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


