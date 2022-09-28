package com.example.proj.repositories;

import com.example.proj.entities.Article;
import com.example.proj.entities.Category;
import com.example.proj.exceptions.CategoryNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByHeader(String header);

    Optional<Category> findByDescriptionLike(String description);

    Optional<Category> findByArticlesArticleId(String articleId);

    default Article findArticleInCategoryRepositoryById(String articleId) {
        return findByArticlesArticleId(articleId).orElseThrow(() -> new CategoryNotFoundException(
                        "Категория со статьей с таким id не была найдена"))
                .getArticles()
                .parallelStream()
                .filter(article -> article.getArticleId().equals(articleId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет статьи с таким id"));
    }
}
