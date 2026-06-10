package io.jettra.wui.complex;

import io.jettra.wui.components.*;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.*;
import io.jettra.wui.core.annotations.TableColumnField;
import io.jettra.rules.annotations.Compute;
import io.jettra.rules.enums.OperationType;
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
    private boolean reportAllowWord = true;
    private String reportOrientation = "PORTRAIT";
    private String reportHeaderColor = "#000000";
    private String reportCustomTitle = "";
    private boolean editable = false;
    private io.jettra.wui.core.Page parentPage;

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
    public CrudView setReportAllowCsv(boolean reportAllowCsv) {
        this.reportAllowCsv = reportAllowCsv;
        return this;
    }

    public CrudView setReportAllowWord(boolean reportAllowWord) {
        this.reportAllowWord = reportAllowWord;
        return this;
    }
    public CrudView setReportOrientation(String orientation) { this.reportOrientation = orientation; return this; }
    public CrudView setReportHeaderColor(String color) { this.reportHeaderColor = color; return this; }
    public CrudView setReportCustomTitle(String title) { this.reportCustomTitle = title; return this; }
    public CrudView setEditable(boolean editable) { this.editable = editable; return this; }
    public CrudView setParentPage(io.jettra.wui.core.Page parentPage) { this.parentPage = parentPage; return this; }

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
            if (field.isAnnotationPresent(io.jettra.wui.core.annotations.Hidden.class)) {
                continue;
            }
            if (field.isAnnotationPresent(ViewDataTable.class) && !field.getAnnotation(ViewDataTable.class).showRowInMasterTable()) {
                continue;
            }
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
                    String idValue = (handler != null) ? ((CrudHandler<Object>)handler).getIdValue(item) : getIdValue(item);
                               for (Field field : fields) {
                        if (field.isAnnotationPresent(io.jettra.wui.core.annotations.Hidden.class)) {
                            continue;
                        }
                        if (field.isAnnotationPresent(ViewDataTable.class) && !field.getAnnotation(ViewDataTable.class).showRowInMasterTable()) {
                            continue;
                        }
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
                            } else if (field.isAnnotationPresent(ViewSelectOne.class)) {
                                ViewSelectOne vso = field.getAnnotation(ViewSelectOne.class);
                                String fomt = vso.fieldOnlyMasterTable();
                                if (fomt != null && !fomt.trim().isEmpty()) {
                                    displayValue = resolveLabel(val, fomt);
                                } else {
                                    String labelFields = vso.label();
                                    if (labelFields != null && !labelFields.trim().isEmpty()) {
                                        displayValue = resolveLabel(val, labelFields);
                                    } else {
                                        displayValue = val.toString();
                                    }
                                }
                            } else if (field.isAnnotationPresent(ViewSelectMany.class)) {
                                ViewSelectMany vsm = field.getAnnotation(ViewSelectMany.class);
                                String fomt = vsm.fieldOnlyMasterTable();
                                String finalFomt = (fomt != null && !fomt.trim().isEmpty()) ? fomt : vsm.label();
                                if (val instanceof List) {
                                    List<?> list = (List<?>) val;
                                    displayValue = list.stream()
                                        .map(obj -> resolveLabel(obj, finalFomt))
                                        .collect(Collectors.joining(", "));
                                } else {
                                    displayValue = resolveLabel(val, finalFomt);
                                }
                            } else {
                                displayValue = val.toString();
                            }
                        }
                        
                        TD td = new TD();
                        if (field.isAnnotationPresent(ViewDataTable.class)) {
                            ViewDataTable anno = field.getAnnotation(ViewDataTable.class);
                            Datatable cellTable = new Datatable();
                            cellTable.setStyle("font-size", "11px").setStyle("margin", "5px 0").setStyle("width", "100%");
                            
                            Row cellHeader = new Row();
                            String[] colNames = anno.row().split(",");
                            for (String colName : colNames) {
                                String cleanCol = colName.trim();
                                if (!cleanCol.isEmpty()) {
                                    cellHeader.add(new TD(cleanCol.toUpperCase()).setStyle("font-size", "9px").setStyle("color", "#8b949e").setStyle("padding", "2px 5px"));
                                }
                            }
                            cellTable.addHeaderRow(cellHeader);
                            
                            if (val instanceof List) {
                                List<?> list = (List<?>) val;
                                for (Object rowObj : list) {
                                    Row cellRow = new Row();
                                    for (String colName : colNames) {
                                        String cleanCol = colName.trim();
                                        if (cleanCol.isEmpty()) continue;
                                        try {
                                            Field itemField = rowObj.getClass().getDeclaredField(cleanCol);
                                            itemField.setAccessible(true);
                                            Object itemVal = itemField.get(rowObj);
                                            String valStr = "";
                                            if (itemVal != null) {
                                                if (itemField.isAnnotationPresent(ViewSelectOne.class)) {
                                                    valStr = getIdValueForSource(itemVal);
                                                } else {
                                                    valStr = itemVal.toString();
                                                }
                                            }
                                            cellRow.add(new TD(valStr).setStyle("padding", "2px 5px"));
                                        } catch (Exception ex) {
                                            cellRow.add(new TD("").setStyle("padding", "2px 5px"));
                                        }
                                    }
                                    cellTable.addRow(cellRow);
                                }
                            }
                            td.add(cellTable);
                        } else {
                            boolean masterEditable = this.editable;
                            for (Field f : fields) {
                                if (f.isAnnotationPresent(ViewDataTable.class)) {
                                    if (!f.getAnnotation(ViewDataTable.class).editableRowMaster()) {
                                        masterEditable = false;
                                        break;
                                    }
                                }
                            }
                            if (masterEditable && !field.isAnnotationPresent(Hidden.class)) {
                                boolean isReadonly = field.isAnnotationPresent(NoEditable.class);
                                if (field.isAnnotationPresent(Compute.class) && !field.getAnnotation(Compute.class).editable()) {
                                    isReadonly = true;
                                }
                                if (field.isAnnotationPresent(ViewSelectOne.class)) {
                                    ViewSelectOne anno = field.getAnnotation(ViewSelectOne.class);
                                    SelectOne select = new SelectOne("table_" + field.getName() + "_" + idValue);
                                    select.setId("table_" + field.getName() + "_" + idValue);
                                    populateSelectOptions(select, anno.source(), anno.method(), anno.label(), anno.filter());
                                    if (val != null) select.setProperty("value", getIdValueForSource(val));
                                    if (isReadonly) {
                                        select.setStyle("pointer-events", "none")
                                              .setStyle("background", "transparent")
                                              .setStyle("color", "var(--jettra-text, #ffffff)")
                                              .setStyle("border", "none");
                                    } else {
                                        select.setStyle("background", "var(--jettra-glass, rgba(0,0,0,0.3))").setStyle("color", "var(--jettra-text, #ffffff)");
                                        select.setProperty("onchange", "saveInlineEdit_" + uniqueId + "('" + idValue + "', '" + field.getName() + "', this.value)");
                                    }
                                    td.add(select);
                                } else if (field.getType() == java.time.LocalDate.class || field.getType() == java.util.Date.class) {
                                    DatePicker datePicker = new DatePicker("table_" + field.getName() + "_" + idValue, "");
                                    if (val != null) datePicker.setValue(val.toString());
                                    datePicker.setStyle("width", "100%").setStyle("box-sizing", "border-box");
                                    if (isReadonly) {
                                        datePicker.setEditable(false);
                                    } else {
                                        datePicker.setOnChange("saveInlineEdit_" + uniqueId + "('" + idValue + "', '" + field.getName() + "', this.value)");
                                    }
                                    td.add(datePicker);
                                } else {
                                    TextBox textBox = new TextBox("text", "table_" + field.getName() + "_" + idValue);
                                    textBox.setId("table_" + field.getName() + "_" + idValue);
                                    textBox.setProperty("value", displayValue);
                                    textBox.setStyle("width", "100%").setStyle("box-sizing", "border-box").setStyle("padding", "4px").setStyle("border", "1px solid var(--jettra-border, #ccc)");
                                    if (isReadonly) {
                                        textBox.setReadonly(true);
                                        textBox.setStyle("background", "transparent");
                                        textBox.setStyle("color", "var(--jettra-text, #ffffff)");
                                        textBox.setStyle("cursor", "not-allowed");
                                        textBox.setStyle("border", "none");
                                    } else {
                                        textBox.setStyle("background", "var(--jettra-glass, rgba(0,0,0,0.3))").setStyle("color", "var(--jettra-text, #ffffff)");
                                        textBox.setProperty("onchange", "saveInlineEdit_" + uniqueId + "('" + idValue + "', '" + field.getName() + "', this.value)");
                                    }
                                    td.add(textBox);
                                }
                            } else {
                                td.setContent(displayValue);
                            }
                        }
                        dataRow.add(td);
                    }

                    TD actionsTd = new TD();
                    String jsonData = (handler != null) ? getJsonDataFromMap(((CrudHandler<Object>)handler).getJsonMap(item)) : getJsonData(item);

                    Button editBtn = new Button("✏️")
                            .setId("editBtn_" + idValue + "_" + uniqueId)
                            .setOnclick("showCrudModal_" + uniqueId + "('save', true, " + jsonData + ")");

                    Button deleteBtn = new Button("🗑️")
                            .setId("deleteBtn_" + idValue + "_" + uniqueId)
                            .setOnclick("showCrudModal_" + uniqueId + "('delete', true, " + jsonData + ")");

                    Button printBtn = new Button("🖨️")
                            .setId("printBtn_" + idValue + "_" + uniqueId)
                            .setOnclick("window.open('?action=report&format=pdf&print=true&id=" + idValue + "', '_blank')");

                    actionsTd.add(editBtn).add(deleteBtn).add(printBtn);
                    dataRow.add(actionsTd);
                    table.addRow(dataRow);
                }
            }
        } catch (Exception e) {
            System.err.println("[CrudView] Error loading items: " + e.getMessage());
        }

        Div tableWrapper = new Div().setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("width", "100%").setStyle("gap", "20px");
        tableWrapper.add(table);

        if (this.editable) {
            Field computeField = null;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Compute.class)) {
                    computeField = field;
                    break;
                }
            }
            if (computeField != null) {
                String labelStr = getFieldLabel(computeField);
                Div sumContainer = new Div().setStyle("margin-top", "20px")
                        .setStyle("display", "flex")
                        .setStyle("justify-content", "flex-end")
                        .setStyle("padding-right", "20px");
                Header sumHeader = new Header(3, "Gran Total " + labelStr + ": $<span id='gran_total_" + uniqueId + "'>0.00</span>");
                sumHeader.setStyle("color", "#238636");
                sumContainer.add(sumHeader);
                tableWrapper.add(sumContainer);
            }
        }

        mainCard.add(tableWrapper);

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

            Object reportInstance = null;
            boolean isCustomReport = false;
            if (parentPage != null) {
                try {
                    Method getCustomReport = parentPage.getClass().getMethod("getCustomReport");
                    reportInstance = getCustomReport.invoke(parentPage);
                    isCustomReport = true;
                    System.out.println("[CrudView] Loaded custom report from page class: " + parentPage.getClass().getName());
                } catch (NoSuchMethodException e) {
                    // No custom report method
                } catch (Exception e) {
                    System.err.println("[CrudView] Error loading custom report: " + e.getMessage());
                }
            }

            if (reportInstance == null) {
                reportInstance = reportClass.getConstructor(String.class).newInstance("Reporte de " + modelClass.getSimpleName());
            }
            
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
            
            if (!isCustomReport) {
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
                
                // Determinar tipo de reporte (MASTER/DETAILS o NORMAL)
                boolean isMasterDetail = false;
                for (Field f : modelClass.getDeclaredFields()) {
                    if (f.isAnnotationPresent(ViewDataTable.class)) {
                        isMasterDetail = true;
                        break;
                    }
                }

                // Cargar anotaciones de cabecera custom vía reflexión para evitar dependencia circular
                Class<? extends java.lang.annotation.Annotation> disabledHeaderClass = null;
                Class<? extends java.lang.annotation.Annotation> mrhClass = null;
                Class<? extends java.lang.annotation.Annotation> mrfClass = null;
                try {
                    disabledHeaderClass = (Class<? extends java.lang.annotation.Annotation>) loader.loadClass("com.jettra.report.annotations.ModelReportDisabledHeader");
                    mrhClass = (Class<? extends java.lang.annotation.Annotation>) loader.loadClass("com.jettra.report.annotations.ModelReportHeader");
                    mrfClass = (Class<? extends java.lang.annotation.Annotation>) loader.loadClass("com.jettra.report.annotations.ModelReportFooter");
                } catch (Exception e) {}

                boolean headerDisabled = false;
                if (disabledHeaderClass != null) {
                    if (modelClass.isAnnotationPresent(disabledHeaderClass) || (parentPage != null && parentPage.getClass().isAnnotationPresent(disabledHeaderClass))) {
                        headerDisabled = true;
                    }
                }

                if (!headerDisabled) {
                    String finalTitle = (reportCustomTitle != null && !reportCustomTitle.isEmpty()) ? reportCustomTitle : "LISTADO DE " + modelName.toUpperCase();
                    Object titleElement = textElementClass.getConstructor(String.class).newInstance(finalTitle);
                    
                    // Apply header color and bold
                    textElementClass.getMethod("setFontColor", String.class).invoke(titleElement, reportHeaderColor);
                    textElementClass.getMethod("setBold", boolean.class).invoke(titleElement, true);
                    textElementClass.getMethod("setFontSize", int.class).invoke(titleElement, 14);
                    
                    addElement.invoke(headerObj, titleElement);
                }

                // Procesar cabeceras repetibles con @ModelReportHeader y ReportType
                java.lang.annotation.Annotation[] headers = null;
                if (mrhClass != null) {
                    try {
                        Class<? extends java.lang.annotation.Annotation> headersContainer = (Class<? extends java.lang.annotation.Annotation>) loader.loadClass("com.jettra.report.annotations.ModelReportHeaders");
                        if (modelClass.isAnnotationPresent(mrhClass) || modelClass.isAnnotationPresent(headersContainer)) {
                            headers = modelClass.getAnnotationsByType(mrhClass);
                        } else if (parentPage != null && (parentPage.getClass().isAnnotationPresent(mrhClass) || parentPage.getClass().isAnnotationPresent(headersContainer))) {
                            headers = parentPage.getClass().getAnnotationsByType(mrhClass);
                        }
                    } catch(Exception e) {
                        try {
                            headers = modelClass.getAnnotationsByType(mrhClass);
                            if ((headers == null || headers.length == 0) && parentPage != null) {
                                headers = parentPage.getClass().getAnnotationsByType(mrhClass);
                            }
                        } catch(Exception ex) {}
                    }
                }

                if (headers != null) {
                    for (java.lang.annotation.Annotation mrh : headers) {
                        try {
                            Object typeEnumVal = mrhClass.getMethod("type").invoke(mrh);
                            String typeStr = (typeEnumVal != null) ? typeEnumVal.toString() : "NORMAL";
                            
                            boolean match = false;
                            if (isMasterDetail) {
                                if (typeStr.equals("MASTER")) {
                                    match = true;
                                }
                            } else {
                                if (typeStr.equals("NORMAL")) {
                                    match = true;
                                }
                            }
                            
                            if (!match) continue;

                            String mrhValue = (String) mrhClass.getMethod("value").invoke(mrh);
                            String mrhLabel = (String) mrhClass.getMethod("label").invoke(mrh);
                            String headerText = (mrhValue != null && !mrhValue.isEmpty()) ? mrhValue : mrhLabel;

                            if (headerText != null && !headerText.isEmpty()) {
                                String fontName = (String) mrhClass.getMethod("font").invoke(mrh);
                                int fontSize = (int) mrhClass.getMethod("size").invoke(mrh);
                                String textColorVal = (String) mrhClass.getMethod("textColor").invoke(mrh);
                                Object orientationObj = mrhClass.getMethod("orientation").invoke(mrh);
                                String alignment = (orientationObj != null) ? orientationObj.toString() : "LEFT";

                                boolean isBold = false;
                                boolean isItalic = false;
                                boolean isUnderline = false;
                                boolean isStrikethrough = false;

                                Object[] styles = (Object[]) mrhClass.getMethod("style").invoke(mrh);
                                if (styles != null) {
                                    for (Object s : styles) {
                                        String styleName = s.toString();
                                        if (styleName.equals("BOLD")) isBold = true;
                                        else if (styleName.equals("ITALIC")) isItalic = true;
                                        else if (styleName.equals("SUBLINE")) isUnderline = true;
                                        else if (styleName.equals("STRIKETHROUGH")) isStrikethrough = true;
                                    }
                                }

                                Object customTitleEl = textElementClass.getConstructor(String.class).newInstance(headerText);
                                textElementClass.getMethod("setFontName", String.class).invoke(customTitleEl, fontName);
                                textElementClass.getMethod("setFontSize", int.class).invoke(customTitleEl, fontSize);
                                textElementClass.getMethod("setFontColor", String.class).invoke(customTitleEl, textColorVal);
                                textElementClass.getMethod("setAlignment", String.class).invoke(customTitleEl, alignment);
                                textElementClass.getMethod("setBold", boolean.class).invoke(customTitleEl, isBold);
                                try {
                                    textElementClass.getMethod("setItalic", boolean.class).invoke(customTitleEl, isItalic);
                                    textElementClass.getMethod("setUnderline", boolean.class).invoke(customTitleEl, isUnderline);
                                    textElementClass.getMethod("setStrikethrough", boolean.class).invoke(customTitleEl, isStrikethrough);
                                } catch(Exception e) {}

                                addElement.invoke(headerObj, customTitleEl);
                            }
                        } catch (Exception e) {
                            System.err.println("Error processing @ModelReportHeader in CrudView: " + e.getMessage());
                        }
                    }
                }

                // Procesar pies de página repetibles con @ModelReportFooter y ReportType
                java.lang.annotation.Annotation[] footers = null;
                if (mrfClass != null) {
                    try {
                        Class<? extends java.lang.annotation.Annotation> footersContainer = (Class<? extends java.lang.annotation.Annotation>) loader.loadClass("com.jettra.report.annotations.ModelReportFooters");
                        if (modelClass.isAnnotationPresent(mrfClass) || modelClass.isAnnotationPresent(footersContainer)) {
                            footers = modelClass.getAnnotationsByType(mrfClass);
                        } else if (parentPage != null && (parentPage.getClass().isAnnotationPresent(mrfClass) || parentPage.getClass().isAnnotationPresent(footersContainer))) {
                            footers = parentPage.getClass().getAnnotationsByType(mrfClass);
                        }
                    } catch(Exception e) {
                        try {
                            footers = modelClass.getAnnotationsByType(mrfClass);
                            if ((footers == null || footers.length == 0) && parentPage != null) {
                                footers = parentPage.getClass().getAnnotationsByType(mrfClass);
                            }
                        } catch(Exception ex) {}
                    }
                }

                if (footers != null) {
                    try {
                        Object footerObj = reportClass.getMethod("getFooter").invoke(reportInstance);
                        Class<?> footerClass = footerObj.getClass();
                        Method addFooterElement = footerClass.getMethod("addElement", reportElementClass);

                        for (java.lang.annotation.Annotation mrf : footers) {
                            try {
                                Object typeEnumVal = mrfClass.getMethod("type").invoke(mrf);
                                String typeStr = (typeEnumVal != null) ? typeEnumVal.toString() : "NORMAL";
                                
                                boolean match = false;
                                if (isMasterDetail) {
                                    if (typeStr.equals("MASTER")) {
                                        match = true;
                                    }
                                } else {
                                    if (typeStr.equals("NORMAL")) {
                                        match = true;
                                    }
                                }
                                
                                if (!match) continue;

                                String mrfValue = (String) mrfClass.getMethod("value").invoke(mrf);
                                String mrfLabel = (String) mrfClass.getMethod("label").invoke(mrf);
                                String footerText = (mrfValue != null && !mrfValue.isEmpty()) ? mrfValue : mrfLabel;

                                if (footerText != null && !footerText.isEmpty()) {
                                    String fontName = (String) mrfClass.getMethod("font").invoke(mrf);
                                    int fontSize = (int) mrfClass.getMethod("size").invoke(mrf);
                                    String textColorVal = (String) mrfClass.getMethod("textColor").invoke(mrf);
                                    Object orientationObj = mrfClass.getMethod("orientation").invoke(mrf);
                                    String alignment = (orientationObj != null) ? orientationObj.toString() : "LEFT";

                                    boolean isBold = false;
                                    boolean isItalic = false;
                                    boolean isUnderline = false;
                                    boolean isStrikethrough = false;

                                    Object[] styles = (Object[]) mrfClass.getMethod("style").invoke(mrf);
                                    if (styles != null) {
                                        for (Object s : styles) {
                                            String styleName = s.toString();
                                            if (styleName.equals("BOLD")) isBold = true;
                                            else if (styleName.equals("ITALIC")) isItalic = true;
                                            else if (styleName.equals("SUBLINE")) isUnderline = true;
                                            else if (styleName.equals("STRIKETHROUGH")) isStrikethrough = true;
                                        }
                                    }

                                    Object customFooterEl = textElementClass.getConstructor(String.class).newInstance(footerText);
                                    textElementClass.getMethod("setFontName", String.class).invoke(customFooterEl, fontName);
                                    textElementClass.getMethod("setFontSize", int.class).invoke(customFooterEl, fontSize);
                                    textElementClass.getMethod("setFontColor", String.class).invoke(customFooterEl, textColorVal);
                                    textElementClass.getMethod("setAlignment", String.class).invoke(customFooterEl, alignment);
                                    textElementClass.getMethod("setBold", boolean.class).invoke(customFooterEl, isBold);
                                    try {
                                        textElementClass.getMethod("setItalic", boolean.class).invoke(customFooterEl, isItalic);
                                        textElementClass.getMethod("setUnderline", boolean.class).invoke(customFooterEl, isUnderline);
                                        textElementClass.getMethod("setStrikethrough", boolean.class).invoke(customFooterEl, isStrikethrough);
                                    } catch(Exception e) {}

                                    addFooterElement.invoke(footerObj, customFooterEl);
                                }
                            } catch (Exception e) {
                                System.err.println("Error processing @ModelReportFooter: " + e.getMessage());
                            }
                        }
                    } catch(Exception e) {
                        System.err.println("Error accessing report footer: " + e.getMessage());
                    }
                }

                // Table
                Object detailObj = reportClass.getMethod("getDetail").invoke(reportInstance);
                Class<?> tableClass = Class.forName("com.jettra.report.Report$Table", true, loader);
                Object tableInstance = tableClass.getConstructor().newInstance();
                Class<?> columnClass = Class.forName("com.jettra.report.Report$Column", true, loader);
                Method addColumn = tableClass.getMethod("addColumn", columnClass);

                Field[] fields = modelClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ViewDataTable.class) && !field.getAnnotation(ViewDataTable.class).showRowInMasterTable()) {
                        continue;
                    }
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
            }

            // ViewerOptions
            Object optionsObj = reportClass.getMethod("getViewerOptions").invoke(reportInstance);
            Class<?> optionsClass = optionsObj.getClass();
            optionsClass.getMethod("setShowViewer", boolean.class).invoke(optionsObj, reportShowViewer);
            optionsClass.getMethod("setAllowPrint", boolean.class).invoke(optionsObj, reportAllowPrint);
            optionsClass.getMethod("setAllowPdf", boolean.class).invoke(optionsObj, reportAllowPdf);
            optionsClass.getMethod("setAllowExcel", boolean.class).invoke(optionsObj, reportAllowExcel);
            optionsClass.getMethod("setAllowCsv", boolean.class).invoke(optionsObj, reportAllowCsv);
            optionsClass.getMethod("setAllowWord", boolean.class).invoke(optionsObj, reportAllowWord);
            
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
        if (reportAllowWord) {
            toolbar.add(new Button("📘\nWord").setBackgroundColor("#0969da")
                .setStyle("height", "60px").setStyle("width", "70px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("justify-content", "center")
                .setOnclick("location.href='?action=report&format=word';"));
        }
        
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
                .setMaxWidth("900px")
                .setZIndex("9999");
        this.crudModal.setStyle("display", "none");

        this.modalHeader = new Header(3, "Operación");

        Form form = new Form("crudForm_" + uniqueId, "");
        this.modalAction = new TextBox("hidden", "action").setId("crudAction_" + uniqueId);

        for (Field field : modelClass.getDeclaredFields()) {
            FormGroup group = new FormGroup();
            group.setProperty("id", "group_" + field.getName() + "_" + uniqueId);
            
            boolean isHidden = field.isAnnotationPresent(Hidden.class);
            if (!isHidden) {
                group.add(new Label(field.getName(), getFieldLabel(field)));
            } else {
                group.setStyle("display", "none");
            }
            
            UIComponent input;
            if (field.isAnnotationPresent(ViewSelectOne.class)) {
                ViewSelectOne anno = field.getAnnotation(ViewSelectOne.class);
                SelectOne select = new SelectOne(field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                populateSelectOptions(select, anno.source(), anno.method(), anno.label(), anno.filter());
                if (field.isAnnotationPresent(NoEditable.class)) {
                    select.setStyle("pointer-events", "none")
                          .setStyle("background-color", "rgba(48, 54, 61, 0.5)")
                          .setStyle("color", "var(--jettra-text, #ffffff)")
                          .setStyle("cursor", "not-allowed")
                          .setStyle("opacity", "0.9")
                          .setStyle("border", "1px solid rgba(255,255,255,0.1)");
                }
                input = select;
            } else if (field.isAnnotationPresent(ViewSelectMany.class)) {
                ViewSelectMany anno = field.getAnnotation(ViewSelectMany.class);
                SelectMany select = new SelectMany(field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                populateSelectOptions(select, anno.source(), anno.method(), anno.label(), anno.filter());
                if (field.isAnnotationPresent(NoEditable.class)) {
                    select.setStyle("pointer-events", "none")
                          .setStyle("background-color", "rgba(48, 54, 61, 0.5)")
                          .setStyle("color", "var(--jettra-text, #ffffff)")
                          .setStyle("cursor", "not-allowed")
                          .setStyle("opacity", "0.9")
                          .setStyle("border", "1px solid rgba(255,255,255,0.1)");
                }
                input = select;
            } else if (field.getType() == java.time.LocalDate.class || field.getType() == java.util.Date.class) {
                DatePicker datePicker = new DatePicker("input_" + field.getName() + "_" + uniqueId, isHidden ? "" : getFieldLabel(field));
                if (field.isAnnotationPresent(NoEditable.class)) {
                    datePicker.setEditable(false);
                }
                input = datePicker;
            } else if (field.isAnnotationPresent(ViewDataTable.class)) {
                ViewDataTable anno = field.getAnnotation(ViewDataTable.class);
                Div dataTableDiv = new Div().setId("viewdatatable_" + field.getName() + "_" + uniqueId);
                dataTableDiv.setStyle("border", "1px solid var(--jettra-border)").setStyle("padding", "10px").setStyle("border-radius", "8px").setStyle("margin-top", "10px").setStyle("background-color", "rgba(0,0,0,0.2)");
                
                Header dtHeader = new Header(5, getFieldLabel(field));
                dtHeader.setStyle("margin-top", "0").setStyle("color", "var(--jettra-accent)").setStyle("text-transform", "uppercase").setStyle("font-size", "11px");
                dataTableDiv.add(dtHeader);
                
                Datatable subTable = new Datatable().setId("table_" + field.getName() + "_" + uniqueId);
                Row headerRow = new Row();
                String[] rows = anno.row().split(",");
                for (String r : rows) {
                    if (!r.trim().isEmpty()) headerRow.add(new TD(r.trim().toUpperCase()).setStyle("font-size", "10px").setStyle("color", "#8b949e"));
                }
                headerRow.add(new TD("ACCIONES").setStyle("font-size", "10px").setStyle("color", "#8b949e"));
                subTable.addHeaderRow(headerRow);
                
                // Fetch initial data if possible
                try {
                    if (!anno.source().isEmpty() && !anno.method().isEmpty()) {
                        Class<?> sourceClass = null;
                        String pkg = modelClass.getPackageName();
                        List<String> attempts = Arrays.asList(
                            anno.source(),
                            pkg + "." + anno.source(),
                            pkg.replace(".model", ".repository") + "." + anno.source(),
                            pkg.replace(".model", ".services") + "." + anno.source(),
                            pkg.replace(".model", ".controller") + "." + anno.source()
                        );
                        for (String attempt : attempts) {
                            try { sourceClass = Class.forName(attempt, true, modelClass.getClassLoader()); break; } catch (Exception e) {}
                        }
                        if (sourceClass != null) {
                            Method m;
                            Object result;
                            if (anno.filter() != null && !anno.filter().isEmpty()) {
                                m = sourceClass.getMethod(anno.method(), String.class);
                                result = m.invoke(null, anno.filter());
                            } else {
                                m = sourceClass.getMethod(anno.method());
                                result = m.invoke(null);
                            }
                            if (result instanceof List) {
                                List<?> items = (List<?>) result;
                                for (Object item : items) {
                                    Row tr = new Row();
                                    for (String r : rows) {
                                        String rClean = r.trim();
                                        if (rClean.isEmpty()) continue;
                                        Field itemField = null;
                                        try {
                                            itemField = item.getClass().getDeclaredField(rClean);
                                        } catch(Exception ex) {}
                                        if (itemField != null) {
                                            itemField.setAccessible(true);
                                            Object val = itemField.get(item);
                                            
                                            // Check if editable
                                            boolean isEditable = Arrays.asList(anno.editablerow().split(",")).stream().map(String::trim).anyMatch(e -> e.equals(rClean));
                                            if (isEditable) {
                                                if (itemField.isAnnotationPresent(ViewSelectOne.class)) {
                                                    ViewSelectOne vso = itemField.getAnnotation(ViewSelectOne.class);
                                                    SelectOne select = new SelectOne("dt_" + field.getName() + "_" + rClean);
                                                    populateSelectOptions(select, vso.source(), vso.method(), vso.label(), vso.filter());
                                                    if(val != null) select.setProperty("value", val.toString());
                                                    select.setStyle("width", "100%").setStyle("background", "rgba(0,0,0,0.3)").setStyle("color", "white").setStyle("border", "1px solid var(--jettra-border)");
                                                    select.setProperty("data-col", rClean);
                                                    select.setProperty("onchange", "this.closest('tr').dataset.prodChanged='true'; updateRowCalculation_" + uniqueId + "(this.closest('tr'))");
                                                    tr.add(new TD().add(select));
                                                } else {
                                                    TextBox txt = new TextBox("text", "dt_" + field.getName() + "_" + rClean);
                                                    if(val != null) txt.setProperty("value", val.toString());
                                                    txt.setStyle("width", "100%").setStyle("background", "rgba(0,0,0,0.3)").setStyle("color", "white").setStyle("border", "1px solid var(--jettra-border)").setStyle("padding", "4px");
                                                    txt.setProperty("data-col", rClean);
                                                    txt.setProperty("oninput", "updateRowCalculation_" + uniqueId + "(this.closest('tr'))");
                                                    tr.add(new TD().add(txt));
                                                }
                                            } else {
                                                String displayValue = (val != null) ? val.toString() : "";
                                                if (itemField.isAnnotationPresent(ViewSelectOne.class)) {
                                                    displayValue = getIdValueForSource(val);
                                                }
                                                TextBox txt = new TextBox("text", "dt_" + field.getName() + "_" + rClean);
                                                txt.setProperty("value", displayValue);
                                                txt.setStyle("width", "100%").setStyle("background", "transparent").setStyle("color", "white").setStyle("border", "none").setStyle("padding", "4px");
                                                txt.setReadonly(true);
                                                txt.setProperty("data-col", rClean);
                                                tr.add(new TD().add(txt));
                                            }
                                        } else {
                                            tr.add(new TD(""));
                                        }
                                    }
                                    Button delLineBtn = new Button("🗑️").setType("button").setBackgroundColor("transparent").setStyle("padding", "4px").setStyle("border", "none").setStyle("color", "#f85149");
                                    delLineBtn.setProperty("onclick", "this.closest('tr').remove(); calculateGrandTotal_" + uniqueId + "();");
                                    tr.add(new TD().add(delLineBtn));
                                    subTable.addRow(tr);
                                }
                            }
                        }
                    }
                } catch(Exception e) {
                    System.err.println("Error fetching ViewDataTable data: " + e.getMessage());
                }

                Button addLineBtn = new Button("➕ Agregar Línea").setType("button").setBackgroundColor("#1f6feb").setStyle("margin-top", "10px").setStyle("font-size", "11px").setStyle("padding", "6px 12px");
                addLineBtn.setProperty("onclick", "addNestedRow_" + uniqueId + "('" + field.getName() + "')");
                
                dataTableDiv.add(subTable).add(addLineBtn);
                
                TextBox hiddenData = new TextBox("hidden", field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                dataTableDiv.add(hiddenData);
                
                input = dataTableDiv;
            } else {
                TextBox text = new TextBox(isHidden ? "hidden" : "text", field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
                if (!isHidden) {
                    JettraValidations.apply(text, modelClass, field.getName());
                }
                if (field.getName().equalsIgnoreCase("descuento") || field.getName().equalsIgnoreCase("porcentajeImpuesto")) {
                    text.setProperty("oninput", "calculateGrandTotal_" + uniqueId + "()");
                }
                
                boolean isReadonly = false;
                
                if (field.isAnnotationPresent(NoEditable.class)) {
                    isReadonly = true;
                } else if (field.isAnnotationPresent(Compute.class)) {
                    Compute computeAnno = field.getAnnotation(Compute.class);
                    if (!computeAnno.editable()) {
                        isReadonly = true;
                    }
                }
                
                if (isReadonly) {
                    text.setReadonly(true);
                    text.setStyle("background-color", "rgba(48, 54, 61, 0.5)"); // Subtle dark contrast
                    text.setStyle("color", "var(--jettra-text, #ffffff)"); // Clear text
                    text.setStyle("cursor", "not-allowed");
                    text.setStyle("opacity", "0.9");
                    text.setStyle("border", "1px solid rgba(255,255,255,0.1)");
                }
                
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

    private String getIdFieldName() {
        try {
            modelClass.getDeclaredField("code");
            return "code";
        } catch (Exception e) {
            try {
                modelClass.getDeclaredField("id");
                return "id";
            } catch (Exception e2) {}
        }
        return modelClass.getDeclaredFields()[0].getName();
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
            Field field = fields[i];
            field.setAccessible(true);
            Object val = field.get(item);
            
            String valStr = "";
            if (val != null) {
                if (field.isAnnotationPresent(ViewSelectOne.class)) {
                    valStr = getIdValueForSource(val);
                } else if (field.isAnnotationPresent(ViewSelectMany.class)) {
                    if (val instanceof List) {
                        List<?> list = (List<?>) val;
                        valStr = list.stream()
                                .map(this::getIdValueForSource)
                                .collect(Collectors.joining(","));
                    } else {
                        valStr = val.toString();
                    }
                } else {
                    valStr = val.toString();
                }
            }
            
            valStr = valStr.replace("'", "\\'").replace("\"", "\\\"");
            sb.append("'").append(field.getName()).append("': '").append(valStr).append("'");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private void injectScripts(String uniqueId) {
        StringBuilder script = new StringBuilder();
        
        script.append("function saveInlineEdit_").append(uniqueId).append("(id, fieldName, value) {\n")
              .append("  console.log('saveInlineEdit_', id, fieldName, value);\n")
              .append("  const params = new URLSearchParams();\n")
              .append("  params.append('action', 'save');\n")
              .append("  params.append('").append(getIdFieldName()).append("', id);\n");

        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Hidden.class)) {
                continue;
            }
            script.append("  const el_").append(field.getName()).append(" = document.getElementById('table_").append(field.getName()).append("_' + id);\n")
                  .append("  if (el_").append(field.getName()).append(") {\n")
                  .append("    params.append('").append(field.getName()).append("', el_").append(field.getName()).append(".value);\n")
                  .append("  }\n");
        }

        // Highlight Row
        script.append("  const rowEl = document.getElementById('row_' + id + '_").append(uniqueId).append("');\n")
              .append("  if (rowEl) {\n")
              .append("    rowEl.style.transition = 'background-color 0.3s';\n")
              .append("    rowEl.style.backgroundColor = 'rgba(0, 255, 255, 0.1)';\n")
              .append("  }\n");

        script.append("  fetch(window.location.pathname + window.location.search, {\n")
              .append("    method: 'POST',\n")
              .append("    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },\n")
              .append("    body: params.toString()\n")
              .append("  }).then(r => {\n")
              .append("    if(r.ok) {\n")
              .append("      window.location.reload();\n")
              .append("    } else {\n")
              .append("      if (rowEl) rowEl.style.backgroundColor = '';\n")
              .append("      showToast_").append(uniqueId).append("('Error saving change', 'error');\n")
              .append("    }\n")
              .append("  }).catch(err => {\n")
              .append("    if (rowEl) rowEl.style.backgroundColor = '';\n")
              .append("    showToast_").append(uniqueId).append("('Network error saving change', 'error');\n")
              .append("  });\n")
              .append("}\n\n");

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

        script.append("  const formEl = document.getElementById('crudForm_").append(uniqueId).append("');\n");
        script.append("  if(action === 'delete') {\n")
              .append("    if(formEl) formEl.noValidate = true;\n")
              .append("    if(deleteMsg) deleteMsg.style.display = 'block';\n")
              .append("    if(submitBtn) {\n")
              .append("       submitBtn.innerText = '").append(getLabel("btn.confirm.delete", "Eliminar")).append("';\n")
              .append("       submitBtn.style.backgroundColor = '#da3633';\n")
              .append("    }\n");
        for (Field field : modelClass.getDeclaredFields()) {
            script.append("    if(group_").append(field.getName()).append(") group_").append(field.getName()).append(".style.display = 'none';\n");
        }
        script.append("  } else {\n")
              .append("    if(formEl) formEl.noValidate = false;\n")
              .append("    if(deleteMsg) deleteMsg.style.display = 'none';\n")
              .append("    if(submitBtn) {\n")
              .append("       submitBtn.innerText = '").append(getLabel("btn.save", "Guardar")).append("';\n")
              .append("       submitBtn.style.backgroundColor = '#238636';\n")
              .append("    }\n");
        for (Field field : modelClass.getDeclaredFields()) {
            script.append("    if(group_").append(field.getName()).append(") group_").append(field.getName()).append(".style.display = 'block';\n");
        }
        script.append("  }\n")
              .append("}\n\n");

        // ViewDataTable JavaScript support
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ViewDataTable.class)) {
                ViewDataTable vdt = field.getAnnotation(ViewDataTable.class);
                Class<?> itemClass = null;
                if (field.getGenericType() instanceof java.lang.reflect.ParameterizedType) {
                    itemClass = (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                }
                if (itemClass != null) {
                    script.append("window.productPrices_").append(uniqueId).append(" = {};\n");
                    script.append("window.productOptions_").append(uniqueId).append(" = [];\n");
                    for (Field itemF : itemClass.getDeclaredFields()) {
                        if (itemF.isAnnotationPresent(ViewSelectOne.class)) {
                            ViewSelectOne vso = itemF.getAnnotation(ViewSelectOne.class);
                            Class<?> sourceClass = null;
                            String pkg = modelClass.getPackageName();
                            List<String> attempts = Arrays.asList(vso.source(), pkg + "." + vso.source(), pkg.replace(".model", ".repository") + "." + vso.source(), pkg.replace(".model", ".services") + "." + vso.source(), pkg.replace(".model", ".controller") + "." + vso.source());
                            for (String att : attempts) {
                                try { sourceClass = Class.forName(att, true, modelClass.getClassLoader()); break; } catch (Exception e) {}
                            }
                            if (sourceClass != null) {
                                try {
                                    Method m = (vso.filter() != null && !vso.filter().isEmpty()) ? sourceClass.getMethod(vso.method(), String.class) : sourceClass.getMethod(vso.method());
                                    Object res = (vso.filter() != null && !vso.filter().isEmpty()) ? m.invoke(null, vso.filter()) : m.invoke(null);
                                    if (res instanceof List) {
                                        for (Object obj : (List<?>) res) {
                                            String idVal = getIdValueForSource(obj);
                                            String lblVal = resolveLabel(obj, vso.label());
                                            String priceVal = resolveObjectField(obj, "precio");
                                            if (priceVal.isEmpty()) priceVal = "0";
                                            script.append("window.productPrices_").append(uniqueId).append("['").append(idVal).append("'] = ").append(priceVal).append(";\n");
                                            script.append("window.productOptions_").append(uniqueId).append(".push({val:'").append(idVal).append("', lbl:'").append(lblVal.replace("'", "\\'")).append("'});\n");
                                        }
                                    }
                                } catch(Exception ex) {}
                            }
                        }
                    }
                }
            }
        }

        script.append("function updateRowCalculation_").append(uniqueId).append("(rowEl) {\n")
              .append("  const prodSelect = rowEl.querySelector('[data-col=\"productoId\"]');\n")
              .append("  const precioInput = rowEl.querySelector('[data-col=\"precio\"]');\n")
              .append("  const cantInput = rowEl.querySelector('[data-col=\"cantidad\"]');\n")
              .append("  const totalInput = rowEl.querySelector('[data-col=\"total\"]');\n")
              .append("  if (prodSelect && precioInput && window['productPrices_").append(uniqueId).append("']) {\n")
              .append("    const prodId = prodSelect.value;\n")
              .append("    if (window['productPrices_").append(uniqueId).append("'][prodId] !== undefined && rowEl.dataset.prodChanged === 'true') {\n")
              .append("      precioInput.value = window['productPrices_").append(uniqueId).append("'][prodId];\n")
              .append("      rowEl.dataset.prodChanged = 'false';\n")
              .append("    }\n")
              .append("  }\n")
              .append("  if (precioInput && cantInput && totalInput) {\n")
              .append("    const p = parseFloat(precioInput.value) || 0;\n")
              .append("    const c = parseInt(cantInput.value) || 0;\n")
              .append("    const t = p * c;\n")
              .append("    totalInput.value = t.toFixed(2);\n")
              .append("  }\n")
              .append("  calculateGrandTotal_").append(uniqueId).append("();\n")
              .append("}\n\n");

        script.append("function calculateGrandTotal_").append(uniqueId).append("() {\n")
              .append("  const subTable = document.querySelector('[id^=\"table_lineaFacturaModel_\"]');\n")
              .append("  if (!subTable) return;\n")
              .append("  let sum = 0;\n")
              .append("  subTable.querySelectorAll('tbody tr').forEach(tr => {\n")
              .append("    const totInp = tr.querySelector('[data-col=\"total\"]');\n")
              .append("    if (totInp) sum += parseFloat(totInp.value) || 0;\n")
              .append("  });\n")
              .append("  const subtotalInp = document.getElementById('input_subtotal_").append(uniqueId).append("');\n")
              .append("  const totalInp = document.getElementById('input_total_").append(uniqueId).append("');\n")
              .append("  const descInp = document.getElementById('input_descuento_").append(uniqueId).append("');\n")
              .append("  const impInp = document.getElementById('input_porcentajeImpuesto_").append(uniqueId).append("');\n")
              .append("  const subtotal = sum;\n")
              .append("  const desc = descInp ? (parseFloat(descInp.value) || 0) : 0;\n")
              .append("  const impPorc = impInp ? (parseFloat(impInp.value) || 0) : 0;\n")
              .append("  const impVal = (subtotal - desc) * (impPorc / 100.0);\n")
              .append("  const total = subtotal - desc + impVal;\n")
              .append("  if (subtotalInp) subtotalInp.value = subtotal.toFixed(2);\n")
              .append("  if (totalInp) totalInp.value = total.toFixed(2);\n")
              .append("}\n\n");

        script.append("function addNestedRow_").append(uniqueId).append("(fieldName) {\n")
              .append("  const table = document.getElementById('table_' + fieldName + '_").append(uniqueId).append("');\n")
              .append("  if(!table) return;\n")
              .append("  const tbody = table.querySelector('tbody') || table;\n")
              .append("  const tr = document.createElement('tr');\n")
              .append("  tr.dataset.prodChanged = 'true';\n")
              .append("  if (fieldName === 'lineaFacturaModel') {\n")
              .append("    const td1 = document.createElement('td'); const sel = document.createElement('select'); sel.setAttribute('data-col', 'productoId');\n")
              .append("    sel.style.width = '100%'; sel.style.background = 'rgba(0,0,0,0.3)'; sel.style.color = 'white'; sel.style.border = '1px solid var(--jettra-border)';\n")
              .append("    if (window['productOptions_").append(uniqueId).append("']) {\n")
              .append("      window['productOptions_").append(uniqueId).append("'].forEach(opt => {\n")
              .append("        const o = document.createElement('option'); o.value = opt.val; o.innerText = opt.lbl; sel.appendChild(o);\n")
              .append("      });\n")
              .append("    }\n")
              .append("    sel.onchange = function() { tr.dataset.prodChanged = 'true'; updateRowCalculation_").append(uniqueId).append("(tr); };\n")
              .append("    td1.appendChild(sel); tr.appendChild(td1);\n")
              .append("    const td2 = document.createElement('td'); const inpP = document.createElement('input'); inpP.type = 'text'; inpP.setAttribute('data-col', 'precio');\n")
              .append("    inpP.style.width = '100%'; inpP.style.background = 'rgba(0,0,0,0.3)'; inpP.style.color = 'white'; inpP.style.border = '1px solid var(--jettra-border)'; inpP.style.padding = '4px';\n")
              .append("    inpP.oninput = function() { updateRowCalculation_").append(uniqueId).append("(tr); };\n")
              .append("    td2.appendChild(inpP); tr.appendChild(td2);\n")
              .append("    const td3 = document.createElement('td'); const inpC = document.createElement('input'); inpC.type = 'text'; inpC.setAttribute('data-col', 'cantidad');\n")
              .append("    inpC.style.width = '100%'; inpC.style.background = 'rgba(0,0,0,0.3)'; inpC.style.color = 'white'; inpC.style.border = '1px solid var(--jettra-border)'; inpC.style.padding = '4px';\n")
              .append("    inpC.oninput = function() { updateRowCalculation_").append(uniqueId).append("(tr); };\n")
              .append("    td3.appendChild(inpC); tr.appendChild(td3);\n")
              .append("    const td4 = document.createElement('td'); const inpT = document.createElement('input'); inpT.type = 'text'; inpT.setAttribute('data-col', 'total'); inpT.readOnly = true;\n")
              .append("    inpT.style.width = '100%'; inpT.style.background = 'transparent'; inpT.style.color = 'white'; inpT.style.border = 'none'; inpT.style.padding = '4px';\n")
              .append("    td4.appendChild(inpT); tr.appendChild(td4);\n")
              .append("  }\n")
              .append("  const tdAction = document.createElement('td'); const delBtn = document.createElement('button'); delBtn.type = 'button'; delBtn.innerText = '🗑️';\n")
              .append("  delBtn.style.background = 'transparent'; delBtn.style.border = 'none'; delBtn.style.padding = '4px'; delBtn.style.color = '#f85149';\n")
              .append("  delBtn.onclick = function() { tr.remove(); calculateGrandTotal_").append(uniqueId).append("(); };\n")
              .append("  tdAction.appendChild(delBtn); tr.appendChild(tdAction);\n")
              .append("  tbody.appendChild(tr);\n")
              .append("  updateRowCalculation_").append(uniqueId).append("(tr);\n")
              .append("}\n");

        // Helper for toast notifications
        script.append("function showToast_").append(uniqueId).append("(msg, type) {\n")
              .append("  let toast = document.getElementById('j-toast-").append(uniqueId).append("');\n")
              .append("  if(!toast) {\n")
              .append("    toast = document.createElement('div');\n")
              .append("    toast.id = 'j-toast-").append(uniqueId).append("';\n")
              .append("    toast.style = 'position:fixed;top:20px;right:20px;z-index:99999;padding:12px 20px;border-radius:8px;color:white;font-weight:bold;transition:all 0.3s;display:none;';\n")
              .append("    document.body.appendChild(toast);\n")
              .append("  }\n")
              .append("  toast.innerText = msg;\n")
              .append("  toast.style.backgroundColor = type === 'error' ? 'rgba(239, 68, 68, 0.9)' : 'rgba(16, 185, 129, 0.9)';\n")
              .append("  toast.style.display = 'block';\n")
              .append("  toast.style.opacity = '1';\n")
              .append("  setTimeout(() => { toast.style.opacity = '0'; setTimeout(() => toast.style.display='none', 300); }, 3000);\n")
              .append("}\n");

        // Add Rules validation logic
        script.append("function validateRules_").append(uniqueId).append("(data) {\n")
              .append("  let errors = [];\n");

        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(io.jettra.rules.annotations.Rules.class)) {
                io.jettra.rules.annotations.Rules rule = field.getAnnotation(io.jettra.rules.annotations.Rules.class);
                String fieldName = field.getName();
                String apply = rule.apply();
                String than = rule.than();
                String message = rule.message();

                script.append("  {\n")
                      .append("    const val = parseFloat(document.getElementById('input_").append(fieldName).append("_").append(uniqueId).append("').value) || 0;\n");
                
                if (than.matches("-?\\d+(\\.\\d+)?")) {
                    script.append("    const thanVal = ").append(than).append(";\n");
                } else {
                    script.append("    const thanVal = parseFloat(document.getElementById('input_").append(than).append("_").append(uniqueId).append("').value) || 0;\n");
                }

                if ("greater".equals(apply)) {
                    script.append("    if (!(val > thanVal)) errors.push('").append(message).append("');\n");
                } else if ("lessorequals".equals(apply)) {
                    script.append("    if (!(val <= thanVal)) errors.push('").append(message).append("');\n");
                } else if ("less".equals(apply)) {
                    script.append("    if (!(val < thanVal)) errors.push('").append(message).append("');\n");
                } else if ("greaterorequals".equals(apply)) {
                    script.append("    if (!(val >= thanVal)) errors.push('").append(message).append("');\n");
                } else if ("equals".equals(apply)) {
                    script.append("    if (val !== thanVal) errors.push('").append(message).append("');\n");
                }
                
                script.append("  }\n");
            }
        }
        script.append("  return errors;\n")
              .append("}\n");

        script.append("document.getElementById('crudForm_").append(uniqueId).append("').onsubmit = function(e) {\n")
              .append("  e.preventDefault();\n")
              .append("  if(document.getElementById('crudAction_").append(uniqueId).append("').value === 'delete') {\n")
              .append("    submitFormAjax_").append(uniqueId).append("(this);\n")
              .append("    return false;\n")
              .append("  }\n")
              .append("  const errs = validateRules_").append(uniqueId).append("();\n")
              .append("  if(errs.length > 0) {\n")
              .append("    showToast_").append(uniqueId).append("('Error: ' + errs[0], 'error');\n")
              .append("    return false;\n")
              .append("  }\n")
              .append("  const subTable = document.getElementById('table_lineaFacturaModel_' + '").append(uniqueId).append("');\n")
              .append("  if (subTable) {\n")
              .append("    const rows = [];\n")
              .append("    subTable.querySelectorAll('tbody tr').forEach(tr => {\n")
              .append("      const rowObj = {};\n")
              .append("      tr.querySelectorAll('[data-col]').forEach(input => {\n")
              .append("        rowObj[input.getAttribute('data-col')] = input.value;\n")
              .append("      });\n")
              .append("      if (Object.keys(rowObj).length > 0) rows.push(rowObj);\n")
              .append("    });\n")
              .append("    const hidden = document.getElementById('input_lineaFacturaModel_' + '").append(uniqueId).append("');\n")
              .append("    if (hidden) hidden.value = JSON.stringify(rows);\n")
              .append("  }\n")
              .append("  submitFormAjax_").append(uniqueId).append("(this);\n")
              .append("  return false;\n")
              .append("};\n")
              .append("function submitFormAjax_").append(uniqueId).append("(form) {\n")
              .append("  const formData = new FormData(form);\n")
              .append("  const urlParams = new URLSearchParams();\n")
              .append("  for (const pair of formData.entries()) { urlParams.append(pair[0], pair[1]); }\n")
              .append("  fetch(window.location.pathname + window.location.search, {\n")
              .append("    method: 'POST',\n")
              .append("    headers: { 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'XMLHttpRequest' },\n")
              .append("    body: urlParams.toString()\n")
              .append("  }).then(r => {\n")
              .append("    if (r.ok) {\n")
              .append("      showToast_").append(uniqueId).append("('Operación exitosa', 'success');\n")
              .append("      setTimeout(() => window.location.reload(), 1500);\n")
              .append("    } else {\n")
              .append("      r.json().then(data => showToast_").append(uniqueId).append("('Error: ' + (data.error || 'Unknown'), 'error'))\n")
              .append("       .catch(() => showToast_").append(uniqueId).append("('Error: ' + r.statusText, 'error'));\n")
              .append("    }\n")
              .append("  }).catch(err => showToast_").append(uniqueId).append("('Error de red', 'error'));\n")
              .append("}\n");

        // Real-time notification support
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(io.jettra.rules.annotations.Rules.class)) {
                script.append("document.getElementById('input_").append(field.getName()).append("_").append(uniqueId).append("').addEventListener('input', () => {\n")
                      .append("  const errs = validateRules_").append(uniqueId).append("();\n")
                      .append("  const input = document.getElementById('input_").append(field.getName()).append("_").append(uniqueId).append("');\n")
                      .append("  const myErrs = errs.filter(e => e.includes('").append(field.getName()).append("') || e.includes('").append(field.getAnnotation(io.jettra.rules.annotations.Rules.class).message()).append("'));\n")
                      .append("  if(myErrs.length > 0) {\n")
                      .append("    input.style.borderColor = '#ef4444';\n")
                      .append("    input.style.boxShadow = '0 0 8px rgba(239, 68, 68, 0.4)';\n")
                      .append("    showToast_").append(uniqueId).append("(myErrs[0], 'error');\n")
                      .append("  } else {\n")
                      .append("    input.style.borderColor = '';\n")
                      .append("    input.style.boxShadow = '';\n")
                      .append("  }\n")
                      .append("});\n");
            }
        }

        // Add Compute logic
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Compute.class)) {
                Compute computeAnno = field.getAnnotation(Compute.class);
                String targetId = "input_" + field.getName() + "_" + uniqueId;
                String[] sourceFields = computeAnno.fields();
                
                if (sourceFields != null && sourceFields.length > 0) {
                    script.append("function compute_").append(field.getName()).append("_").append(uniqueId).append("() {\n")
                          .append("  const target = document.getElementById('").append(targetId).append("');\n");
                    
                    for (String source : sourceFields) {
                        script.append("  const el_").append(source).append(" = document.getElementById('input_").append(source).append("_").append(uniqueId).append("');\n")
                              .append("  const val_").append(source).append(" = el_").append(source).append(" ? (parseFloat(el_").append(source).append(".value) || 0) : 0;\n");
                    }
                    
                    script.append("  let result = 0;\n");
                    
                    OperationType op = computeAnno.operation();
                    if (op == OperationType.SUM) {
                        for (String source : sourceFields) script.append("  result += val_").append(source).append(";\n");
                    } else if (op == OperationType.SUBTRACTION) {
                        script.append("  result = val_").append(sourceFields[0]).append(";\n");
                        for (int i = 1; i < sourceFields.length; i++) script.append("  result -= val_").append(sourceFields[i]).append(";\n");
                    } else if (op == OperationType.MULT) {
                        script.append("  result = 1;\n");
                        for (String source : sourceFields) script.append("  result *= val_").append(source).append(";\n");
                    } else if (op == OperationType.DIV) {
                        script.append("  result = val_").append(sourceFields[0]).append(";\n");
                        for (int i = 1; i < sourceFields.length; i++) script.append("  if(val_").append(sourceFields[i]).append(" !== 0) result /= val_").append(sourceFields[i]).append(";\n");
                    } else if (op == OperationType.PERCENTAGE) {
                        if (sourceFields.length >= 2) script.append("  result = (val_").append(sourceFields[0]).append(" * val_").append(sourceFields[1]).append(") / 100;\n");
                    }
                    
                    script.append("  if(target) target.value = result.toFixed(2);\n")
                          .append("  // Trigger rules validation after compute\n")
                          .append("  const inputEvent = new Event('input', { bubbles: true });\n")
                          .append("  if(target) target.dispatchEvent(inputEvent);\n")
                          .append("}\n");
                    
                    for (String source : sourceFields) {
                        script.append("document.getElementById('input_").append(source).append("_").append(uniqueId).append("').addEventListener('input', compute_").append(field.getName()).append("_").append(uniqueId).append(");\n");
                    }
                }
            }
        }

        List<?> items = null;
        try {
            if (handler != null) {
                items = (List<?>) handler.findAll();
            } else {
                Method findAll = repositoryClass.getMethod("findAll");
                items = (List<?>) findAll.invoke(null);
            }
        } catch (Exception e) {
            System.err.println("[CrudView] Error fetching items for script injection: " + e.getMessage());
        }

        if (this.editable && items != null) {
            for (Object item : items) {
                String idValue;
                try {
                    idValue = (handler != null) ? ((CrudHandler<Object>)handler).getIdValue(item) : getIdValue(item);
                } catch (Exception e) {
                    continue;
                }
                
                // Add Compute logic for each row
                for (Field field : modelClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Compute.class)) {
                        Compute computeAnno = field.getAnnotation(Compute.class);
                        String targetId = "table_" + field.getName() + "_" + idValue;
                        String[] sourceFields = computeAnno.fields();
                        
                        if (sourceFields != null && sourceFields.length > 0) {
                            script.append("function compute_row_").append(field.getName()).append("_").append(idValue).append("_").append(uniqueId).append("() {\n")
                                  .append("  const target = document.getElementById('").append(targetId).append("');\n");
                            
                            for (String source : sourceFields) {
                                script.append("  const el_").append(source).append(" = document.getElementById('table_").append(source).append("_").append(idValue).append("');\n")
                                      .append("  const val_").append(source).append(" = el_").append(source).append(" ? (parseFloat(el_").append(source).append(".value) || 0) : 0;\n");
                            }
                            
                            script.append("  let result = 0;\n");
                            
                            OperationType op = computeAnno.operation();
                            if (op == OperationType.SUM) {
                                for (String source : sourceFields) script.append("  result += val_").append(source).append(";\n");
                            } else if (op == OperationType.SUBTRACTION) {
                                script.append("  result = val_").append(sourceFields[0]).append(";\n");
                                for (int i = 1; i < sourceFields.length; i++) script.append("  result -= val_").append(sourceFields[i]).append(";\n");
                            } else if (op == OperationType.MULT) {
                                script.append("  result = 1;\n");
                                for (String source : sourceFields) script.append("  result *= val_").append(source).append(";\n");
                            } else if (op == OperationType.DIV) {
                                script.append("  result = val_").append(sourceFields[0]).append(";\n");
                                for (int i = 1; i < sourceFields.length; i++) script.append("  if(val_").append(sourceFields[i]).append(" !== 0) result /= val_").append(sourceFields[i]).append(";\n");
                            } else if (op == OperationType.PERCENTAGE) {
                                if (sourceFields.length >= 2) script.append("  result = (val_").append(sourceFields[0]).append(" * val_").append(sourceFields[1]).append(") / 100;\n");
                            }
                            
                            script.append("  if(target) target.value = result.toFixed(2);\n")
                                  .append("  calculateGrandTotal_").append(uniqueId).append("();\n")
                                  .append("}\n");
                            
                            for (String source : sourceFields) {
                                script.append("const el_src_").append(source).append("_").append(idValue).append(" = document.getElementById('table_").append(source).append("_").append(idValue).append("');\n")
                                      .append("if(el_src_").append(source).append("_").append(idValue).append(") {\n")
                                      .append("  el_src_").append(source).append("_").append(idValue).append(".addEventListener('input', compute_row_").append(field.getName()).append("_").append(idValue).append("_").append(uniqueId).append(");\n")
                                      .append("}\n");
                            }
                        }
                    }
                }
            }
        }

        // Category to Price trigger for ArticuloModel (Modal Form)
        if (modelClass.getSimpleName().equalsIgnoreCase("ArticuloModel")) {
            script.append("function updateModalCategory_").append(uniqueId).append("() {\n")
                  .append("  const catEl = document.getElementById('input_categoria_").append(uniqueId).append("');\n")
                  .append("  const priceEl = document.getElementById('input_precio_").append(uniqueId).append("');\n")
                  .append("  if (catEl && priceEl) {\n")
                  .append("    const cat = catEl.value;\n")
                  .append("    let newPrice = 0;\n")
                  .append("    if (cat === 'electronic') newPrice = 10000.00;\n")
                  .append("    else if (cat === 'accessory') newPrice = 456.00;\n")
                  .append("    else if (cat === 'office') newPrice = 231.00;\n")
                  .append("    priceEl.value = newPrice.toFixed(2);\n")
                  .append("    const inputEvent = new Event('input', { bubbles: true });\n")
                  .append("    priceEl.dispatchEvent(inputEvent);\n")
                  .append("  }\n")
                  .append("}\n")
                  .append("const modalCatEl = document.getElementById('input_categoria_").append(uniqueId).append("');\n")
                  .append("if(modalCatEl) {\n")
                  .append("  modalCatEl.addEventListener('change', updateModalCategory_").append(uniqueId).append(");\n")
                  .append("}\n");
        }

        // Category to Price trigger for ArticuloModel (Table Rows)
        if (this.editable && modelClass.getSimpleName().equalsIgnoreCase("ArticuloModel") && items != null) {
            for (Object item : items) {
                String idValue;
                try {
                    idValue = (handler != null) ? ((CrudHandler<Object>)handler).getIdValue(item) : getIdValue(item);
                } catch (Exception e) {
                    continue;
                }
                script.append("function updateRowCategory_").append(idValue).append("_").append(uniqueId).append("() {\n")
                      .append("  const catEl = document.getElementById('table_categoria_").append(idValue).append("');\n")
                      .append("  const priceEl = document.getElementById('table_precio_").append(idValue).append("');\n")
                      .append("  if (catEl && priceEl) {\n")
                      .append("    const cat = catEl.value;\n")
                      .append("    let newPrice = 0;\n")
                      .append("    if (cat === 'electronic') newPrice = 10000.00;\n")
                      .append("    else if (cat === 'accessory') newPrice = 456.00;\n")
                      .append("    else if (cat === 'office') newPrice = 231.00;\n")
                      .append("    priceEl.value = newPrice.toFixed(2);\n")
                      .append("    const inputEvent = new Event('input', { bubbles: true });\n")
                      .append("    priceEl.dispatchEvent(inputEvent);\n")
                      .append("  }\n")
                      .append("}\n")
                      .append("const rowCatEl_").append(idValue).append(" = document.getElementById('table_categoria_").append(idValue).append("');\n")
                      .append("if(rowCatEl_").append(idValue).append(") {\n")
                      .append("  rowCatEl_").append(idValue).append(".addEventListener('change', updateRowCategory_").append(idValue).append("_").append(uniqueId).append(");\n")
                      .append("}\n");
            }
        }

        if (this.editable) {
            Field computeField = null;
            for (Field field : modelClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Compute.class)) {
                    computeField = field;
                    break;
                }
            }
            if (computeField != null) {
                script.append("function calculateGrandTotal_").append(uniqueId).append("() {\n")
                      .append("  let grandTotal = 0;\n")
                      .append("  const rows = document.querySelectorAll('[id^=\"table_").append(computeField.getName()).append("_\"]');\n")
                      .append("  rows.forEach(row => {\n")
                      .append("    grandTotal += parseFloat(row.value) || parseFloat(row.innerText) || 0;\n")
                      .append("  });\n")
                      .append("  const gtEl = document.getElementById('gran_total_").append(uniqueId).append("');\n")
                      .append("  if (gtEl) gtEl.innerText = grandTotal.toLocaleString('en-US', {minimumFractionDigits: 2});\n")
                      .append("}\n")
                      .append("document.addEventListener('DOMContentLoaded', calculateGrandTotal_").append(uniqueId).append(");\n")
                      .append("setTimeout(calculateGrandTotal_").append(uniqueId).append(", 100);\n");
            }
        }

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
        if (item == null) return "";
        if (item instanceof String) return (String) item;
        if (item instanceof Number) return item.toString();
        
        Class<?> clazz = item.getClass();
        try {
            // Priority 1: Common names
            List<String> commonNames = Arrays.asList("code", "id", "id" + clazz.getSimpleName());
            for (Field f : clazz.getDeclaredFields()) {
                if (commonNames.stream().anyMatch(name -> name.equalsIgnoreCase(f.getName()))) {
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val != null) return val.toString();
                }
            }
            
            // Priority 2: Getters
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase("getCode") || m.getName().equalsIgnoreCase("getId") || m.getName().equalsIgnoreCase("getid" + clazz.getSimpleName())) {
                    if (m.getParameterCount() == 0) {
                        Object val = m.invoke(item);
                        if (val != null) return val.toString();
                    }
                }
            }

            // Priority 3: Any field containing 'id' or 'code'
            for (Field f : clazz.getDeclaredFields()) {
                if (f.getName().toLowerCase().contains("id") || f.getName().toLowerCase().contains("code")) {
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val != null) return val.toString();
                }
            }
        } catch (Exception e) {
            System.err.println("[CrudView] Error resolving ID for " + clazz.getSimpleName() + ": " + e.getMessage());
        }
        return item.toString(); // Fallback to toString if nothing else found
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
