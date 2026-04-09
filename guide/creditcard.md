# CreditCard Component

The `CreditCard` component provides a fully styled, interactive form for accepting credit card information, complete with a glassmorphic 3D real-time visual preview of the card details.

## Basic Usage

```java
import io.jettra.wui.components.CreditCard;

public class MyPage extends DashboardBasePage {
    @Override
    protected void initCenter(Center center, String username) {
        // Simple usage
        CreditCard cc = new CreditCard("my-cc");
        cc.setSubmitText("Pay $99.00");
        
        center.add(cc);
    }
}
```

## Form Submission

You can set the `formAction` property to make the component behave as a standard HTML form that posts data to your server.

```java
CreditCard cc = new CreditCard("payment-form")
    .setFormAction("/api/process-payment")
    .setSubmitText("Complete Purchase");
```

When submitted, the following field names will be sent:
- `cardNumber`
- `cardName`
- `cardExpiry`
- `cardCvc`

## Customizing Buttons

If you want to add custom buttons instead of the default submit button, you can add them as children:

```java
CreditCard cc = new CreditCard("payment-form");

Button payBtn = new Button("Pay Now");
payBtn.addClass("j-btn-primary");
payBtn.setProperty("onclick", "processPayment()");

Button cancelBtn = new Button("Cancel");
cancelBtn.addClass("j-btn");

cc.add(payBtn).add(cancelBtn);
```
