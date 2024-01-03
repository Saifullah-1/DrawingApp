package com.paint.backend.shapes;


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
    protected float scaleX;
    protected float scaleY;

    @Override
    public int getID() {
        return id;
    }

}
