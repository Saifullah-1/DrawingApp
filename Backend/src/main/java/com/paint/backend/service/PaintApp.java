package com.paint.backend.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaintApp {
    private final Database serv;
    public PaintApp(){
        serv = Database.getInstance();
    }
    public int create(JSONObject newShape) {
        return this.serv.create(newShape);
    }

    public String update(int id, String newData) {
        return serv.update(id, newData);
    }

    public void delete(int id) {
        this.serv.delete(id);
    }

    public String undo() {
        return this.serv.undo();
    }

    public void redo() {
        this.serv.redo();
    }

    public void save(String fileType) {
        if (fileType.equalsIgnoreCase("json"))
            this.serv.saveJSON();
        this.serv.saveXML();

    }

    public String load(String fileType) {
        this.serv.clear();
        if (fileType.equalsIgnoreCase("json"))
            return this.serv.loadJSON();
        return this.serv.loadXML();
    }

    public void clear() {
        this.serv.clear();
    }
}
