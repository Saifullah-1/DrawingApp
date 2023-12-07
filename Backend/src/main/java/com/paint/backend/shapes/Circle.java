package com.paint.backend.shapes;

import com.paint.backend.ShapeFactory;
import org.json.JSONObject;

public class Circle extends Shape {
    private float radius;

    public Circle() {}

    @Override
    public JSONObject draw() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("x", x);
        json.put("y", y);
        json.put("radius", this.radius);
        json.put("stroke", stroke);
        json.put("fill", fill);
        json.put("strokeWidth", strokeWidth);
        json.put("draggable", draggable);
        return json.put("shapeName", shapeName);
    }

    @Override
    public IShape update(JSONObject updatesData) {
        JSONObject updated = this.draw();
        for (String key : JSONObject.getNames(updatesData))
            updated.put(key, updatesData.get(key));
        updated.put("state", "updated");
        return ShapeFactory.create(updated);
    }
}
