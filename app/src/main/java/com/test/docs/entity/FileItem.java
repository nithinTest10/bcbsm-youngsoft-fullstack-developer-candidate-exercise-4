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

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ObjectId getFileData() {
		return fileData;
	}

	public void setFileData(ObjectId fileData) {
		this.fileData = fileData;
	}
}
