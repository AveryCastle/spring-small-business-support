package com.example.springlocalgovernmentsupport.controllers;

import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentInputDto;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportItemOutputDto;
import com.example.springlocalgovernmentsupport.services.LocalGovernmentFacadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class LocalGovernmentController {

    @Autowired
    private LocalGovernmentFacadeService localGovernmentFacadeService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public int upload(@RequestParam("file") MultipartFile file) throws IOException {
        int size = localGovernmentFacadeService.storeFile(file.getInputStream());
        return size;
    }

    @GetMapping(value = "/local-governments")
    public List<LocalGovernmentSupportItemOutputDto> findAll() {
        return localGovernmentFacadeService.findAll();
    }

    @PostMapping(value = "/local-government")
    public LocalGovernmentSupportItemOutputDto findOne(@Valid @RequestBody LocalGovernmentInputDto localGovernmentInputDto) {
        return localGovernmentFacadeService.findByName(localGovernmentInputDto.getRegion());
    }
}
