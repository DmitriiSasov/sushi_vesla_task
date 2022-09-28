package com.example.proj.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {


    private String articleId = UUID.randomUUID().toString();

    private String header;

    private String description;

    private Date createdAt;

    private String createdBy;

    private List<String> tags;

    private Date publicationDate;

    private String photoFilename;

}
