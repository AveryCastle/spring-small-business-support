package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.repositories.LocalGovernmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LocalGovernmentService {

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    @Transactional
    public int storeFile(List<LocalGovernment> localGovernments) {
        localGovernments.stream().forEach(localGovernment -> {
            LocalGovernment foundOne = localGovernmentRepository.findByName(localGovernment.getName());
            if (Objects.isNull(foundOne)) {
                localGovernmentRepository.save(localGovernment);
            } else {
                foundOne.setName(localGovernment.getName());
                SupportedItem foundSupportedItem = foundOne.getSupportedItem();
                foundSupportedItem.update(foundSupportedItem);
            }
        });
        return localGovernments.size();
    }

    public List<LocalGovernment> findAll() {
        return localGovernmentRepository.findAll();
    }

    public LocalGovernment findByName(String name) {
        return localGovernmentRepository.findByName(name);
    }
}
