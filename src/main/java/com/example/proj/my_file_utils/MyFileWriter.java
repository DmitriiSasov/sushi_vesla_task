package com.example.proj.my_file_utils;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Неизменяемый класс для чтения
 */
@Component
@Scope("prototype")
public class MyFileWriter {

    /**
     * Загружаемый из сети файл
     */
    private MultipartFile file;

    /**
     * Директория, куда надо сохранить файл после загрузки
     */
    private String directory;

    public void setFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Имя файла должно быть непустым");
        }
        this.file = file;
    }

    public void setDirectory(String directory) {
        if (directory == null || directory.isBlank()) {
            throw new IllegalArgumentException("Директория не должна быть null");
        }
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return file.getOriginalFilename();
    }

    /**
     * Сохраняем содержимое файла на диск в указанную директорию (директория создается, если ее нет)
     * @return Имя для сохраненного файла
     */
    @SneakyThrows
    public String save() {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("Имя файла должно быть непустым");
        }
        if (directory == null || directory.isBlank()) {
            throw new IllegalStateException("Директория должна быть указана");
        }
        File uploadDir = new File(directory);
        if (!uploadDir.exists() && !uploadDir.mkdir()) {
            throw new FileNotFoundException("Не удалось создать директорию для загрузки файла");
        }
        String savedFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(new File(uploadDir + "\\" + savedFilename));
        return savedFilename;
    }

}
