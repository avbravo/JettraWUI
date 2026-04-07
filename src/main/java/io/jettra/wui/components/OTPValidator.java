package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class OTPValidator extends UIComponent {

    private int amountOfDigits = 6;
    private String otpId;
    private String onCompleteJs = "";

    public OTPValidator(String id) {
        super("div");
        this.otpId = id;
        setProperty("id", id);
        setStyle("display", "flex");
        setStyle("gap", "10px");
        setStyle("justify-content", "center");
    }

    public OTPValidator setAmountOfDigits(int amount) {
        this.amountOfDigits = amount;
        return this;
    }

    public OTPValidator setOnComplete(String jsCallback) {
        this.onCompleteJs = jsCallback;
        return this;
    }

    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        
        // Append input boxes seamlessly inside this div
        for (int i = 0; i < amountOfDigits; i++) {
            html.append("<input type='text' maxlength='1' id='").append(otpId).append("_in_").append(i)
                .append("' class='j-input' style='width: 50px; height: 60px; font-size: 24px; text-align: center; border-radius: 8px;' />\n");
        }
        setContent(html.toString());

        // Attach logic to auto tab between inputs
        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("document.addEventListener('DOMContentLoaded', function() {\n");
        js.append("  setTimeout(function() {\n");
        js.append("    var digitsCount = ").append(amountOfDigits).append(";\n");
        js.append("    var baseId = '").append(otpId).append("_in_';\n");
        js.append("    for(var i=0; i<digitsCount; i++) {\n");
        js.append("      let input = document.getElementById(baseId + i);\n");
        js.append("      if(!input) continue;\n");
        js.append("      input.addEventListener('keyup', function(e) {\n");
        js.append("         var code = e.keyCode || e.which;\n");
        js.append("         var val = this.value;\n");
        js.append("         var idx = parseInt(this.id.split('_in_')[1]);\n");
        js.append("         // Move backward on backspace\n");
        js.append("         if(code === 8 && val === '' && idx > 0) {\n");
        js.append("            document.getElementById(baseId + (idx - 1)).focus();\n");
        js.append("         } else if(val !== '' && idx < digitsCount - 1) {\n");
        js.append("            document.getElementById(baseId + (idx + 1)).focus();\n");
        js.append("         }\n");
        js.append("         \n");
        js.append("         // Check completeness\n");
        js.append("         var fullOtp = '';\n");
        js.append("         var isComplete = true;\n");
        js.append("         for(var j=0; j<digitsCount; j++) {\n");
        js.append("            var part = document.getElementById(baseId + j);\n");
        js.append("            if(!part || part.value==='') { isComplete = false; break; }\n");
        js.append("            fullOtp += part.value;\n");
        js.append("         }\n");
        js.append("         if(isComplete) {\n");
        js.append("            var otpStr = fullOtp;\n");
        js.append("            ").append(onCompleteJs).append("\n");
        js.append("         }\n");
        js.append("      });\n");
        js.append("    }\n");
        js.append("  }, 100);\n");
        js.append("});\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }
}
