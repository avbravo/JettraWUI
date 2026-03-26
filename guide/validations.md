# JettraValidations Guide

The Jettra validation system allows you to define constraints on your models using annotations, which are then automatically applied to UI components.

## Available Annotations

The following annotations are available in the `io.jettra.wui.validations` package:

### String Validations
- `@NotNull`: Field cannot be null.
- `@NotEmpty`: Field cannot be empty (length > 0).
- `@NotBlank`: Field must contain at least one non-whitespace character.
- `@Size(min=x, max=y)`: String length must be between min and max.
- `@Email`: Must be a valid email format.
- `@Pattern(regexp="...")`: Must match the specified regular expression.

### Numeric Validations
- `@Min(value)`: Minimum integer value.
- `@Max(value)`: Maximum integer value.
- `@DecimalMin(value)`: Minimum decimal value.
- `@DecimalMax(value)`: Maximum decimal value.
- `@Digits(integer=x, fraction=y)`: Limit number of integer and fractional digits.
- `@Positive`: Must be a positive number.
- `@PositiveOrZero`: Must be 0 or positive.
- `@Negative`: Must be a negative number.
- `@NegativeOrZero`: Must be 0 or negative.

### Boolean Validations
- `@AssertTrue`: Value must be true.
- `@AssertFalse`: Value must be false.

### Date/Time Validations
- `@Future`: Date must be in the future.
- `@FutureOrPresent`: Date must be today or in the future.
- `@Past`: Date must be in the past.
- `@PastOrPresent`: Date must be today or in the past.

## Usage Example

### 1. Define Annotations in your Model

```java
import io.jettra.wui.validations.*;

public class PersonaModel {
    @NotNull
    @Size(min = 2, max = 50)
    private String nombre;

    @Email
    private String email;

    @Min(18)
    private int edad;
}
```

### 2. Apply Validations in your Page

In your `Page` class, use the `JettraValidations.apply` method to connect the model constraints to the UI components:

```java
import io.jettra.wui.validations.JettraValidations;

// ... inside your page initialization ...
TextBox inputNombre = new TextBox("text", "nombre");
JettraValidations.apply(inputNombre, PersonaModel.class, "nombre");
```

This will automatically set HTML5 attributes like `required`, `minlength`, `maxlength`, `min`, `max`, and `type="email"` on the generated component.
This will automatically set HTML5 attributes like `required`, `minlength`, `maxlength`, `min`, `max`, and `type="email"` on the generated component.
