package com.example.springlocalgovernmentsupport.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocalGovernmentRepositoryTest {

    private static final String GANGNEUNG = "강릉시";

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    private String localGovernmentId;

    private Long supportedItemId;

    @BeforeEach
    public void setUp() {
        SupportedItem gangneungSupportedItem = SupportedItem.builder().target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.OPERATION).limitAmount(-1L).fromRate(3.00f).institute("강릉시").mgmt("강릉점")
                .reception("강릉시 소재 영업점").build();
        LocalGovernment gangneung = new LocalGovernment(GANGNEUNG, gangneungSupportedItem);
        localGovernmentRepository.save(gangneung);

        localGovernmentId = gangneung.getId();
        supportedItemId = gangneungSupportedItem.getId();
    }

    @AfterEach
    public void tearDown() {
        localGovernmentRepository.deleteAll();
    }

    @Test
    public void shouldSaveCustomGeneratedStringId() {
        LocalGovernment foundOne = localGovernmentRepository.findById(localGovernmentId).get();
        assertTrue(foundOne.getId().contains("reg"));
    }

    @Test
    public void givenRelationalLocalGovernmentAndSupportedItem_whenFindLocalGovernment_thenFindWithSupportedItem() {
        LocalGovernment foundOne = localGovernmentRepository.findById(localGovernmentId).get();
        assertEquals(GANGNEUNG, foundOne.getName());

        SupportedItem supportedItem = foundOne.getSupportedItem();
        assertEquals(supportedItemId, supportedItem.getId());
    }

    @Test
    public void whenSavingDuplcateLocalGovernmentName_thenThrowsException() {
        LocalGovernment duplicatedLocalGovernment = new LocalGovernment(GANGNEUNG);
        assertThrows(Exception.class, () -> {
            localGovernmentRepository.save(duplicatedLocalGovernment);
        });
    }
}
