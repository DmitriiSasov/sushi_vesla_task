package com.example.proj.controllers;

import com.example.proj.configs.SpringFoxConfig;
import com.example.proj.entities.Category;
import com.example.proj.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = {SpringFoxConfig.CATEGORY_CONTROLLER}, produces = "json, image/png")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавление новой категории", consumes = "Фотографию относящуюся к категории и" +
            " информацию о категории")
    public void createCategory(@RequestParam MultipartFile photoFile,
                               @RequestParam String description,
                               @RequestParam String header) {
        categoryService.addCategory(photoFile, header, description);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Получение всех категорий вместе со статьями")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/image/{category_id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value = "Получение изображения, закрепленного за категорией")
    public byte[] getCategoryImage(@PathVariable(name = "category_id") String categoryId) {
        return categoryService.getCategoryImage(categoryId);
    }
}
