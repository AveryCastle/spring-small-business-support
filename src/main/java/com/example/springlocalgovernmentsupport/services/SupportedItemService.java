package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.repositories.SupportedItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportedItemService {

    @Autowired
    private SupportedItemRepository supportedItemRepository;

    @Transactional(readOnly = true)
    public List<String> findAll(Pageable pageable) {
        Page<SupportedItem> supportedItemPage = supportedItemRepository.findAll(pageable);

        return supportedItemPage.stream()
                .map(supportedItem -> supportedItem.getLocalGovernment().getName())
                .collect(Collectors.toList());
    }
}
