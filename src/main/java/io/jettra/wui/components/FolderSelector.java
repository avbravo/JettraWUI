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
}
