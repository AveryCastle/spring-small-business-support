package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.domains.SupportedItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateSupportedItemService extends AbstractSupportedItemService {

    @Transactional(readOnly = true)
    public List<String> findAll(Pageable pageable) {
        Page<SupportedItem> supportedItemPage = supportedItemRepository.findAll(pageable);

        return supportedItemPage.stream()
                .map(supportedItem -> supportedItem.getInstitute())
                .collect(Collectors.toList());
    }
}
