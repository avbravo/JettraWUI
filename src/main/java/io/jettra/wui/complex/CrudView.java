package io.jettra.wui.complex;

import io.jettra.wui.components.*;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.*;
import io.jettra.wui.core.annotations.TableColumnField;
import io.jettra.wui.validations.JettraValidations;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class CrudView extends UIComponent {

    private Class<?> modelClass;
    private Class<?> repositoryClass;
    private CrudHandler<?> handler;
    private Properties messages;
    private String title;
    private String subtitle;
    private String modelName;
    private boolean reportEnabled = false;
    private boolean reportShowViewer = true;
    private boolean reportAllowPrint = true;
    private boolean reportAllowPdf = true;
    private boolean reportAllowExcel = true;
    private boolean reportAllowCsv = true;
    private String reportOrientation = "PORTRAIT";
    private String reportHeaderColor = "#000000";
    private String reportCustomTitle = "";

    private Modal crudModal;
    private Modal reportModal;
    private Header modalHeader;
    private TextBox modalAction;
    private List<UIComponent> inputFields = new ArrayList<>();
    private List<FormGroup> groupFields = new ArrayList<>();
    private Paragraph deleteMsg;
    private Button modalSubmitBtn;

    public CrudView(Class<?> modelClass, Class<?> repositoryClass, Properties messages) {
        this(modelClass, repositoryClass, messages, null);
    }

    public CrudView(Class<?> modelClass, Class<?> repositoryClass, Properties messages, CrudHandler<?> handler) {
        super("div");
        this.modelClass = modelClass;
        this.repositoryClass = repositoryClass;
        this.messages = messages;
        this.handler = handler;
        this.modelName = modelClass.getSimpleName().replace("Model", "");
        
        String modelKey = modelName.toLowerCase();
        this.title = getLabel("title." + modelKey, "Mantenimiento de " + modelName);
        this.subtitle = getLabel("subtitle." + modelKey, "Gestión de registros");
    }

    public CrudView build() {
        init();
        return this;
    }

    public CrudView setReportEnabled(boolean enabled) { this.reportEnabled = enabled; return this; }
    public CrudView setReportShowViewer(boolean show) { this.reportShowViewer = show; return this; }
    public CrudView setReportAllowPrint(boolean allow) { this.reportAllowPrint = allow; return this; }
    public CrudView setReportAllowPdf(boolean allow) { this.reportAllowPdf = allow; return this; }
    public CrudView setReportAllowExcel(boolean allow) { this.reportAllowExcel = allow; return this; }
    public CrudView setReportAllowCsv(boolean allow) { this.reportAllowCsv = allow; return this; }
    public CrudView setReportOrientation(String orientation) { this.reportOrientation = orientation; return this; }
    public CrudView setReportHeaderColor(String color) { this.reportHeaderColor = color; return this; }
    public CrudView setReportCustomTitle(String title) { this.reportCustomTitle = title; return this; }

    public CrudView setTitle(String title) {
        this.title = title;
        return this;
    }

    public CrudView setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    private void init() {
        Card mainCard = new Card()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setWidth("100%");

        String uniqueId = modelName.toLowerCase();
        setupModal(uniqueId);

        String modelKey = modelName.toLowerCase();
        String addLabel = getLabel("btn.add." + modelKey, null);
        if (addLabel == null) {
            String genericAdd = getLabel("btn.add", "Añadir");
            if (genericAdd.toLowerCase().contains("persona")) {
                addLabel = "Añadir " + modelName;
            } else {
                addLabel = genericAdd;
            }
        }

        Button addBtn = new Button("➕ " + addLabel)
                .setId("addBtn_" + uniqueId)
                .setBackgroundColor("#238636")
                .setOnclick("showCrudModal_" + uniqueId + "('save', false)");

        Datatable table = new Datatable();
        Row headerRow = new Row();

        Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            headerRow.add(new TD(getFieldLabel(field)));
        }

        TD actionsTdHeader = new TD();
        actionsTdHeader.add(addBtn);
        
        if (reportEnabled) {
            Button reportBtn = new Button("🖨️ " + getLabel("btn.report", "Reporte"))
                    .setId("reportBtn_" + uniqueId)
                    .setBackgroundColor("#007bff");

            if (reportShowViewer) {
                reportBtn.setOnclick("document.getElementById('reportModal_" + uniqueId + "').style.display='flex'");
            } else {
                reportBtn.setOnclick("location.href='?action=report&format=pdf'");
            }
            actionsTdHeader.add(reportBtn);
        }

        headerRow.add(actionsTdHeader);
        table.addHeaderRow(headerRow);

        try {
            List<?> items;
            if (handler != null) {
                items = (List<?>) handler.findAll();
            } else {
                Method findAll = repositoryClass.getMethod("findAll");
                items = (List<?>) findAll.invoke(null);
            }

            if (items != null) {
                for (Object item : items) {
                    Row dataRow = new Row();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object val = field.get(item);
                        
                        String displayValue = "";
                        if (val != null) {
                            if (field.isAnnotationPresent(TableColumnField.class)) {
                                TableColumnField tcf = field.getAnnotation(TableColumnField.class);
                                String targetField = tcf.field();
                                if (!targetField.isEmpty()) {
                                    if (val instanceof List) {
                                        List<?> list = (List<?>) val;
                                        displayValue = list.stream()
                                            .map(obj -> resolveObjectField(obj, targetField))
                                            .collect(Collectors.joining(", "));
                                    } else {
                                        displayValue = resolveObjectField(val, targetField);
                                    }
                                } else {
                                    displayValue = val.toString();
                                }
                            } else {
                                displayValue = val.toString();
                            }
                        }
                        dataRow.add(new TD(displayValue));
                    }

                    TD actionsTd = new TD();
                    String idValue = (handler != null) ? ((CrudHandler<Object>)handler).getIdValue(item) : getIdValue(item);
                    String jsonData = (handler != null) ? getJsonDataFromMap(((CrudHandler<Object>)handler).getJsonMap(item)) : getJsonData(item);

                    Button editBtn = new Button("✏️")
                            .setId("editBtn_" + idValue + "_" + uniqueId)
                            .setOnclick("showCrudModal_" + uniqueId + "('save', true, " + jsonData + ")");

                    Button deleteBtn = new Button("🗑️")
                            .setId("deleteBtn_" + idValue + "_" + uniqueId)
                            .setOnclick("showCrudModal_" + uniqueId + "('delete', true, " + jsonData + ")");

                    actionsTd.add(editBtn).add(deleteBtn);
                    dataRow.add(actionsTd);
                    table.addRow(dataRow);
                }
            }
        } catch (Exception e) {
            System.err.println("[CrudView] Error loading items: " + e.getMessage());
        }

        mainCard.add(table);
        this.add(mainCard);
        this.add(crudModal);
        
        if (reportEnabled && reportShowViewer) {
            setupReportModal(uniqueId);
            this.add(reportModal);
        }

        injectScripts(uniqueId);
    }

    private void setupReportModal(String uniqueId) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> reportClass = null;
            try {
                reportClass = Class.forName("com.jettra.report.Report", true, loader);
            } catch (Exception e) {
                try {
                    loader = modelClass.getClassLoader();
                    reportClass = Class.forName("com.jettra.report.Report", true, loader);
                } catch (Exception e2) {
                    reportClass = Class.forName("com.jettra.report.Report");
                    loader = reportClass.getClassLoader();
                }
            }

            if (reportClass == null) {
                setupFallbackModal(uniqueId, "No se pudo cargar la clase com.jettra.report.Report");
                return;
            }

            Object reportInstance = reportClass.getConstructor(String.class).newInstance("Reporte de " + modelClass.getSimpleName());
            
            // Set data
            Method setData = reportClass.getMethod("setData", List.class);
            List<?> items = null;
            try {
                if (handler != null) {
                    items = (List<?>) handler.findAll();
                } else {
                    Method findAll = repositoryClass.getMethod("findAll");
                    items = (List<?>) findAll.invoke(null);
                }
            } catch (Exception e) {
                System.err.println("[CrudView] Error fetching data for report: " + e.getMessage());
                items = new ArrayList<>();
            }
            setData.invoke(reportInstance, items);
            
            // Page Orientation
            try {
                Object pageSettings = reportClass.getMethod("getPageSettings").invoke(reportInstance);
                Class<?> orientationEnum = Class.forName("com.jettra.report.Report$PageSettings$Orientation", true, loader);
                Object orientationVal = Enum.valueOf((Class<Enum>)orientationEnum, reportOrientation.toUpperCase());
                pageSettings.getClass().getMethod("setOrientation", orientationEnum).invoke(pageSettings, orientationVal);
            } catch (Exception e) {
                System.err.println("[CrudView] Error setting orientation: " + e.getMessage());
            }

            // Header
            Object headerObj = reportClass.getMethod("getHeader").invoke(reportInstance);
            Class<?> headerClass = headerObj.getClass();
            Class<?> reportElementClass = Class.forName("com.jettra.report.Report$ReportElement", true, loader);
            Method addElement = headerClass.getMethod("addElement", reportElementClass);
            Class<?> textElementClass = Class.forName("com.jettra.report.Report$TextElement", true, loader);
            
            String finalTitle = (reportCustomTitle != null && !reportCustomTitle.isEmpty()) ? reportCustomTitle : "LISTADO DE " + modelName.toUpperCase();
            Object titleElement = textElementClass.getConstructor(String.class).newInstance(finalTitle);
            
            // Apply header color and bold
            textElementClass.getMethod("setFontColor", String.class).invoke(titleElement, reportHeaderColor);
            textElementClass.getMethod("setBold", boolean.class).invoke(titleElement, true);
            textElementClass.getMethod("setFontSize", int.class).invoke(titleElement, 14);
            
            addElement.invoke(headerObj, titleElement);

            // Table
            Object detailObj = reportClass.getMethod("getDetail").invoke(reportInstance);
            Class<?> tableClass = Class.forName("com.jettra.report.Report$Table", true, loader);
            Object tableInstance = tableClass.getConstructor().newInstance();
            Class<?> columnClass = Class.forName("com.jettra.report.Report$Column", true, loader);
            Method addColumn = tableClass.getMethod("addColumn", columnClass);

            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                String lbl = getFieldLabel(field);
                Object col = columnClass.getConstructor(String.class, String.class, int.class).newInstance(lbl, field.getName(), 150);
                
                if (field.getName().equalsIgnoreCase("id") || field.getName().equalsIgnoreCase("code")) {
                    columnClass.getMethod("setFontColor", String.class).invoke(col, reportHeaderColor);
                    columnClass.getMethod("setBold", boolean.class).invoke(col, true);
                }
                
                addColumn.invoke(tableInstance, col);
            }
            Method addDetailElement = detailObj.getClass().getMethod("addElement", reportElementClass);
            addDetailElement.invoke(detailObj, tableInstance);

            // ViewerOptions
            Object optionsObj = reportClass.getMethod("getViewerOptions").invoke(reportInstance);
            Class<?> optionsClass = optionsObj.getClass();
            optionsClass.getMethod("setShowViewer", boolean.class).invoke(optionsObj, reportShowViewer);
            optionsClass.getMethod("setAllowPrint", boolean.class).invoke(optionsObj, reportAllowPrint);
            optionsClass.getMethod("setAllowPdf", boolean.class).invoke(optionsObj, reportAllowPdf);
            optionsClass.getMethod("setAllowExcel", boolean.class).invoke(optionsObj, reportAllowExcel);
            optionsClass.getMethod("setAllowCsv", boolean.class).invoke(optionsObj, reportAllowCsv);
            
            // Create Viewer
            Method createViewer = reportClass.getMethod("createViewer", String.class);
            this.reportModal = (Modal) createViewer.invoke(reportInstance, uniqueId);
            return;
        } catch (Exception e) {
            System.err.println("[CrudView] CRITICAL: Reflection error in setupReportModal: " + e.getMessage());
            e.printStackTrace();
            setupFallbackModal(uniqueId, e.toString() + " - " + e.getMessage());
            return;
        }
    }

    private void setupFallbackModal(String uniqueId, String errorMsg) {
        this.reportModal = new Modal("reportModal_" + uniqueId)
                .setPadding("0")
                .setMaxWidth("900px")
                .setBackgroundColor("#161b22")
                .setZIndex("9999");
        this.reportModal.setStyle("display", "none");
        this.reportModal.setStyle("border", "1px solid #30363d");

        Div toolbar = new Div()
            .setStyle("display", "flex").setStyle("flex-wrap", "wrap").setStyle("gap", "8px").setStyle("padding", "15px")
            .setStyle("background-color", "#0d1117").setStyle("border-bottom", "1px solid #30363d").setStyle("border-radius", "8px 8px 0 0");

        if (reportAllowPdf) {
            toolbar.add(new Button("📄\nPDF").setBackgroundColor("#da3633")
                .setStyle("height", "60px").setStyle("width", "70px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=pdf';"));
        }
        if (reportAllowExcel) {
            toolbar.add(new Button("📊\nExcel").setBackgroundColor("#238636")
                .setStyle("height", "60px").setStyle("width", "70px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=excel';"));
        }
        if (reportAllowCsv) {
            toolbar.add(new Button("📝\nCSV").setBackgroundColor("#8957e5")
                .setStyle("height", "60px").setStyle("width", "70px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=csv';"));
        }
        toolbar.add(new Button("📘\nWord").setBackgroundColor("#0969da")
                .setStyle("height", "60px").setStyle("width", "70px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=word';"));
        
        if (reportAllowPrint) {
            toolbar.add(new Button("🖨️\nImprimir").setBackgroundColor("#007bff")
                .setStyle("height", "60px").setStyle("width", "100px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=pdf&print=true';"));
        }

        SelectOne sizeSelect = new SelectOne("sizeSelect")
            .setStyle("height", "60px").setStyle("padding", "0 15px").setStyle("background-color", "#21262d").setStyle("color", "white").setStyle("border", "1px solid #30363d").setStyle("border-radius", "6px")
            .addOption("100%", "Maximizar (100%)")
            .addOption("900px", "Original (900px)")
            .setProperty("onchange", "var size = this.value; var modal = document.getElementById('reportModal_" + uniqueId + "'); if(modal) modal.style.width = size;");
        toolbar.add(sizeSelect);

        toolbar.add(new Button("Cerrar").setBackgroundColor("#30363d")
                .setStyle("height", "60px").setStyle("padding", "0 20px")
                .setOnclick("document.getElementById('reportModal_" + uniqueId + "').style.display='none';"));

        this.reportModal.add(toolbar);

        Div previewArea = new Div()
            .setStyle("background-color", "white").setStyle("color", "#333").setStyle("padding", "40px").setStyle("margin", "20px").setStyle("border-radius", "4px").setStyle("text-align", "center");
        
        previewArea.add(new Header(3, getLabel("lbl.report_preview", "Vista Previa no disponible")))
                  .add(new Paragraph(getLabel("msg.report_engine_missing", "El motor JettraReport no está detectado. Use los botones superiores para exportar directamente.")))
                  .add(new Paragraph("Error: " + (errorMsg != null ? errorMsg : "Desconocido")).setStyle("color", "red").setStyle("font-size", "12px").setStyle("margin-top", "20px"));
        
        this.reportModal.add(previewArea);
    }

    private void setupModal(String uniqueId) {
        this.crudModal = new Modal("crudModal_" + uniqueId)
                .setPadding("35px")
                .setMaxWidth("650px")
                .setZIndex("9999");
        this.crudModal.setStyle("display", "none");

        this.modalHeader = new Header(3, "Operación");

        Form form = new Form("crudForm_" + uniqueId, "");
        this.modalAction = new TextBox("hidden", "action").setId("crudAction_" + uniqueId);

        for (Field field : modelClass.getDeclaredFields()) {
            FormGroup group = new FormGroup();
            group.setProperty("id", "group_" + field.getName() + "_" + uniqueId);
            group.add(new Label(field.getName(), getFieldLabel(field)));
            
            UIComponent input;
            if (field.isAnnotationPresent(ViewSelectOne.class)) {
                ViewSelectOne anno = field.getAnnotation(ViewSelectOne.class);
                SelectOne select = new SelectOne(field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                populateSelectOptions(select, anno.source(), anno.method(), anno.label(), anno.filter());
                input = select;
            } else if (field.isAnnotationPresent(ViewSelectMany.class)) {
                ViewSelectMany anno = field.getAnnotation(ViewSelectMany.class);
                SelectMany select = new SelectMany(field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                populateSelectOptions(select, anno.source(), anno.method(), anno.label(), anno.filter());
                input = select;
            } else {
                TextBox text = new TextBox("text", field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                JettraValidations.apply(text, modelClass, field.getName());
                input = text;
            }
            
            group.add(input);
            inputFields.add(input);
            groupFields.add(group);
            form.add(group);
        }

        this.deleteMsg = new Paragraph(getLabel("msg.confirm.delete", "¿Está seguro de eliminar este registro?"));
        this.deleteMsg.setProperty("id", "deleteMsg_" + uniqueId);
        this.deleteMsg.setColor("#f85149").setStyle("display", "none");

        Div actions = new Div().addClass("modal-actions");
        actions.setStyle("display", "flex").setStyle("gap", "10px").setStyle("margin-top", "20px");

        Button cancelBtn = new Button(getLabel("btn.cancel", "Cancelar"))
                .setType("button")
                .setBackgroundColor("#30363d")
                .setOnclick("document.getElementById('crudModal_" + uniqueId + "').style.display='none'; return false;");

        this.modalSubmitBtn = new Button(getLabel("btn.save", "Guardar"))
                .setId("modalSubmitBtn_" + uniqueId)
                .setType("submit")
                .setBackgroundColor("#238636");

        actions.add(cancelBtn).add(this.modalSubmitBtn);
        form.add(this.modalAction).add(this.deleteMsg).add(actions);

        this.crudModal.add(this.modalHeader).add(form);
    }

    private String getFieldLabel(Field field) {
        if (field.isAnnotationPresent(PropertiesLabel.class)) {
            PropertiesLabel anno = field.getAnnotation(PropertiesLabel.class);
            return getLabel(anno.value(), anno.label());
        }
        return field.getName();
    }

    private String getLabel(String key, String defaultValue) {
        if (messages != null && messages.containsKey(key)) {
            return messages.getProperty(key);
        }
        return defaultValue;
    }

    private String getIdValue(Object item) throws Exception {
        Field idField = modelClass.getDeclaredFields()[0];
        try {
            idField = modelClass.getDeclaredField("code");
        } catch (Exception e) {
            try {
                idField = modelClass.getDeclaredField("id");
            } catch (Exception e2) {}
        }
        idField.setAccessible(true);
        Object val = idField.get(item);
        return val != null ? val.toString() : "0";
    }

    private String getJsonDataFromMap(Map<String, String> data) {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String valStr = entry.getValue().replace("'", "\\'").replace("\"", "\\\"");
            sb.append("'").append(entry.getKey()).append("': '").append(valStr).append("'");
            if (++count < data.size()) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private String getJsonData(Object item) throws Exception {
        StringBuilder sb = new StringBuilder("{");
        Field[] fields = modelClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object val = fields[i].get(item);
            String valStr = val != null ? val.toString().replace("'", "\\'").replace("\"", "\\\"") : "";
            sb.append("'").append(fields[i].getName()).append("': '").append(valStr).append("'");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private void injectScripts(String uniqueId) {
        StringBuilder script = new StringBuilder();
        script.append("function showCrudModal_").append(uniqueId).append("(action, isEdit, data) {\n")
              .append("  console.log('showCrudModal_").append(uniqueId).append("', action, isEdit, data);\n")
              .append("  const modal = document.getElementById('crudModal_").append(uniqueId).append("');\n")
              .append("  const actionInput = document.getElementById('crudAction_").append(uniqueId).append("');\n")
              .append("  const submitBtn = document.getElementById('modalSubmitBtn_").append(uniqueId).append("');\n")
              .append("  const deleteMsg = document.getElementById('deleteMsg_").append(uniqueId).append("');\n")
              .append("  \n")
              .append("  if(!modal) { console.error('Modal not found: crudModal_").append(uniqueId).append("'); return; }\n")
              .append("  \n")
              .append("  modal.style.display = 'flex';\n")
              .append("  if(actionInput) actionInput.value = action;\n")
              .append("  \n");

        for (Field field : modelClass.getDeclaredFields()) {
            script.append("  const input_").append(field.getName()).append(" = document.getElementById('input_").append(field.getName()).append("_").append(uniqueId).append("');\n")
                  .append("  const group_").append(field.getName()).append(" = document.getElementById('group_").append(field.getName()).append("_").append(uniqueId).append("');\n")
                  .append("  if(input_").append(field.getName()).append(") {\n")
                  .append("    let val = data ? (data['").append(field.getName()).append("'] || '') : '';\n")
                  .append("    if(input_").append(field.getName()).append(".classList.contains('j-select-many-list')) {\n")
                  .append("       const hidden = document.getElementById('input_").append(field.getName()).append("_").append(uniqueId).append("_value');\n")
                  .append("       if(hidden) hidden.value = val;\n")
                  .append("       const vals = val.split(',');\n")
                  .append("       input_").append(field.getName()).append(".querySelectorAll('.j-select-many-item').forEach(item => {\n")
                  .append("          const isSel = vals.includes(item.getAttribute('data-value'));\n")
                  .append("          item.classList.toggle('selected', isSel);\n")
                  .append("          const inner = item.querySelector('.j-checkbox-inner');\n")
                  .append("          if(inner) inner.innerText = isSel ? '✓' : '';\n")
                  .append("       });\n")
                  .append("    } else {\n")
                  .append("       input_").append(field.getName()).append(".value = val;\n")
                  .append("    }\n")
                  .append("    input_").append(field.getName()).append(".readOnly = (action === 'save' && isEdit && '").append(field.getName()).append("' === 'code');\n")
                  .append("  }\n");
        }

        script.append("  if(action === 'delete') {\n")
              .append("    if(deleteMsg) deleteMsg.style.display = 'block';\n")
              .append("    if(submitBtn) {\n")
              .append("       submitBtn.innerText = '").append(getLabel("btn.confirm.delete", "Eliminar")).append("';\n")
              .append("       submitBtn.style.backgroundColor = '#da3633';\n")
              .append("    }\n");
        for (Field field : modelClass.getDeclaredFields()) {
            script.append("    if(group_").append(field.getName()).append(") group_").append(field.getName()).append(".style.display = 'none';\n");
        }
        script.append("  } else {\n")
              .append("    if(deleteMsg) deleteMsg.style.display = 'none';\n")
              .append("    if(submitBtn) {\n")
              .append("       submitBtn.innerText = '").append(getLabel("btn.save", "Guardar")).append("';\n")
              .append("       submitBtn.style.backgroundColor = '#238636';\n")
              .append("    }\n");
        for (Field field : modelClass.getDeclaredFields()) {
            script.append("    if(group_").append(field.getName()).append(") group_").append(field.getName()).append(".style.display = 'block';\n");
        }
        script.append("  }\n")
              .append("}\n");

        this.add(new Script(script.toString()));
    }

    private void populateSelectOptions(SelectOne select, String source, String method, String labelFields, String filter) {
        try {
            Class<?> sourceClass = null;
            String pkg = modelClass.getPackageName();
            
            // Intentar cargar la clase fuente por nombre completo o relativo a paquetes comunes
            List<String> attempts = Arrays.asList(
                source,
                pkg + "." + source,
                pkg.replace(".model", ".repository") + "." + source,
                pkg.replace(".model", ".services") + "." + source,
                pkg.replace(".model", ".controller") + "." + source
            );

            for (String attempt : attempts) {
                try {
                    sourceClass = Class.forName(attempt, true, modelClass.getClassLoader());
                    break;
                } catch (Exception e) {}
            }

            if (sourceClass != null) {
                Method m;
                Object result;
                if (filter != null && !filter.isEmpty()) {
                    m = sourceClass.getMethod(method, String.class);
                    result = m.invoke(null, filter);
                } else {
                    m = sourceClass.getMethod(method);
                    result = m.invoke(null);
                }

                if (result instanceof List) {
                    List<?> list = (List<?>) result;
                    for (Object obj : list) {
                        String val = getIdValueForSource(obj);
                        String lbl = resolveLabel(obj, labelFields);
                        select.addOption(val, lbl);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[CrudView] Error populating select options for source " + source + ": " + e.getMessage());
        }
    }

    private String getIdValueForSource(Object item) {
        if (item instanceof String) return (String) item;
        if (item instanceof Number) return item.toString();
        
        try {
            for (String idName : Arrays.asList("code", "id", "id" + item.getClass().getSimpleName())) {
                try {
                    Field f = item.getClass().getDeclaredField(idName);
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val != null) return val.toString();
                } catch (Exception e) {}
            }
            
            // Try getters
            for (String idName : Arrays.asList("getCode", "getId")) {
                try {
                    Method m = item.getClass().getMethod(idName);
                    Object val = m.invoke(item);
                    if (val != null) return val.toString();
                } catch (Exception e) {}
            }
        } catch (Exception e) {}
        return "0";
    }

    private String resolveObjectField(Object obj, String fieldName) {
        if (obj == null) return "";
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object val = f.get(obj);
            return val != null ? val.toString() : "";
        } catch (Exception e) {
            try {
                String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method m = obj.getClass().getMethod(getterName);
                Object val = m.invoke(obj);
                return val != null ? val.toString() : "";
            } catch (Exception e2) {
                return obj.toString();
            }
        }
    }

    private String resolveLabel(Object obj, String labelFields) {
        if (obj instanceof String) return (String) obj;
        String[] fields = labelFields.split(",");
        List<String> values = new ArrayList<>();
        
        for (String fieldName : fields) {
            try {
                String name = fieldName.trim();
                Field f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
                Object val = f.get(obj);
                if (val != null) values.add(val.toString());
            } catch (Exception e) {
                // Try getter
                try {
                    String name = fieldName.trim();
                    String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method m = obj.getClass().getMethod(getterName);
                    Object val = m.invoke(obj);
                    if (val != null) values.add(val.toString());
                } catch (Exception e2) {}
            }
        }
        return values.isEmpty() ? obj.toString() : values.stream().collect(Collectors.joining(" - "));
    }
}
