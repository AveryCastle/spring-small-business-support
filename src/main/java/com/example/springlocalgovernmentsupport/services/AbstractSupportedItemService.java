package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.repositories.SupportedItemRepository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSupportedItemService {

    @Autowired
    protected SupportedItemRepository supportedItemRepository;
}
