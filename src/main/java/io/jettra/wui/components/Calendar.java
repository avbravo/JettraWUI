package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Calendar extends UIComponent {

    public Calendar() {
        super("div");
        addClass("j-calendar");
        // Inline CSS mapping to support internal basic look if user doesn't inject css
        setStyle("border", "1px solid var(--jettra-border)");
        setStyle("border-radius", "8px");
        setStyle("overflow", "hidden");
        setStyle("background", "var(--jettra-surface)");
    }
    
    @Override
    public String render() {
        String cid = (String) getProperties().get("id");
        if (cid == null || cid.isEmpty()) {
            cid = "cal_" + System.currentTimeMillis();
            setProperty("id", cid);
        }

        StringBuilder html = new StringBuilder();
        
        // Container
        String innerId = cid + "_inner";
        html.append("<div id='").append(innerId).append("'></div>");
        
        // Javascript to render dynamic calendar
        html.append("<script>\n");
        html.append("(function() {\n");
        // Delay logic briefly to guarantee JS runs after Jettra UI attaches the DOM string
        html.append("  setTimeout(function() {\n");
        html.append("    var calEl = document.getElementById('").append(innerId).append("');\n");
        html.append("    if(!calEl) return;\n");
        html.append("    var currentDate = new Date();\n");
        html.append("    var currentMonth = currentDate.getMonth();\n");
        html.append("    var currentYear = currentDate.getFullYear();\n");
        html.append("    var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];\n");
        
        // Expose globally to avoid scoping issues with inline onclick string literals
        html.append("    window['cal_api_").append(innerId).append("'] = {\n");
        html.append("      prevMonth: function() { currentMonth--; if(currentMonth < 0) { currentMonth=11; currentYear--; } renderCalendar(currentMonth, currentYear); },\n");
        html.append("      nextMonth: function() { currentMonth++; if(currentMonth > 11) { currentMonth=0; currentYear++; } renderCalendar(currentMonth, currentYear); },\n");
        html.append("      setMonth: function(m) { currentMonth = parseInt(m); renderCalendar(currentMonth, currentYear); },\n");
        html.append("      setYear: function(y) { currentYear = parseInt(y); renderCalendar(currentMonth, currentYear); }\n");
        html.append("    };\n");

        html.append("    function renderCalendar(month, year) {\n");
        html.append("      var firstDay = new Date(year, month, 1).getDay();\n");
        html.append("      var daysInMonth = new Date(year, month + 1, 0).getDate();\n");
        html.append("      var apiName = 'window.cal_api_").append(innerId).append("';\n");
        html.append("      var calHtml = '';\n");
        
        // Header
        html.append("      calHtml += \"<div style='background:rgba(0,0,0,0.2); padding:10px; display:flex; justify-content:space-between; align-items:center; font-weight:bold; border-bottom:1px solid var(--jettra-border); color:var(--jettra-accent);'>\";\n");
        html.append("      calHtml += \"  <button type='button' class='j-btn' style='padding:2px 8px;' onclick='\"+apiName+\".prevMonth()'>&lt;</button>\";\n");
        html.append("      calHtml += \"  <select class='j-input' style='background:transparent; border:none; color:var(--jettra-accent); width:auto;' onchange='\"+apiName+\".setMonth(this.value)'>\";\n");
        html.append("      for(var i=0; i<12; i++) { calHtml += \"<option style='color:#000' value='\"+i+\"' \"+(i===month?'selected':'')+\">\"+monthNames[i]+\"</option>\"; }\n");
        html.append("      calHtml += \"  </select>\";\n");
        html.append("      calHtml += \"  <input type='number' class='j-input' style='background:transparent; border:none; color:var(--jettra-accent); width:60px;' value='\"+year+\"' onchange='\"+apiName+\".setYear(this.value)'>\";\n");
        html.append("      calHtml += \"  <button type='button' class='j-btn' style='padding:2px 8px;' onclick='\"+apiName+\".nextMonth()'>&gt;</button>\";\n");
        html.append("      calHtml += \"</div>\";\n");
        
        // Days Row
        html.append("      calHtml += \"<div style='display:grid; grid-template-columns:repeat(7, 1fr); text-align:center; padding:10px; font-weight:bold; font-size:0.8rem; opacity:0.8'>\";\n");
        html.append("      var days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];\n");
        html.append("      for(var d=0; d<days.length; d++) { calHtml += \"<div>\"+days[d]+\"</div>\"; }\n");
        html.append("      calHtml += \"</div>\";\n");
        
        // Grid
        html.append("      calHtml += \"<div class='j-calendar-grid' style='display:grid; grid-template-columns:repeat(7, 1fr); padding:10px; gap:5px;'>\";\n");
        html.append("      for(var i=0; i<firstDay; i++) { calHtml += \"<div></div>\"; }\n");
        html.append("      var today = new Date();\n");
        html.append("      for(var i=1; i<=daysInMonth; i++) {\n");
        html.append("          var isToday = (i === today.getDate() && month === today.getMonth() && year === today.getFullYear());\n");
        html.append("          var cellStyle = 'padding:10px; text-align:center; border-radius:4px; transition:all 0.2s; cursor:pointer; position:relative;';\n");
        html.append("          var bg = isToday ? 'background:var(--jettra-accent); color:#000;' : 'background:rgba(255,255,255,0.05);';\n");
        html.append("          var dt = year+'-'+(month+1)+'-'+i;\n");
        html.append("          calHtml += \"<div class='calendar-day' data-date='\"+dt+\"' onclick=\\\"window.show3DMessage('Calendar Selection', 'Date chosen: \"+dt+\"')\\\" style='\"+cellStyle+bg+\"' onmouseover=\\\"if(\"+!isToday+\"){this.style.background='rgba(255,255,255,0.1)'}\\\" onmouseout=\\\"if(\"+!isToday+\"){this.style.background='rgba(255,255,255,0.05)'}\\\">\" + i + \"</div>\";\n");
        html.append("      }\n");
        html.append("      calHtml += \"</div>\";\n");
        html.append("      calEl.innerHTML = calHtml;\n");
        html.append("    }\n");
        html.append("    renderCalendar(currentMonth, currentYear);\n");
        html.append("  }, 100);\n");
        html.append("})();\n");
        html.append("</script>");
        
        setContent(html.toString());
        return super.render();
    }
}
