package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Map extends UIComponent {

    private double lat = 0.0;
    private double lng = 0.0;
    private int zoom = 13;
    private String markerTitle = "Location";
    private String onClickJs = "";
    private boolean enableSearch = false;
    private java.util.List<Waypoint> waypoints = new java.util.ArrayList<>();
    private java.util.List<Route> routes = new java.util.ArrayList<>();

    private String mapId;

    public Map(String id) {
        super("div");
        this.mapId = id;
        setProperty("id", id);
        setStyle("width", "100%");
        setStyle("height", "400px");
        setStyle("border-radius", "8px");
        setStyle("z-index", "1");
        setStyle("position", "relative");
    }

    public Map setCenter(double lat, double lng, int zoom) {
        this.lat = lat;
        this.lng = lng;
        this.zoom = zoom;
        return this;
    }

    public Map setMarker(String title) {
        this.markerTitle = title;
        return this;
    }

    public Map setOnMapClick(String jsFunction) {
        this.onClickJs = jsFunction;
        return this;
    }

    public Map addWaypoint(double lat, double lng, String title) {
        waypoints.add(new Waypoint(lat, lng, title));
        return this;
    }

    public Map addRoute(double startLat, double startLng, double endLat, double endLng) {
        routes.add(new Route(startLat, startLng, endLat, endLng));
        return this;
    }

    public Map setEnableSearch(boolean search) {
        this.enableSearch = search;
        return this;
    }

    @Override
    public String render() {
        StringBuilder js = new StringBuilder();
        js.append(LeafletExtension.getInlineScript());
        
        js.append("<script>\n");
        js.append("document.addEventListener('DOMContentLoaded', function() {\n");
        js.append("  var mapEl = document.getElementById('").append(mapId).append("');\n");
        js.append("  if (mapEl) {\n");
        
        if (enableSearch) {
            js.append("     var searchBox = document.createElement('div');\n");
            js.append("     searchBox.style.position = 'absolute'; searchBox.style.top = '10px'; searchBox.style.right = '10px'; searchBox.style.zIndex = '1000'; searchBox.style.background = 'white'; searchBox.style.padding = '5px'; searchBox.style.borderRadius = '4px';\n");
            js.append("     searchBox.innerHTML = '<input type=\"text\" id=\"search_").append(mapId).append("\" placeholder=\"Search...\" onkeydown=\"if(event.key===\\'Enter\\'){ searchMap_").append(mapId).append("(this.value); event.preventDefault(); }\" style=\"border:1px solid #ccc; padding:4px\" /> <button type=\"button\" onclick=\"searchMap_").append(mapId).append("(document.getElementById(\\'search_").append(mapId).append("\\').value)\" style=\"padding:4px; cursor:pointer\">Q</button>';\n");
            js.append("     mapEl.appendChild(searchBox);\n");
            js.append("     window.searchMap_").append(mapId).append(" = function(query) {\n");
            js.append("         fetch('https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURIComponent(query))\n");
            js.append("         .then(res => res.json()).then(data => {\n");
            js.append("             if(data && data.length > 0) {\n");
            js.append("                 window['map_").append(mapId).append("'].setView([data[0].lat, data[0].lon], 13);\n");
            js.append("                 L.marker([data[0].lat, data[0].lon]).addTo(window['map_").append(mapId).append("']).bindPopup('<b>'+data[0].display_name+'</b>').openPopup();\n");
            js.append("             }\n");
            js.append("         });\n");
            js.append("     };\n");
        }

        js.append("     var map = L.map('").append(mapId).append("').setView([").append(lat).append(", ").append(lng).append("], ").append(zoom).append(");\n");
        js.append("     window['map_").append(mapId).append("'] = map;\n");
        js.append("     L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n");
        js.append("         attribution: '&copy; OpenStreetMap contributors'\n");
        js.append("     }).addTo(map);\n");
        
        js.append("     delete L.Icon.Default.prototype._getIconUrl;\n");
        js.append("     L.Icon.Default.mergeOptions({\n");
        js.append("       iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',\n");
        js.append("       iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',\n");
        js.append("       shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png'\n");
        js.append("     });\n");

        js.append("     var marker = L.marker([").append(lat).append(", ").append(lng).append("]).addTo(map);\n");
        js.append("     marker.bindPopup('<b>").append(markerTitle).append("</b>').openPopup();\n");
        
        for (Waypoint w : waypoints) {
            js.append("     L.marker([").append(w.lat).append(", ").append(w.lng).append("]).addTo(map).bindPopup('<b>").append(w.title).append("</b>');\n");
        }

        for (Route r : routes) {
            js.append("     var latlngs = [[").append(r.lat1).append(", ").append(r.lng1).append("], [").append(r.lat2).append(", ").append(r.lng2).append("]];\n");
            js.append("     L.polyline(latlngs, {color: 'red', weight: 4}).addTo(map);\n");
        }
        
        if (onClickJs != null && !onClickJs.isEmpty()) {
            js.append("     map.on('click', function(e) {\n");
            js.append("         var lat = e.latlng.lat; var lng = e.latlng.lng;\n");
            js.append("         ").append(onClickJs).append("\n");
            js.append("     });\n");
        }

        js.append("  }\n");
        js.append("});\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }
    
    private static class Waypoint {
        public double lat, lng;
        public String title;
        public Waypoint(double lat, double lng, String title) { this.lat = lat; this.lng = lng; this.title = title; }
    }
    
    private static class Route {
        public double lat1, lng1, lat2, lng2;
        public Route(double lat1, double lng1, double lat2, double lng2) { this.lat1 = lat1; this.lng1 = lng1; this.lat2 = lat2; this.lng2 = lng2; }
    }
}
