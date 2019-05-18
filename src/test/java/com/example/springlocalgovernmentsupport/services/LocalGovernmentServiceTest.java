package com.example.springlocalgovernmentsupport.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import com.example.springlocalgovernmentsupport.repositories.LocalGovernmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LocalGovernmentServiceTest {

    @InjectMocks
    private LocalGovernmentService localGovernmentService;

    @Mock
    private LocalGovernmentRepository localGovernmentRepository;

    @Test
    public void shouldSaveAll() {
        final String gangneung = "강릉시";
        SupportedItem supportedItem = SupportedItem.builder().usage(UsageCode.OPERATION).build();
        LocalGovernment localGovernment = new LocalGovernment(gangneung, supportedItem);
        List<LocalGovernment> localGovernments = Arrays.asList(localGovernment);
        when(localGovernmentRepository.findByName(gangneung)).thenReturn(localGovernment);

        int size = localGovernmentService.storeFile(localGovernments);
        assertEquals(1, size);
    }

    @Test
    public void shouldFindAll() {
        final String GANGNEUNG = "강릉시";
        SupportedItem supportedItem = SupportedItem.builder().usage(UsageCode.OPERATION).build();
        List<LocalGovernment> localGovernments = Arrays.asList(new LocalGovernment(GANGNEUNG, supportedItem));
        when(localGovernmentRepository.findAll()).thenReturn(localGovernments);

        List<LocalGovernment> list = localGovernmentService.findAll();
        LocalGovernment foundOne = list.get(0);
        assertEquals(GANGNEUNG, foundOne.getName());
        assertEquals(UsageCode.OPERATION, foundOne.getSupportedItem().getUsage());
    }
}
