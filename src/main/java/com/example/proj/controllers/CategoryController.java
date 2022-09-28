package com.example.proj.controllers;

import com.example.proj.entities.Category;
import com.example.proj.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestParam MultipartFile photoFile,
                               @RequestParam String description,
                               @RequestParam String header) {
        categoryService.addCategory(photoFile, header, description);
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/image/{category_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCategoryImage(@PathVariable(name = "category_id") String categoryId) {
        return categoryService.getCategoryImage(categoryId);
    }
}
