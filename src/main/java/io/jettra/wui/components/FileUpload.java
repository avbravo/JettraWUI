package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * FileUpload component for handling file selection and upload process.
 */
public class FileUpload extends Div {
    private UIComponent fileInput;
    private Button uploadBtn;

    public FileUpload(String id) {
        super();
        this.addClass("j-fileupload-container");
        this.setStyle("display", "flex").setStyle("gap", "10px").setStyle("align-items", "center");

        this.fileInput = new UIComponent("input") {};
        this.fileInput.setProperty("type", "file");
        this.fileInput.setId(id);
        this.fileInput.addClass("j-input");
        this.fileInput.setStyle("margin-bottom", "0");
        this.add(fileInput);

        this.uploadBtn = new Button("Upload");
        this.uploadBtn.addClass("j-btn-primary");
        this.add(uploadBtn);
    }

    public FileUpload setMultiple(boolean multiple) {
        if (multiple) {
            fileInput.setProperty("multiple", "multiple");
        } else {
            fileInput.getProperties().remove("multiple");
        }
        return this;
    }

    public FileUpload setAccept(String accept) {
        if (accept == null || accept.equalsIgnoreCase("ALL")) {
            fileInput.getProperties().remove("accept");
        } else {
            fileInput.setProperty("accept", accept);
        }
        return this;
    }

    public FileUpload setMaxFileSize(long bytes) {
        fileInput.setProperty("data-max-size", String.valueOf(bytes));
        return this;
    }

    public FileUpload setDestination(String path) {
        fileInput.setProperty("data-destination", path);
        return this;
    }

    public FileUpload setFileNamePattern(String pattern) {
        fileInput.setProperty("data-pattern", pattern);
        return this;
    }

    public FileUpload setAutoUpload(boolean auto) {
        if (auto) {
            fileInput.setProperty("onchange", "this.closest('.j-fileupload-container').querySelector('.j-btn-primary').click()");
            uploadBtn.setStyle("display", "none");
        } else {
            fileInput.getProperties().remove("onchange");
            uploadBtn.setStyle("display", "inline-block");
        }
        return this;
    }

    public Button getUploadButton() {
        return uploadBtn;
    }
}
