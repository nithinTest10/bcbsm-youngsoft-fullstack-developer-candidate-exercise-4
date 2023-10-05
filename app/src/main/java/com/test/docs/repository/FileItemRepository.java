package com.test.docs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.docs.entity.FileItem;

public interface FileItemRepository extends MongoRepository<FileItem, Long> {
}
