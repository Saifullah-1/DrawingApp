package com.paint.backend.shapes;

import com.paint.backend.ShapeFactory;
import org.json.JSONObject;

public class Line extends Shape {
    private float[] points;
    private String lineCap;
    private String lineJoin;
    private float tension;

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
        json.put("tension", this.tension);
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