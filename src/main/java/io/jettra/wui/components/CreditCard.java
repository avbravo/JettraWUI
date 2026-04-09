package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * JettraWUI CreditCard Component
 * Generates a form for credit card input with premium styling.
 */
public class CreditCard extends UIComponent {
    
    private String formAction = "";
    private String submitText = "Pay Now";
    // Using default form fields names
    private String nameField = "cardName";
    private String numberField = "cardNumber";
    private String expiryField = "cardExpiry";
    private String cvcField = "cardCvc";

    public CreditCard(String id) {
        super("div");
        setProperty("id", id);
        this.addClass("j-credit-card").addClass("j-component");
        
        this.setStyle("background", "linear-gradient(135deg, rgba(30,40,60,0.9), rgba(10,15,30,0.95))")
            .setStyle("border", "1px solid rgba(0, 255, 255, 0.2)")
            .setStyle("border-radius", "16px")
            .setStyle("padding", "25px")
            .setStyle("width", "100%")
            .setStyle("max-width", "400px")
            .setStyle("box-shadow", "0 10px 30px rgba(0,0,0,0.5), inset 0 1px 2px rgba(255,255,255,0.1)");
    }
    
    public CreditCard setFormAction(String formAction) {
        this.formAction = formAction;
        return this;
    }
    
    public CreditCard setSubmitText(String submitText) {
        this.submitText = submitText;
        return this;
    }

    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        
        // CSS for CreditCard
        html.append("<style>");
        html.append(".cc-group { margin-bottom: 15px; }");
        html.append(".cc-label { display: block; color: var(--jettra-accent); font-size: 11px; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 5px; font-weight: bold; }");
        html.append(".cc-input { width: 100%; background: rgba(0,0,0,0.4); border: 1px solid rgba(255,255,255,0.1); border-radius: 6px; padding: 10px 12px; color: #fff; font-size: 14px; font-family: monospace; outline: none; transition: border-color 0.2s, box-shadow 0.2s; box-sizing: border-box; }");
        html.append(".cc-input:focus { border-color: var(--jettra-accent); box-shadow: 0 0 8px rgba(0, 255, 255, 0.3); }");
        html.append(".cc-row { display: flex; gap: 15px; }");
        html.append(".cc-col { flex: 1; }");
        html.append(".cc-btn { margin-top: 10px; width: 100%; border-radius: 8px; padding: 12px; font-size: 14px; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; box-shadow: 0 4px 15px rgba(0,255,255,0.3); }");
        html.append("</style>");

        String formStart = formAction != null && (!formAction.isEmpty()) ? "<form action=\"" + formAction + "\" method=\"POST\">" : "<div>";
        String formEnd = formAction != null && (!formAction.isEmpty()) ? "</form>" : "</div>";

        html.append(formStart);

        // Visual Card Preview
        html.append("<div style=\"position: relative; margin-bottom: 25px; height: 180px; border-radius: 12px; background: linear-gradient(145deg, #1e293b, #0f172a); border: 1px solid rgba(255,255,255,0.1); box-shadow: 0 15px 35px -5px rgba(0,0,0,0.5); padding: 20px; display: flex; flex-direction: column; justify-content: space-between; overflow: hidden;\">");
        
        // Chip and Logo
        html.append("  <div style=\"display: flex; justify-content: space-between; align-items: flex-start;\">");
        html.append("    <div style=\"width: 45px; height: 35px; background: linear-gradient(135deg, #eab308, #ca8a04); border-radius: 5px; opacity: 0.8; border: 1px solid rgba(255,255,255,0.2);\"></div>");
        html.append("    <svg width=\"40\" height=\"25\" viewBox=\"0 0 100 60\"><circle cx=\"35\" cy=\"30\" r=\"25\" fill=\"rgba(239, 68, 68, 0.8)\"/><circle cx=\"65\" cy=\"30\" r=\"25\" fill=\"rgba(245, 158, 11, 0.8)\"/></svg>");
        html.append("  </div>");
        
        // Card Number
        html.append("  <div id=\"cc-preview-num_").append(properties.get("id")).append("\" style=\"font-family: monospace; font-size: 20px; letter-spacing: 3px; color: #fff; text-shadow: 0 2px 2px rgba(0,0,0,0.5);\">•••• •••• •••• ••••</div>");
        
        // Bottom row
        html.append("  <div style=\"display: flex; justify-content: space-between;\">");
        html.append("    <div style=\"display: flex; flex-direction: column; gap: 2px;\">");
        html.append("       <span style=\"font-size: 8px; color: #94a3b8; text-transform: uppercase;\">Card Holder</span>");
        html.append("       <span id=\"cc-preview-name_").append(properties.get("id")).append("\" style=\"font-family: monospace; font-size: 14px; color: #fff; text-transform: uppercase;\">NAME SURNAME</span>");
        html.append("    </div>");
        html.append("    <div style=\"display: flex; flex-direction: column; gap: 2px;\">");
        html.append("       <span style=\"font-size: 8px; color: #94a3b8; text-transform: uppercase;\">Expires</span>");
        html.append("       <span id=\"cc-preview-exp_").append(properties.get("id")).append("\" style=\"font-family: monospace; font-size: 14px; color: #fff;\">MM/YY</span>");
        html.append("    </div>");
        html.append("  </div>");
        
        // Decorational shine
        html.append("  <div style=\"position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: linear-gradient(to right, rgba(255,255,255,0) 0%, rgba(255,255,255,0.05) 50%, rgba(255,255,255,0) 100%); transform: rotate(30deg); pointer-events: none;\"></div>");
        html.append("</div>");

        // Input Setup 
        String scriptId = "cc_script_" + properties.get("id");
        
        html.append("<div class=\"cc-group\">");
        html.append("  <label class=\"cc-label\">Card Number</label>");
        html.append("  <input type=\"text\" class=\"cc-input\" name=\"").append(numberField).append("\" placeholder=\"0000 0000 0000 0000\" maxlength=\"19\" oninput=\"updateCCNum_").append(scriptId).append("(this)\">");
        html.append("</div>");

        html.append("<div class=\"cc-group\">");
        html.append("  <label class=\"cc-label\">Card Holder</label>");
        html.append("  <input type=\"text\" class=\"cc-input\" name=\"").append(nameField).append("\" placeholder=\"Name Surname\" oninput=\"updateCCName_").append(scriptId).append("(this)\">");
        html.append("</div>");

        html.append("<div class=\"cc-row\">");
        html.append("  <div class=\"cc-group cc-col\">");
        html.append("    <label class=\"cc-label\">Expires</label>");
        html.append("    <input type=\"text\" class=\"cc-input\" name=\"").append(expiryField).append("\" placeholder=\"MM/YY\" maxlength=\"5\" oninput=\"updateCCExp_").append(scriptId).append("(this)\">");
        html.append("  </div>");
        html.append("  <div class=\"cc-group cc-col\">");
        html.append("    <label class=\"cc-label\">CVC</label>");
        html.append("    <input type=\"text\" class=\"cc-input\" name=\"").append(cvcField).append("\" placeholder=\"123\" maxlength=\"4\">");
        html.append("  </div>");
        html.append("</div>");
        
        // Optional children (like custom buttons)
        StringBuilder childrenHtmlBuilder = new StringBuilder();
        for (io.jettra.wui.core.UIComponent child : this.getChildren()) {
            childrenHtmlBuilder.append(child.render());
        }
        String childrenHtml = childrenHtmlBuilder.toString();
        
        if (childrenHtml.isEmpty()) {
            html.append("<button type=\"").append(formAction.isEmpty() ? "button" : "submit").append("\" class=\"j-btn j-btn-primary cc-btn\">");
            html.append(submitText);
            html.append("</button>");
        } else {
            html.append(childrenHtml);
        }
        
        html.append(formEnd);

        // Scripts for updating preview
        html.append("<script>");
        html.append("function updateCCNum_").append(scriptId).append("(input) {");
        html.append("  let val = input.value.replace(/\\s/g, '').replace(/(\\d{4})/g, '$1 ').trim();");
        html.append("  input.value = val;");
        html.append("  document.getElementById('cc-preview-num_").append(properties.get("id")).append("').innerText = val || '•••• •••• •••• ••••';");
        html.append("}");
        html.append("function updateCCName_").append(scriptId).append("(input) {");
        html.append("  document.getElementById('cc-preview-name_").append(properties.get("id")).append("').innerText = input.value.trim() || 'NAME SURNAME';");
        html.append("}");
        html.append("function updateCCExp_").append(scriptId).append("(input) {");
        html.append("  let val = input.value.replace(/\\D/g, '');");
        html.append("  if(val.length > 2) val = val.substring(0,2) + '/' + val.substring(2);");
        html.append("  input.value = val;");
        html.append("  document.getElementById('cc-preview-exp_").append(properties.get("id")).append("').innerText = val || 'MM/YY';");
        html.append("}");
        html.append("</script>");

        this.setContent(html.toString());
        return super.render();
    }
}
