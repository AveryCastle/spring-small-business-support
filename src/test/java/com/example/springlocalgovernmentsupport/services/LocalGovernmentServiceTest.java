package com.example.springlocalgovernmentsupport.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import com.example.springlocalgovernmentsupport.repositories.LocalGovernmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LocalGovernmentServiceTest {

    final static String GANGNEUNG = "강릉시";

    @InjectMocks
    private LocalGovernmentService localGovernmentService;

    @Mock
    private LocalGovernmentRepository localGovernmentRepository;

    @Test
    public void givenEmpty_whenUpload_shouldSaveAll() {
        when(localGovernmentRepository.findByName(GANGNEUNG)).thenReturn(null);

        SupportedItem supportedItem = SupportedItem.builder().usage(UsageCode.OPERATION).build();
        LocalGovernment localGovernment = new LocalGovernment(GANGNEUNG, supportedItem);
        List<LocalGovernment> localGovernments = Arrays.asList(localGovernment);

        int size = localGovernmentService.storeFile(localGovernments);
        assertEquals(1, size);
    }

    @Test
    public void shouldFindAll() {
        SupportedItem supportedItem = SupportedItem.builder().usage(UsageCode.OPERATION).build();
        List<LocalGovernment> localGovernments = Arrays.asList(new LocalGovernment(GANGNEUNG, supportedItem));
        when(localGovernmentRepository.findAll()).thenReturn(localGovernments);

        List<LocalGovernment> list = localGovernmentService.findAll();
        LocalGovernment foundOne = list.get(0);
        assertEquals(GANGNEUNG, foundOne.getName());
        assertEquals(UsageCode.OPERATION, foundOne.getSupportedItem().getUsage());
    }

    @Nested
    public class Modify {

        private LocalGovernment localGovernment;

        @BeforeEach
        public void setUp() {
            SupportedItem supportedItem = SupportedItem.builder().usage(UsageCode.OPERATION).build();
            localGovernment = new LocalGovernment(GANGNEUNG, supportedItem);
            when(localGovernmentRepository.findByName(GANGNEUNG)).thenReturn(localGovernment);
        }

        @Test
        public void givenLocalGovernmentAndSupportedItem_whenChangingSupportedItem_thenModifyAttributes() {
            SupportedItem changedSupportedItem =
                    SupportedItem.builder()
                            .target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                            .usage(UsageCode.FACILITIES)
                            .build();
            localGovernment.setSupportedItem(changedSupportedItem);
            List<LocalGovernment> localGovernments = Arrays.asList(localGovernment);
            int size = localGovernmentService.storeFile(localGovernments);

            assertEquals(1, size);

            LocalGovernment foundOne = localGovernmentRepository.findByName(GANGNEUNG);
            SupportedItem modifiedSupportedItem = foundOne.getSupportedItem();
            assertEquals("강릉시 소재 중소기업으로서 강릉시장이 추천한 자", modifiedSupportedItem.getTarget());
            assertEquals(UsageCode.FACILITIES, modifiedSupportedItem.getUsage());
        }
    }
}
