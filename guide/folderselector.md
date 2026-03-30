# FolderSelector Component

The `FolderSelector` component allows users to select an entire directory and manage references to its location and content. It supports 3D aesthetics and automatic integration with the `Tree` component.

## Usage

```java
Tree myTree = new Tree();
myTree.setId("my-explorer-tree");

FolderSelector selector = new FolderSelector("my-fs")
    .setReferenceLocation("/var/data")
    .setReferenceContent("Backup Data")
    .style3D()
    .setConfirmUpload(true, "Confirmar Cambio", "¿Desea cargar los archivos de esta carpeta?")
    .setTree(myTree); // Link to tree for automatic updates
```

## Features
- **Directory Selection**: Uses `webkitdirectory` for folder picking.
- **3D Styles**: `style3D()` applies a cyberpunk/glassmorphism look.
- **Tree Integration**: `.setTree(Tree)` links the selector to a Tree component which is automatically populated with the selected folder's structure after confirmation.
- **3D Confirmation**: Shows a nested visual tree before confirming the selection.
- **AJAX Updates**: Support for `.setUpdate(String ids)` to refresh other components.

## Methods
- `setReferenceLocation(String location)`: Sets the primary location reference.
- `setReferenceContent(String content)`: Sets the content description.
- `style3D()`: Enables 3D styling for the component and dialogs.
- `setConfirmUpload(boolean confirm, String title, String message)`: Enables 3D confirmation dialog with tree view.
- `setTree(Tree tree)`: Associates a Tree component for automatic updates.
- `setUpdate(String ids)`: Specify IDs of other components to refresh after interaction.

