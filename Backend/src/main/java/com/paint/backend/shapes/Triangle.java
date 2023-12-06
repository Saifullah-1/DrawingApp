package com.paint.backend.shapes;

import org.json.JSONObject;
public class Triangle extends Shape{
    private static int sides = 3;
    private float radius;

    public Triangle() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        json.put("radius", this.radius);
        json.put("sides", sides);
        return json.put("shapeName", shapeName);
    }

//    @Override
//    public IShape update(JSONObject updated) {
//        JSONObject json = this.draw();
//        for (Object obj : json.names()) {
//            json.put()
//        }
//        return ShapeFactory.create(updated);
//    }
}
