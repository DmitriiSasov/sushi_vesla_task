package com.example.proj.controllers;

import com.example.proj.configs.SpringFoxConfig;
import com.example.proj.entities.Article;
import com.example.proj.services.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/article")
@Api(tags = {SpringFoxConfig.ARTICLE_CONTROLLER}, produces = "json, image/png")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавление новой статьи в категорию", consumes = "Фотографию относящуюся к статьи, категорию" +
            " и прочую информацию по статье")
    public void addArticle(@RequestParam MultipartFile photo,
                           @RequestParam String categoryId,
                           @RequestParam String header,
                           @RequestParam String description,
                           @RequestParam String tags) {
        articleService.addArticleToCategory(photo, categoryId, header, description, tags);
    }

    @GetMapping(path = "/{article_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Получение статьи по ID")
    public Article getArticle(@PathVariable(name = "article_id") String articleId) {
        return articleService.getArticle(articleId);
    }

    @GetMapping(path = "/all/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Получение статей по категории ID")
    public Set<Article> getArticles(@PathVariable(name = "category_id") String categoryId) {
        return articleService.getArticles(categoryId);
    }

    @GetMapping(value = "/image/{article_id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value = "Получение изображения, закрепленного за статьей")
    public byte[] getArticleImage(@PathVariable("article_id") String articleId) {
        return articleService.getArticleImage(articleId);
    }


}
