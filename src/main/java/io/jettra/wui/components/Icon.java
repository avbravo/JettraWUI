package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * A generic Icon component to render SVG vectors or Emojis.
 */
public class Icon extends UIComponent {

    // Common predefined vector icons (SVG)
    public static final String HOME = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z\"></path><polyline points=\"9 22 9 12 15 12 15 22\"></polyline></svg>";
    public static final String USER = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg>";
    public static final String SETTINGS = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><circle cx=\"12\" cy=\"12\" r=\"3\"></circle><path d=\"M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z\"></path></svg>";
    public static final String TRASH = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><polyline points=\"3 6 5 6 21 6\"></polyline><path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path><line x1=\"10\" y1=\"11\" x2=\"10\" y2=\"17\"></line><line x1=\"14\" y1=\"11\" x2=\"14\" y2=\"17\"></line></svg>";
    public static final String CHECK = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><polyline points=\"20 6 9 17 4 12\"></polyline></svg>";
    public static final String CLOSE = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line><line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line></svg>";
    public static final String PLUS = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"12\" y1=\"5\" x2=\"12\" y2=\"19\"></line><line x1=\"5\" y1=\"12\" x2=\"19\" y2=\"12\"></line></svg>";
    public static final String MINUS = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"5\" y1=\"12\" x2=\"19\" y2=\"12\"></line></svg>";

    // Additional SVG Icons
    public static final String EYE = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z\"></path><circle cx=\"12\" cy=\"12\" r=\"3\"></circle></svg>";
    public static final String EYE_OFF = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24\"></path><line x1=\"1\" y1=\"1\" x2=\"23\" y2=\"23\"></line></svg>";
    public static final String MENU = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><line x1=\"3\" y1=\"12\" x2=\"21\" y2=\"12\"></line><line x1=\"3\" y1=\"6\" x2=\"21\" y2=\"6\"></line><line x1=\"3\" y1=\"18\" x2=\"21\" y2=\"18\"></line></svg>";
    public static final String SEARCH = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><circle cx=\"11\" cy=\"11\" r=\"8\"></circle><line x1=\"21\" y1=\"21\" x2=\"16.65\" y2=\"16.65\"></line></svg>";
    public static final String CALENDAR = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><rect x=\"3\" y=\"4\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"></rect><line x1=\"16\" y1=\"2\" x2=\"16\" y2=\"6\"></line><line x1=\"8\" y1=\"2\" x2=\"8\" y2=\"6\"></line><line x1=\"3\" y1=\"10\" x2=\"21\" y2=\"10\"></line></svg>";
    public static final String HEART = "<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z\"></path></svg>";
    
    // Unicode HTML Symbols
    public static final String UNI_STAR_BLACK = "&#x2605;";
    public static final String UNI_STAR_WHITE = "&#x2606;";
    public static final String UNI_HEART_BLACK = "&#x2665;";
    public static final String UNI_HEART_WHITE = "&#x2661;";
    public static final String UNI_WARNING = "&#x26A0;";
    public static final String UNI_INFO = "&#x2139;";
    public static final String UNI_CHECK = "&#x2714;";
    public static final String UNI_CROSS = "&#x2716;";
    public static final String UNI_TELEPHONE = "&#x260E;";
    public static final String UNI_ENVELOPE = "&#x2709;";
    public static final String UNI_SCISSORS = "&#x2702;";
    public static final String UNI_AIRPLANE = "&#x2708;";
    public static final String UNI_SUN = "&#x2600;";
    public static final String UNI_CLOUD = "&#x2601;";
    public static final String UNI_UMBRELLA = "&#x2602;";
    public static final String UNI_SNOWMAN = "&#x2603;";
    public static final String UNI_COMET = "&#x2604;";
    public static final String UNI_LIGHTNING = "&#x26A1;";
    public static final String UNI_FLOWER = "&#x273F;";
    public static final String UNI_MUSIC = "&#x266B;";
    public static final String UNI_GEAR = "&#x2699;";
    public static final String UNI_SPARKLES = "&#x2728;";

    private String iconContent;

    public Icon() {
        this(USER);
    }

    public Icon(String iconContent) {
        super("div");
        this.iconContent = iconContent;
        addClass("j-icon-wrapper");
        setStyle("display", "inline-flex");
        setStyle("align-items", "center");
        setStyle("justify-content", "center");
        setStyle("width", "24px");  // default size
        setStyle("height", "24px");
        setContent(iconContent);
    }
    
    public Icon setSize(String size) {
        setStyle("width", size);
        setStyle("height", size);
        setStyle("font-size", size);
        return this;
    }
    
    public Icon setColor(String color) {
        setStyle("color", color);
        // Need to ensure SVG paths inherit color correctly
        if (iconContent != null && iconContent.trim().startsWith("<svg")) {
           // SVGs with stroke="currentColor" will automatically inherit this
        }
        return this;
    }
    
    public Icon setIconContent(String iconContent) {
        this.iconContent = iconContent;
        setContent(iconContent);
        return this;
    }
    
    public String getIconContent() {
        return iconContent;
    }

    @Override
    public Icon setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Icon setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Icon setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Icon addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Icon removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Icon setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Icon setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Icon addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Icon add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
