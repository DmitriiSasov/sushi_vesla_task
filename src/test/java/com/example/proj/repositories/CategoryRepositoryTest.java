package com.example.proj.repositories;

import com.example.proj.entities.Article;
import com.example.proj.entities.Category;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    private List<String> createdDataSetIds;

    private List<Category> createdCategories;

    @BeforeEach
    void insertDataSet() {
        var category1 = new Category();
        category1.setHeader("header1");
        category1.setDescription("MyDesc1");
        category1.setCreatedAt(new Date());
        category1.setCreatedBy("Admin");
        category1.setPhotoFilename("path1");
        category1.setArticles(Set.of(new Article("13213214098098","art1", "desc1", new Date(), "Admin", new ArrayList<>(), null, "path4")));
        var category2 = new Category();
        category2.setHeader("header2");
        category2.setDescription("MyDesc2");
        category2.setCreatedAt(new Date());
        category2.setCreatedBy("Admin");
        category2.setPhotoFilename("path2");
        category2.setArticles(Set.of(new Article("1321321478987","art2", "desc2", new Date(), "Admin", new ArrayList<>(), null, "path5")));
        var category3 = new Category();
        category3.setHeader("header3");
        category3.setDescription("MyDesc3");
        category3.setCreatedAt(new Date());
        category3.setCreatedBy("Admin");
        category3.setPhotoFilename("path3");
        category3.setArticles(Set.of(new Article("13213214123123","art3", "desc3", new Date(), "Admin", new ArrayList<>(), null, "path6")));
        List<Category> data = List.of(category1, category2, category3);
        createdCategories = repository.saveAll(data);
        createdDataSetIds = createdCategories.stream().map(Category::getId).collect(Collectors.toList());
    }

    @Test
    void tryToInsertIntoDB() {
        var category = new Category();
        category.setCreatedAt(new Date());
        category.setCreatedBy("me");
        category.setDescription("Some desc");
        category.setHeader("My header");
        category.setPhotoFilename("path8");
        category.setArticles(Set.of(
                new Article("13213214",
                        "some header", "some desc", new Date(),
                        "me", List.of("tag1", "tag2"), new Date(),
                        "path7"
                )
        ));

        var res = repository.insert(category);
        var foundCategory= repository.findByHeader(category.getHeader());

        assertEquals(res, foundCategory.orElseThrow(() -> new RuntimeException("")));

        repository.deleteById(res.getId());
    }

    @Test
    void getArticleByDesc() {
        var res = repository.findByDescriptionLike("MyDesc2").orElseThrow(RuntimeException::new);
        assertEquals(createdCategories.get(1), res);
    }

    @Test
    void getArticleById() {
        var res = repository.findByArticlesArticleId("13213214098098").orElseThrow(RuntimeException::new);
        assertEquals(createdCategories.get(0), res);
    }

    @Test
    void findArticleByIdInCategory() {
        var id = "13213214123123";
        var res = repository.findArticleInCategoryRepositoryById(id);
        assertEquals(createdCategories.get(2).getArticles()
                .stream()
                .filter(value -> value.getArticleId().equals(id))
                .findFirst().orElse(null), res);
    }

    @AfterEach
    public void clearDb() {
        repository.deleteAllById(createdDataSetIds);
    }


}