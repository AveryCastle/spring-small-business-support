package com.example.springlocalgovernmentsupport.repositories;

import com.example.springlocalgovernmentsupport.domains.SupportedItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportedItemRepository extends JpaRepository<SupportedItem, Long> {
}
