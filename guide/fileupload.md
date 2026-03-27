# FileUpload Component

The `FileUpload` component allows users to select files from their device and provides an integrated upload button.

## Usage

```java
FileUpload upload = new FileUpload("my-upload");
upload.setMultiple(true);
upload.setAccept("image/*");
upload.setDestination("/home/user/uploads");
upload.setFileNamePattern("doc_{timestamp}_{name}");
```

## Methods

- `setMultiple(boolean multiple)`: Allows selecting multiple files.
- `setAccept(String accept)`: Defines the accepted file types.
- `setDestination(String path)`: Specifies the target path on the server/system.
- `setFileNamePattern(String pattern)`: Sets the naming pattern for uploaded files.
- `getUploadButton()`: Returns the `Button` instance for further customization.

## Structure
The component renders as a `Div` containing an `input type="file"` and a `Button`.
