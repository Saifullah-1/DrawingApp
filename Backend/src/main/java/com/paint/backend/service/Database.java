package com.paint.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paint.backend.ShapeFactory;
import com.paint.backend.shapes.IShape;
import com.paint.backend.shapes.Shape;
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
    private int lastID = -1; //the first ID will be equal 0 because of the container
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
//        convertData();
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
        JSONObject updated = shape.draw();
        IShape updatedShape = shape.update(new JSONObject(newData));
        shapesList.add(updatedShape);
        saveState();
        test();  //Testing
        return updatedShape.draw().toString();
    }

    public void delete(int id) {
        if(id == 0)
            return;

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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        if (undoStack.size() > 1) {
            System.out.println("peek : " + undoStack.peek());
            redoStack.push(new ArrayList<>(shapesList));
            undoStack.pop();
            System.out.println("peek : " + redoStack.peek());
            System.out.println("peek : " + undoStack.peek());
            shapesList = new ArrayList<>(undoStack.peek());
            test();
            return gson.toJson(shapesList);
        }
        test();
        return gson.toJson(shapesList);
    }

    public String redo() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        if (!redoStack.isEmpty()) {
            System.out.println("peek : " + redoStack.peek());
            undoStack.push(new ArrayList<>(redoStack.peek()));
            redoStack.pop();
            System.out.println("peek : " + undoStack.peek());
            shapesList = new ArrayList<>(undoStack.peek());
            test();
            return gson.toJson(shapesList);
        }
        test();
        return gson.toJson(shapesList);
    }

    private void saveState() {
        undoStack.push(new ArrayList<>(shapesList));
        redoStack.clear(); // Clear redo stack after a new change
    }

    /*                                   SAVE & LOAD                                   */
    public void saveJSON() { // handle if the file is empty  <<<<<<<<<<<<<<<< !!!!!!!!!!!!!!!!!!x`
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

    public JSONObject convertData() {
        JSONObject jsonObject = new JSONObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
//        System.out.println("shapeslist : " + gson.toJson(this.shapesList));
        jsonObject.put("lastID", lastID);
        jsonObject.put("shapesList", gson.toJson(this.shapesList));
//        System.out.println("obj : " + jsonObject.toString());
        return jsonObject;
    }
    public void saveXML() {
        File file = new File("save.xml");
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            XMLEncoder encoder = new XMLEncoder(fileStream);
//            System.out.println(instance.getData());
            System.out.println(XML.toString(instance.convertData(),"paint"));
            encoder.writeObject(XML.toString(instance.convertData(), "paint"));
            encoder.close();
            fileStream.flush();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Object> myObjectList = objectMapper.readValue(new File("save.json"), new TypeReference<List<Object>>() {});

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            String jsonStr = gson.toJson(myObjectList);
            startJSON(jsonStr);
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public String loadXML() throws IOException {
        File file = new File("save.xml");
        try {
            FileInputStream fileInputStream = new FileInputStream(file.toPath().toString());
            XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);
            String data = (String) xmlDecoder.readObject();
            xmlDecoder.close();
            fileInputStream.close();

            JSONObject jsonObject = XML.toJSONObject(data);
//            System.out.println(jsonObject.toString());
//            System.out.println(jsonObject.get("paint").toString());
            JSONObject jsonObject1 = new JSONObject(jsonObject.get("paint").toString());
//            System.out.println(jsonObject1.toString());
//            System.out.println(jsonObject1.get("shapesList").toString());
            JSONArray jsonArray = new JSONArray(jsonObject1.get("shapesList").toString());
//            System.out.println(jsonArray.toString());
            clear();
            this.lastID = jsonObject1.getInt("lastID");
//            System.out.println("ID: " + this.lastID);
//            System.out.println("Shapes : " + gson.toJson(jsonArray));
            startXML(jsonArray);
            return jsonArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public void clear() {
        this.shapesList.clear();
        this.undoStack.clear();
        this.redoStack.clear();
        this.lastID = -1;
//        createContainer();
    }

    public void createContainer() {
        JSONObject container = new JSONObject();
        container.put("id", ++this.lastID);
        container.put("shapeName", "container");
        container.put("fill", "#FFFFFF");
        IShape containerShape = ShapeFactory.create(container);
        this.shapesList.add(containerShape);
        saveState();
    }
    /*                                             UPLOADING DATA                                             */
    void startJSON(String jsonStr) {
//        System.out.println(jsonStr);
        clear();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (Object jsonObject : jsonArray) {
            int temp;
            IShape shape = ShapeFactory.create(new JSONObject(jsonObject.toString()));
            this.shapesList.add(shape);
            temp = this.lastID;
            this.lastID = Math.max(shape.getID(), temp);
        }
        saveState();
    }

    void startXML(JSONArray jsonArray) {
//        System.out.println(jsonArray.toString());
        for (Object jsonObject : jsonArray) {
            IShape shape = ShapeFactory.create(new JSONObject(jsonObject.toString()));
            this.shapesList.add(shape);
        }
        saveState();
    }

    public boolean isEmpty() {
        return this.shapesList.isEmpty();
    }
    /*                                             TEST                                             */
    public void test() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        System.out.println(gson.toJson(shapesList));
    }
}