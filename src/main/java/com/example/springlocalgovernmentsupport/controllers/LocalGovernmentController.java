package com.example.springlocalgovernmentsupport.controllers;

import com.example.springlocalgovernmentsupport.services.LocalGovernmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1")
public class LocalGovernmentController {

    @Autowired
    private LocalGovernmentService localGovernmentService;

    @PostMapping(value = "/upload", consumes = "text/csv")
    public String upload(@RequestBody InputStream body) throws IOException {
        return localGovernmentService.storeFile(body);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        return localGovernmentService.storeFile(file.getInputStream());
    }
}
