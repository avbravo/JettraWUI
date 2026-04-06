package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Map extends UIComponent {

    private double lat = 0.0;
    private double lng = 0.0;
    private int zoom = 13;
    private String markerTitle = "Location";

    private String mapId;

    public Map(String id) {
        super("div");
        this.mapId = id;
        setProperty("id", id);
        setStyle("width", "100%");
        setStyle("height", "400px");
        setStyle("border-radius", "8px");
        setStyle("z-index", "1");
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

    @Override
    public String render() {
        StringBuilder js = new StringBuilder();
        // Incluimos la extension leaflet (CSS/JS embebidos si es necesario o desde cache)
        js.append(LeafletExtension.getInlineScript());
        
        js.append("<script>\n");
        js.append("document.addEventListener('DOMContentLoaded', function() {\n");
        js.append("  if (document.getElementById('").append(mapId).append("')) {\n");
        js.append("     var map = L.map('").append(mapId).append("').setView([").append(lat).append(", ").append(lng).append("], ").append(zoom).append(");\n");
        js.append("     L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n");
        js.append("         attribution: '&copy; OpenStreetMap contributors'\n");
        js.append("     }).addTo(map);\n");
        js.append("     var marker = L.marker([").append(lat).append(", ").append(lng).append("]).addTo(map);\n");
        js.append("     marker.bindPopup('<b>").append(markerTitle).append("</b>').openPopup();\n");
        js.append("     // Fix para Leaflet icons cuando faltan imagenes locales:\n");
        js.append("     delete L.Icon.Default.prototype._getIconUrl;\n");
        js.append("     L.Icon.Default.mergeOptions({\n");
        js.append("       iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',\n");
        js.append("       iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',\n");
        js.append("       shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png'\n");
        js.append("     });\n");
        js.append("  }\n");
        js.append("});\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }
}
