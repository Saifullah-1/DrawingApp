package com.paint.backend.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PaintApp {
    private final Database serv;
    public PaintApp(){
        serv = Database.getInstance();
    }

    public int create(JSONObject newShape) {
        if (this.serv.isEmpty())
            this.serv.createContainer();
        return this.serv.create(newShape);
    }

    public String update(int id, String newData) {
        if (this.serv.isEmpty())
            this.serv.createContainer();
        return serv.update(id, newData);
    }

    public void delete(int id) {
        this.serv.delete(id);
    }

    public String undo() {
        return this.serv.undo();
    }

    public String redo() {
        return this.serv.redo();
    }

    public void save(String fileType) {
        if (this.serv.isEmpty())
            this.serv.createContainer();
        if (fileType.equalsIgnoreCase("json"))
            this.serv.saveJSON();
        else
            this.serv.saveXML();
    }

    public String load(String fileType) throws IOException {
        this.serv.clear();
        if (fileType.equalsIgnoreCase("json"))
            return this.serv.loadJSON();
        return this.serv.loadXML();
    }

    public void clear() {
        this.serv.clear();
    }
}
