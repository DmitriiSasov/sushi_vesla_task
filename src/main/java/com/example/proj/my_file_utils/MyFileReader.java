package com.example.proj.my_file_utils;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;

@Component
@Scope("prototype")
public class MyFileReader {

    private String filename;

    private String directory;

    private File file;

    public void setParams(String filename, String directory) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Имя файла не должно быть пустым");
        }
        if (directory == null || directory.isBlank()) {
            throw new IllegalArgumentException("Директория не должна быть пустой");
        }
        this.filename = filename;
        this.directory = directory;
        this.file = new File(directory + "\\" + filename);
    }

    public String getFilename() {
        return filename;
    }

    public String getDirectory() {
        return directory;
    }

    /**
     * @return считанные из файла, переданного по сети данные
     */
    @SneakyThrows
    public byte[] read() {
        return Files.readAllBytes(file.toPath());
    }

}
