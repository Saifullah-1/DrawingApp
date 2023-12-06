package com.paint.backend.shapes;

import org.json.JSONObject;

public class Ellipse extends Shape {
    private float radiusX;
    private float radiusY;

    public Ellipse() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("radiusX", this.radiusX);
        json.put("radiusY", this.radiusY);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }
}
