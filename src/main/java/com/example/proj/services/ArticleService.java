package com.example.proj.services;

import com.example.proj.entities.Article;
import com.example.proj.exceptions.CategoryNotFoundException;
import com.example.proj.my_file_utils.MyFileReader;
import com.example.proj.my_file_utils.MyFileWriter;
import com.example.proj.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Value("${my-default-user-name}")
    private String defaultUserName;

    @Value("${upload-path}")
    private String uploadPath;

    private final CategoryRepository repository;

    private final MyFileReader reader;

    private final MyFileWriter writer;

    public ArticleService(CategoryRepository categoryRepository, MyFileReader reader, MyFileWriter writer) {
        this.repository = categoryRepository;
        this.reader = reader;
        this.writer = writer;
    }

    public Article getArticle(String articleId) {
        return repository.findArticleInCategoryRepositoryById(articleId);
    }

    public Set<Article> getArticles(String categoryId) {
        return repository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(
                "Категория, с таким id не найдена")).getArticles();

    }

    public void addArticleToCategory(MultipartFile photo, String categoryId, String header, String description, String tags) {
        var category = repository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(
                "Категория, куда надо добавить статью не найдена"));

        String savedFilename = "";
        if (photo != null && !photo.isEmpty()) {
            writer.setFile(photo);
            writer.setDirectory(uploadPath);
            savedFilename = writer.save();
        }

        if (header == null || description == null) {
            throw new IllegalStateException("Некорректное поля для новой статьи");
        }
        var newArticle = new Article();
        newArticle.setDescription(description);
        newArticle.setHeader(header);
        newArticle.setPhotoFilename(savedFilename);
        newArticle.setCreatedAt(new Date());
        newArticle.setCreatedBy(defaultUserName);
        newArticle.setTags(List.of(tags.split(" ")));
        category.getArticles().add(newArticle);
        repository.save(category);
    }

    public byte[] getArticleImage(String articleId) {
        var article= repository.findArticleInCategoryRepositoryById(articleId);

        if (article.getPhotoFilename().isEmpty()) {
            throw new IllegalStateException("Для данной статьи не задана фотография");
        }
        reader.setParams(article.getPhotoFilename(), uploadPath);
        return reader.read();
    }
}
