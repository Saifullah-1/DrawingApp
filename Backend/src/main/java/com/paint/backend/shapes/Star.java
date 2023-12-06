package com.paint.backend.shapes;

import org.json.JSONObject;

public class Star extends Shape {
    private int numPoints;
    private float innerRadius;
    private float outerRadius;

    public Star() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("numPoints", this.numPoints);
        json.put("innerRadius", this.innerRadius);
        json.put("outerRadius", this.outerRadius);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }
}
