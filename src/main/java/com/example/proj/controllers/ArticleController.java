package com.example.proj.controllers;

import com.example.proj.entities.Article;
import com.example.proj.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addArticle(@RequestParam MultipartFile photo,
                           @RequestParam String categoryId,
                           @RequestParam String header,
                           @RequestParam String description,
                           @RequestParam String tags) {
        articleService.addArticleToCategory(photo, categoryId, header, description, tags);
    }

    @GetMapping("/{article_id}")
    public Article getArticle(@PathVariable(name = "article_id") String articleId) {
        return articleService.getArticle(articleId);
    }

    @GetMapping("/all/{category_id}")
    public Set<Article> getArticles(@PathVariable(name = "category_id") String categoryId) {
        return articleService.getArticles(categoryId);
    }

    @GetMapping(value = "/image/{article_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getArticleImage(@PathVariable("article_id") String articleId) {
        return articleService.getArticleImage(articleId);
    }


}
