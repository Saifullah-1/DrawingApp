package com.paint.backend.shapes;

import org.json.JSONObject;

public interface IShape {
    JSONObject draw();

//    IShape update(JSONObject updated);

    int getID();
}
