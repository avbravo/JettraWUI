package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;

public class Carousel extends UIComponent {
    
    private static class Element extends UIComponent {
        public Element(String tag) { super(tag); }
    }
    
    public Carousel(String id) {
        super("div");
        setProperty("id", id);
        this.addClass("j-carousel-wrapper j-component");
        this.setStyle("position", "relative");
        this.setStyle("display", "flex");
        this.setStyle("flex-direction", "column");
        this.setStyle("padding", "10px");
        this.setStyle("gap", "10px");
        this.setStyle("align-items", "center");
        
        UIComponent track = new Element("div");
        track.setProperty("id", id + "-track");
        track.addClass("j-carousel-track");
        track.setStyle("display", "flex");
        track.setStyle("overflow-x", "auto");
        track.setStyle("scroll-snap-type", "x mandatory");
        track.setStyle("gap", "15px");
        track.setStyle("scroll-behavior", "smooth");
        track.setStyle("width", "100%");
        // Hide scrollbar syntax for cleaner UI
        track.setStyle("-ms-overflow-style", "none");
        track.setStyle("scrollbar-width", "none");
        
        super.add(track);
        
        // Add navigation controls
        UIComponent controls = new Element("div");
        controls.setStyle("display", "flex");
        controls.setStyle("justify-content", "center");
        controls.setStyle("gap", "10px");
        controls.setStyle("margin-top", "10px");
        
        UIComponent btnFirst = createControlButton("&laquo;", "Primero");
        UIComponent btnPrev = createControlButton("&lsaquo;", "Anterior");
        UIComponent btnNext = createControlButton("&rsaquo;", "Siguiente");
        UIComponent btnLast = createControlButton("&raquo;", "Último");
        
        controls.add(btnFirst).add(btnPrev).add(btnNext).add(btnLast);
        
        // JS functionality
        String jsPrev = String.format("event.preventDefault(); document.getElementById('%s-track').scrollBy({left: -document.getElementById('%s-track').offsetWidth, behavior: 'smooth'});", id, id);
        btnPrev.setProperty("onclick", jsPrev);
        
        String jsNext = String.format("event.preventDefault(); document.getElementById('%s-track').scrollBy({left: document.getElementById('%s-track').offsetWidth, behavior: 'smooth'});", id, id);
        btnNext.setProperty("onclick", jsNext);
        
        String jsFirst = String.format("event.preventDefault(); document.getElementById('%s-track').scrollTo({left: 0, behavior: 'smooth'});", id);
        btnFirst.setProperty("onclick", jsFirst);
        
        String jsLast = String.format("event.preventDefault(); document.getElementById('%s-track').scrollTo({left: document.getElementById('%s-track').scrollWidth, behavior: 'smooth'});", id, id);
        btnLast.setProperty("onclick", jsLast);

        super.add(controls);
    }
    
    private UIComponent createControlButton(String label, String title) {
        UIComponent btn = new Element("button");
        btn.setContent(label);
        btn.addClass("j-btn");
        btn.setProperty("title", title);
        btn.setStyle("padding", "5px 15px");
        btn.setStyle("font-size", "1.2rem");
        btn.setStyle("display", "flex");
        btn.setStyle("align-items", "center");
        btn.setStyle("justify-content", "center");
        btn.setStyle("min-width", "40px");
        return btn;
    }
    
    public void addSlide(UIComponent content) {
        UIComponent slide = new Element("div");
        slide.setStyle("scroll-snap-align", "start");
        slide.setStyle("flex", "0 0 100%");
        slide.setStyle("min-width", "100%");
        slide.add(content);
        
        if (!getChildren().isEmpty()) {
            getChildren().get(0).add(slide);
        }
    }

    @Override
    public Carousel setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Carousel setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Carousel setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Carousel addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Carousel removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Carousel setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Carousel setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Carousel addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Carousel add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
