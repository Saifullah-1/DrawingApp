package com.paint.backend;


import com.paint.backend.service.PaintApp;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:8081/")
@RestController
@RequestMapping(value = "/paint")
public class Controller {

    private final PaintApp paint;

    public Controller(PaintApp paint) {
        this.paint = paint;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public int create(@RequestBody String shape) {
        JSONObject json = new JSONObject(shape);
        return this.paint.create(json);
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public String update(@RequestParam int id, @RequestBody String newData) {
        return this.paint.update(id, newData);
    }

    @PostMapping(value = "/delete")
    public void delete(@RequestParam int id) {
        this.paint.delete(id);
    }

    @GetMapping(value = "/undo")
    @ResponseBody
    public String undo() {
        return this.paint.undo();
    }

    @GetMapping(value = "/redo")
    @ResponseBody
    public String redo() {
        return this.paint.redo();
    }

    @GetMapping(value = "/load")
    @ResponseBody
    public String load(@RequestParam String fileType) {
        try {
            return this.paint.load(fileType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/save")
    public void save(@RequestParam String fileType) {
        System.out.println("Yes");
        this.paint.save(fileType);
    }

    @GetMapping(value = "/new")
    public void start() {
        this.paint.clear();
    }
}
