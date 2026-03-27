# FolderSelector Component

The `FolderSelector` component allows users to select an entire directory and manage references to its location and content. It does not automatically upload files but stores metadata references for application logic.

## Usage

```java
FolderSelector selector = new FolderSelector("my-fs")
    .setReferenceLocation("/var/data")
    .setReferenceContent("Backup Data");
```

## Features
- **Directory Selection**: Uses `webkitdirectory` to allow folder picking in modern browsers.
- **Reference Management**: Allows setting and getting the location and content references for application logic.

## Methods
- `setReferenceLocation(String location)`: Sets the primary location reference.
- `setReferenceContent(String content)`: Sets the content description/reference.
