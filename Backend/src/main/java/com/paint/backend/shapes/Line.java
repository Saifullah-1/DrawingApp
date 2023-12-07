package com.paint.backend.shapes;

import org.json.JSONObject;

public class Line extends Shape {
    private float[] points;
    private String lineCap;
    private String lineJoin;

    public Line() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("points", this.points);
        json.put("lineCap", this.lineCap);
        json.put("lineJoin", this.lineJoin);
        json.put("stroke", stroke);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }
}
