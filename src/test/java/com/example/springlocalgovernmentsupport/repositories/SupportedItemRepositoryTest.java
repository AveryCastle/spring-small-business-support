package com.example.springlocalgovernmentsupport.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.springlocalgovernmentsupport.config.JpaAuditingConfiguration;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = JpaAuditingConfiguration.class)
class SupportedItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SupportedItemRepository supportedItemRepository;

    private Long supportedItemId;

    @BeforeEach
    public void setUp() {
        SupportedItem gangneung = SupportedItem.builder().target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.OPERATION).limitAmount(-1L).fromRate(3.00f).institute("강릉시").mgmt("강릉점")
                .reception("강릉시 소재 영업점").build();

        SupportedItem savedOne = entityManager.persist(gangneung);
        supportedItemId = savedOne.getId();
    }

    @Test
    public void givenEmptyEndRate_whenSavingFromRate_shouldAlsoSaveEndRate() {
        SupportedItem foundOne = supportedItemRepository.findById(supportedItemId).get();

        assertEquals(3.00f, foundOne.getEndRate());
    }

    @Test
    public void whenSaving_shouldSaveCreatedDateAndUpdateDate() {
        SupportedItem foundOne = supportedItemRepository.findById(supportedItemId).get();

        assertNotNull(foundOne.getCreatedDate());
        assertNotNull(foundOne.getModifiedDate());
    }
}
