package io.jettra.wui.complex;

import io.jettra.wui.components.*;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.CrudHandler;
import io.jettra.wui.core.annotations.PropertiesLabel;
import io.jettra.wui.validations.JettraValidations;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CrudView extends UIComponent {

    private Class<?> modelClass;
    private Class<?> repositoryClass;
    private CrudHandler<?> handler;
    private Properties messages;
    private String title;
    private String subtitle;
    private String modelName;
    private boolean reportEnabled = false;

    private Modal crudModal;
    private Header modalHeader;
    private TextBox modalAction;
    private List<TextBox> inputFields = new ArrayList<>();
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

        init();
    }

    public CrudView setReportEnabled(boolean enabled) {
        this.reportEnabled = enabled;
        // Re-init or add button if already initialized? 
        // Better to set it before init() if called from constructor, 
        // but init() is called in constructor. 
        // I will change the constructor to include it or make init() callable later.
        return this;
    }

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

        String uniqueId = modelName + "_" + System.currentTimeMillis();
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
            Button reportBtn = new Button("📄 " + getLabel("btn.report", "Reporte"))
                    .setId("reportBtn_" + uniqueId)
                    .setBackgroundColor("#007bff")
                    .setOnclick("location.href='?action=report'");
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
                        dataRow.add(new TD(val != null ? val.toString() : ""));
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

        injectScripts(uniqueId);
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
            
            TextBox input = new TextBox("text", field.getName()).setId("input_" + field.getName() + "_" + uniqueId);
            JettraValidations.apply(input, modelClass, field.getName());
            
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
                  .append("    if(data) {\n")
                  .append("      input_").append(field.getName()).append(".value = data['").append(field.getName()).append("'] || '';\n")
                  .append("    } else {\n")
                  .append("      input_").append(field.getName()).append(".value = '';\n")
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
}
