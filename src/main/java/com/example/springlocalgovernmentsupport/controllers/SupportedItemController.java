package com.example.springlocalgovernmentsupport.controllers;

import com.example.springlocalgovernmentsupport.services.SupportedItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SupportedItemController {

    @Autowired
    private SupportedItemService supportedItemService;

    @GetMapping(value = "/local-government")
    public List<String> findLocalGovernmentWithSize(@RequestParam("size") int size) {
        size = validateSize(size);

        Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "limitAmount");
        return supportedItemService.findAll(pageable);
    }

    private int validateSize(int size) {
        return size <= 0 ? Integer.MAX_VALUE : size;
    }
}
