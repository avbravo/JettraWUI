package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * FileUpload component for handling file selection and upload process.
 */
public class FileUpload extends Div {
    private UIComponent fileInput;
    private Button uploadBtn;
    private ProgressBar progressBar;

    public FileUpload(String id) {
        super();
        this.addClass("j-fileupload-container");
        this.setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("gap", "10px");

        Div controls = new Div();
        controls.setStyle("display", "flex").setStyle("gap", "10px").setStyle("align-items", "center");

        this.fileInput = new UIComponent("input") {};
        this.fileInput.setProperty("type", "file");
        this.fileInput.setId(id);
        this.fileInput.addClass("j-input");
        this.fileInput.setStyle("margin-bottom", "0");
        controls.add(fileInput);

        this.uploadBtn = new Button("Upload");
        this.uploadBtn.addClass("j-btn-primary");
        controls.add(uploadBtn);
        
        this.add(controls);

        this.progressBar = new ProgressBar();
        this.progressBar.setStyle("display", "none").setStyle("margin-top", "5px");
        this.add(progressBar);
        
        setupUploadLogic(id);
    }

    private boolean confirmUpload = false;
    private String confirmTitle = "Confirm Upload";

    public FileUpload setConfirmUpload(boolean confirm, String title) {
        this.confirmUpload = confirm;
        if (title != null) this.confirmTitle = title;
        return this;
    }

    private void setupUploadLogic(String id) {
        // Simple JS to simulate/handle progress if possible via native fetch or XHR
        String js = "<script>" +
                    "(function() {" +
                    "  const btn = document.querySelector('#" + id + "').closest('.j-fileupload-container').querySelector('.j-btn-primary');" +
                    "  const input = document.getElementById('" + id + "');" +
                    "  const pb = document.querySelector('#" + id + "').closest('.j-fileupload-container').querySelector('.j-progressbar-container');" +
                    "  const bar = pb.querySelector('.j-progressbar-fill');" +
                    "  const lbl = pb.querySelector('.j-progressbar-label');" +
                    "  " +
                    "  function startProgress() {" +
                    "    pb.style.display = 'block';" +
                    "    let prog = 0;" +
                    "    const interval = setInterval(() => {" +
                    "      prog += 5;" +
                    "      bar.style.width = prog + '%';" +
                    "      if(lbl) lbl.innerText = prog + '%';" +
                    "      if(prog >= 100) {" +
                    "        clearInterval(interval);" +
                    "        setTimeout(() => { pb.style.display = 'none'; bar.style.width='0%'; }, 1000);" +
                    "      }" +
                    "    }, 100);" +
                    "  }" +
                    "  " +
                    "  btn.addEventListener('click', () => {" +
                    "    if(!input.files || input.files.length === 0) return;" +
                    "    if(" + confirmUpload + " && window.show3DConfirm) {" +
                    "      const msg = '¿Quieres subir ' + input.files.length + ' archivos a este sitio web?';" +
                    "      window.show3DConfirm('" + confirmTitle + "', msg, startProgress);" +
                    "    } else {" +
                    "      startProgress();" +
                    "    }" +
                    "  });" +
                    "})();" +
                    "</script>";
        this.add(new UIComponent("div") {
            @Override
            public String render() {
                return js;
            }
        });
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
