package com.paint.backend.shapes;

import org.json.JSONObject;

public class Rect extends Shape {
    private float width;
    private float height;

    public Rect() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("width", this.width);
        json.put("height", this.height);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }
}
