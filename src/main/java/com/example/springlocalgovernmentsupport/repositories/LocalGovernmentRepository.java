package com.example.springlocalgovernmentsupport.repositories;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalGovernmentRepository extends JpaRepository<LocalGovernment, Long> {

    LocalGovernment findByName(String name);
}
