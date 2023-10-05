package com.test.docs.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.test.docs.entity.DataBaseSequence;
import com.test.docs.entity.FileItem;
import com.test.docs.repository.FileItemRepository;

@Service
public class FileItemService {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private FileItemRepository fileItemRepository;

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private GridFsTemplate template;

    public void uploadFile(MultipartFile file, String loginUser) throws IOException {
    	byte[] compressedData = compressData(file.getBytes());
        FileItem fileItem = new FileItem();
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", file.getSize());
        ObjectId objectId = template.store(new ByteArrayInputStream(compressedData), file.getOriginalFilename(), file.getContentType(), metadata);
        fileItem.setFileData(objectId);
        fileItem.setId(generateSequence(FileItem.SEQUENCE_NAME));
        fileItem.setUserId(loginUser);
        fileItem.setFileName(file.getOriginalFilename());
        fileItem.setUploadDate(LocalDateTime.now());
        fileItemRepository.save(fileItem);
    }
    
    private byte[] compressData(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public long generateSequence(String seqName) {
        DataBaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DataBaseSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public List<FileItem> getAllFileItems() {
        return fileItemRepository.findAll();
    }

    public String  deleteItem(long itemId) {
        Optional<FileItem> fileItem = fileItemRepository.findById(itemId);

        if (fileItem.isPresent()) {
            fileItemRepository.delete(fileItem.get());

            return "file deleted successfully";
        }

        return "file not exist please refresh page";
    }

    public ResponseEntity<ByteArrayResource> downloadFile(long fileItemId) throws IOException {
        Optional<FileItem> fileItem = fileItemRepository.findById(fileItemId);

        if (fileItem.isPresent()) {
            FileItem fileItemPresent = fileItem.get();
            ObjectId objectId = fileItemPresent.getFileData();
            GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(objectId.toString())));
            Document metaData = gridFSFile.getMetadata();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(metaData.get("_contentType").toString()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileItemPresent.getFileName() + "\"")
                    .body(new ByteArrayResource(
                            IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream())
                    ));
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
