package com.paint.backend.shapes;

import com.paint.backend.ShapeFactory;
import org.json.JSONObject;

public class Container extends Shape {

    public Container() { }

    @Override
    public JSONObject draw() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 0);
        jsonObject.put("shapeName", "container");
        jsonObject.put("fill", this.fill);
        return jsonObject;
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
