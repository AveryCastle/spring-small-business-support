package com.example.springlocalgovernmentsupport.controllers;

import com.example.springlocalgovernmentsupport.services.LocalGovernmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class LocalGovernmentController {

    @Autowired
    private LocalGovernmentService localGovernmentService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public int upload(@RequestParam("file") MultipartFile file) throws IOException {
        int size = localGovernmentService.storeFile(file.getInputStream());
        return size;
    }
}
