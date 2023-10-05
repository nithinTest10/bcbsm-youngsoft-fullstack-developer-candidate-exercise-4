package com.test.docs.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "filestorage")
public class FileItem {

    @Transient
    public static final String SEQUENCE_NAME = "fileItemSeq";

    @Id
    private long id;

    private String userId;

    private String fileName;

    private LocalDateTime uploadDate;

    private ObjectId fileData;
}
