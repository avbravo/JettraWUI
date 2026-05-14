# @TableColumnField

The `@TableColumnField` annotation allows you to specify which field of a related model should be displayed in a `DataTable` or a Report, instead of using the default `toString()` method.

## Usage

When you have a relationship (like `@ViewSelectOne` or `@ViewSelectMany`), the system by default calls `toString()` on the related objects to display them in the table. By adding `@TableColumnField(field="targetField")`, you can choose a specific attribute of the related model to show.

### Example

In this example, `SubGrupoModel` has a list of `DeporteModel`. We want to show the `name` of each sport in the table.

```java
@JettraViewModel
public class SubGrupoModel {
    @NotNull
    @PropertiesLabel(value = "lbl.id", label = "ID")
    private String id;
    
    @ViewSelectMany(label = "deporte", source = "DeporteRepository", method = "findAll")
    @PropertiesLabel(value = "lbl.deportes", label = "Deportes")
    @TableColumnField(field = "name")
    private List<DeporteModel> deportesModel;
    
    // ...
}
```

### Attributes

| Attribute | Description |
|-----------|-------------|
| `field`   | The name of the field in the related model to be displayed. |

## Benefits

1.  **Cleaner UI**: Show only the relevant information (like a name or description) instead of a complex object string.
2.  **Consistency**: Ensures that the same field is used in both the web view and the generated reports (PDF, Excel, CSV, Word).
3.  **Flexibility**: Allows choosing different fields for different relationships even if they point to the same model.
