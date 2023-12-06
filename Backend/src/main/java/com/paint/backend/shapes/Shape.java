package com.paint.backend.shapes;

import com.paint.backend.ShapeFactory;
import org.json.JSONObject;

public abstract class Shape implements IShape {
    protected String state;
    protected int id;
    protected String shapeName;
    protected String stroke;
    protected String fill;
    protected float x;
    protected float y;
    protected float strokeWidth;
    protected boolean draggable;

    @Override
    public int getID() {
        return id;
    }

}
