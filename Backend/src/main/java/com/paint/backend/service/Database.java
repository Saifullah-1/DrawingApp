package com.paint.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.paint.backend.ShapeFactory;
import com.paint.backend.shapes.IShape;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//move update to shape class
public class Database {
    private int lastID = 0;
    private static Database instance;
    private List<IShape> shapesList = new ArrayList<>();
    private final Stack<List<IShape>> undoStack = new Stack<>();
    private final Stack<List<IShape>> redoStack = new Stack<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<IShape> getShapes() {return new ArrayList<>(this.shapesList); }
    public int create(JSONObject newShape) {
        newShape.put("id", ++lastID);
        newShape.put("state", "new");
        IShape shape = ShapeFactory.create(newShape);
        shapesList.add(shape);
        saveState();
        test();
        return this.lastID;
    }

    public String update(int id, String newData) { //will be edited for undo and redo
        IShape shape = null;
        for (IShape s : this.shapesList) {
            if (s.getID() == id) {
                shape = s;
                this.shapesList.remove(s);
                break;
            }
        }
        JSONObject data = new JSONObject(newData);
        JSONObject updated = shape.draw();
        for(String key : JSONObject.getNames(data)) updated.put(key, data.get(key));
        updated.put("state", "updated");
        IShape updatedShape = ShapeFactory.create(updated);
        shapesList.add(updatedShape);
        saveState();
        test();  //Testing
        return updated.toString();
    }

    public void delete(int id) {
        for (IShape shape : this.shapesList) {
            if (shape.getID() == id) {
                this.shapesList.remove(shape);
                break;
            }
        }
        saveState();
        test();
    }

    /*                                   UNDO & REDO                                   */
    public String undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(undoStack.pop());
            shapesList = undoStack.peek();
            IShape shape = null;
            if (!shapesList.isEmpty()) shape = shapesList.get(shapesList.size() - 1);
            test();
            return new JSONObject(shape).toString();
        }
        shapesList.clear();
        test();
        return "[]";
    }

    public String redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(redoStack.pop());
            shapesList = undoStack.peek();
            IShape shape = null;
            if (!shapesList.isEmpty()) shape = shapesList.get(shapesList.size() - 1);
            test();
            return new JSONObject(shape).toString();
        }
        test();
        return null;
    }

    private void saveState() {
        undoStack.push(new ArrayList<>(shapesList));
        redoStack.clear(); // Clear redo stack after a new change
    }

    /*                                   SAVE & LOAD                                   */
    public void saveJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        try(FileWriter fileWriter = new FileWriter("save.json")) {
            fileWriter.write(gson.toJson(this.shapesList));
            fileWriter.flush();
            return;
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getData(){
        return new JSONObject().put("lastID",lastID).put("shapes",this.shapesList);
    }

    public void saveXML() {
        File file = new File("save.xml");
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            XMLEncoder encoder = new XMLEncoder(fileStream);
            encoder.writeObject(XML.toString(instance.getData(),"data"));
            encoder.close();
            fileStream.flush();
            fileStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadJSON() {
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            // Read JSON array and map it to a list of objects
            List<Object> myObjectList = objectMapper.readValue(new File("save.json"), new TypeReference<List<Object>>() {});

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            String jsonStr = gson.toJson(myObjectList);
            start(jsonStr);
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loadXML() {
        File file = new File("save.xml");
        try {
            FileInputStream f2 = new FileInputStream(file.toPath().toString());
            XMLDecoder mydecoder = new XMLDecoder(f2);
            String result = (String) mydecoder.readObject();
            mydecoder.close();
            f2.close();
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON string as a JsonNode
            JsonNode jsonNode = objectMapper.readTree(XML.toJSONObject(result).toString());

            // Access specific fields or process the entire JSON structure as needed
            JsonNode shapesNode = jsonNode.path("data").path("shapes");
            JsonNode lastIDNode = jsonNode.path("data").path("lastID");

            lastID = lastIDNode.asInt();
            JSONArray jsonArray = new JSONArray(shapesNode.asText());
            JSONArray arr = startXML(jsonArray);
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.setPrettyPrinting();
//            Gson gson = gsonBuilder.create();
//            JSONObject jsonObject = jsonArray.toJSONObject(jsonArray);
//            System.out.println(jsonObject.toString());
//            JSONArray jsonArray1 = (JSONArray) jsonObject.get("myArrayList");
//            System.out.println(jsonArray1.toString());
//            JSONObject jsonObject1 = new JSONObject(jsonArray1.toString());
//            System.out.println(jsonObject1.toString());
//            start(jsonArray1.toString());

//            return gson.toJson(shapesNode.asText());
            return arr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        this.shapesList.clear();
        this.undoStack.clear();
        this.redoStack.clear();
        this.lastID = 0;
    }

    void start(String jsonStr) {
        System.out.println(jsonStr);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (Object jsonObject : jsonArray) {
            IShape shape = ShapeFactory.create(new JSONObject(jsonObject.toString()));
            this.shapesList.add(shape);
            this.lastID = shape.getID();
        }
        undoStack.push(shapesList);
    }

    JSONArray startXML(JSONArray jsonArray) {
        JSONArray arr = new JSONArray();
        for (Object jsonObject : jsonArray) {
            String str = (String) new JSONObject(jsonObject.toString()).get("attrs");
            IShape shape = ShapeFactory.create(new JSONObject(str));
            arr.put(new JSONObject(jsonObject.toString()).get("attrs"));
            this.shapesList.add(shape);
            this.lastID = shape.getID();
        }
        return arr;
    }
    public void test() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        System.out.println(gson.toJson(shapesList));
    }
}
