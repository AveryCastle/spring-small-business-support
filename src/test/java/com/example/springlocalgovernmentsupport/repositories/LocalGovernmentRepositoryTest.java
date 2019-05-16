package com.example.springlocalgovernmentsupport.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springlocalgovernmentsupport.config.JpaAuditingConfiguration;
import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import javax.persistence.PersistenceException;

@DataJpaTest
@Import(value = JpaAuditingConfiguration.class)
class LocalGovernmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    private Long localGovernmentId, supportedItemId;

    @BeforeEach
    public void setUp() {
        SupportedItem gangneungSupportedItem = SupportedItem.builder().target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.OPERATION).limitAmount(-1L).fromRate(3.00f).institute("강릉시").mgmt("강릉점")
                .reception("강릉시 소재 영업점").build();
        entityManager.persist(gangneungSupportedItem);

        supportedItemId = gangneungSupportedItem.getId();

        LocalGovernment gangneung = new LocalGovernment("강릉시", gangneungSupportedItem);
        entityManager.persist(gangneung);

        localGovernmentId = gangneung.getId();
    }

    @Test
    public void givenRelationalLocalGovernmentAndSupportedItem_whenFindLocalGovernment_thenFindWithSupportedItem() {
        LocalGovernment foundOne = localGovernmentRepository.findById(localGovernmentId).get();
        assertEquals("강릉시", foundOne.getName());

        SupportedItem supportedItem = foundOne.getSupportedItem();
        assertEquals(supportedItemId, supportedItem.getId());
    }

    @Test
    public void whenSavingDuplcateLocalGovernmentName_thenThrowsException() {
        LocalGovernment duplicatedLocalGovernment = new LocalGovernment("강릉시");
        assertThrows(PersistenceException.class, () -> {
            entityManager.persist(duplicatedLocalGovernment);
        });
    }
}
