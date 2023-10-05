package com.test.docs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.docs.entity.FileItem;
import com.test.docs.service.FileItemService;


@RestController
@RequestMapping("/filerepo")
public class FileRepoController {

    @Autowired
    private FileItemService fileItemService;
    @PostMapping("/save")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestHeader(value="userId") String userId) throws IOException {
        fileItemService.uploadFile(file, userId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/getall")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<FileItem> getAllFileItems() {
        return fileItemService.getAllFileItems();
    }

    @PostMapping("/delete")
    @CrossOrigin(origins = "http://localhost:4200")
    public String deleteFileRepoItem(@RequestParam(name = "id") long itemId) {
        return fileItemService.deleteItem(itemId);
    }

    @GetMapping("/download")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ByteArrayResource> download(@RequestParam(name = "id") long id) throws IOException {
        return fileItemService.downloadFile(id);
    }
}