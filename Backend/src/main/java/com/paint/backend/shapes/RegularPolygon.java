package com.paint.backend.shapes;

import org.json.JSONObject;

public class RegularPolygon extends Shape {
    private float radius;
    private int sides;

    public RegularPolygon() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("radius", this.radius);
        json.put("sides", this.sides);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }
}
