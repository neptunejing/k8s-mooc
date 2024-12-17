package com.nanqing.todoapp.controller;

import com.nanqing.todoapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class RequestController {

    @Autowired
    private ImageService imageService;

    @GetMapping("")
    public String getTodoPage(Model model) throws IOException {
        String imageUrl = imageService.getCachedImage();
        model.addAttribute("imageUrl", imageUrl);

        // hardcoded todos
        List<String> todos = Arrays.asList("TODO 1", "TODO 2");
        model.addAttribute("todos", todos);

        return "todo";
    }


//    @GetMapping("/images/{filename}")
//    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
//        Path imagePath = Paths.get(System.getenv("CACHE_DIR")).resolve(filename);
//        Resource image = new UrlResource(imagePath.toUri());
//
//        if (image.exists() && image.isReadable()) {
//            return ResponseEntity.ok().body(image);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/port")
    @ResponseBody
    public String getPort() {
        return System.getenv("PORT") != null ? System.getenv("PORT") : "Port not set";
    }
}
