package com.paint.backend;


import com.paint.backend.service.PaintApp;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(shape);
        JSONObject json = new JSONObject(shape);
        return this.paint.create(json);
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public String update(@RequestParam int id, @RequestBody String newData) {
        return this.paint.update(id, newData);
    }

    @GetMapping(value = "/delete")
    public void delete(@RequestParam int id) {
        this.paint.delete(id);
    }

    @PostMapping(value = "/undo")
    @ResponseBody
    public String undo() {
        return this.paint.undo();
    }

    @PostMapping(value = "/redo")
    @ResponseBody
    public void redo() {
        this.paint.redo();
    }

//    @PostMapping(value = "/save")
//    public String save(@RequestParam String fileType) {
//        System.out.println(fileType);
//        return this.paint.save(fileType);
//    }

    @GetMapping("/load")
    @ResponseBody
    public String load(@RequestParam String fileType) {
        return this.paint.load(fileType);
    }

    @GetMapping(value = "/save")
    public void save(@RequestParam String fileType) {
        System.out.println("Yes");
        this.paint.save(fileType);
    }
}