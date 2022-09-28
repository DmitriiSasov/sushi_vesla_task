package com.example.proj.services;

import com.example.proj.entities.Category;
import com.example.proj.exceptions.CategoryNotFoundException;
import com.example.proj.my_file_utils.MyFileReader;
import com.example.proj.my_file_utils.MyFileWriter;
import com.example.proj.repositories.CategoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
public class CategoryService {

    @Value("${upload-path}")
    private String uploadPath;

    @Value("${my-default-user-name}")
    private String defaultUser;

    private final MyFileWriter writer;

    private final MyFileReader reader;

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository, MyFileReader reader, MyFileWriter writer) {
        this.repository = repository;
        this.reader = reader;
        this.writer = writer;
    }

    @SneakyThrows
    public void addCategory(MultipartFile uploadedPhotoFile, String description, String header) {
        String photoFile = "";

        if (uploadedPhotoFile != null && !uploadedPhotoFile.isEmpty()) {
            writer.setFile(uploadedPhotoFile);
            writer.setDirectory(uploadPath);
            photoFile = writer.save();
        }

        var newCategory = new Category();
        newCategory.setHeader(Optional.ofNullable(header).orElse(""));
        newCategory.setDescription(Optional.ofNullable(description).orElse(""));
        newCategory.setCreatedBy(defaultUser);
        newCategory.setCreatedAt(new Date());
        newCategory.setArticles(new HashSet<>());
        newCategory.setPhotoFilename(photoFile);

        repository.save(newCategory);
    }

    @SneakyThrows
    public byte[] getCategoryImage(String categoryId) {
        var category = repository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(
                "Категорию с данным id не удалось найти"));
        if (category.getPhotoFilename().isEmpty()) {
            throw new IllegalStateException("Для данной категории не задана фотография");
        }
        reader.setParams(category.getPhotoFilename(), uploadPath);
        return reader.read();
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }
}
