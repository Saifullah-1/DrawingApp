package com.paint.backend;

import org.json.JSONObject;
import com.google.gson.Gson;
import com.paint.backend.shapes.*;

/**
 * The ShapeFactory class is responsible for creating different shapes based on the provided JSON object.
 */
public class ShapeFactory {

    public ShapeFactory() {}

    /**
     * Creates a shape object based on the provided JSON object.
     *
     * @param newShape The JSON object representing the shape.
     * @return The created shape object.
     */
    public static IShape create(JSONObject newShape) {
        IShape shape = null;
        Gson gson = new Gson();
        String shapeType = newShape.getString("shapeName");
        if (shapeType.equalsIgnoreCase("circle")) {
            shape = gson.fromJson(newShape.toString(), Circle.class);
        } else if (shapeType.equalsIgnoreCase("regularpolygon")) {
            shape = gson.fromJson(newShape.toString(), RegularPolygon.class);
        } else if (shapeType.equalsIgnoreCase("rectangle")) {
            shape = gson.fromJson(newShape.toString(), Rect.class);
        } else if (shapeType.equalsIgnoreCase("ellipse")) {
            shape = gson.fromJson(newShape.toString(), Ellipse.class);
        } else if (shapeType.equalsIgnoreCase("line")) {
            shape = gson.fromJson(newShape.toString(), Line.class);
        } else if (shapeType.equalsIgnoreCase("star")) {
            shape = gson.fromJson(newShape.toString(), Star.class);
        } else if (shapeType.equalsIgnoreCase("square")) {
            shape = gson.fromJson(newShape.toString(), Square.class);
        } else if (shapeType.equalsIgnoreCase("triangle")) {
            shape = gson.fromJson(newShape.toString(), Triangle.class);
        } else if (shapeType.equalsIgnoreCase("container")) {
            shape = gson.fromJson(newShape.toString(), Container.class);
        }
        return shape;
    }
}
