package com.example.springlocalgovernmentsupport.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.repositories.SupportedItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class SupportedItemServiceTest {

    private static final int SIZE = 3;

    private final PageRequest pageable = PageRequest.of(0, SIZE, new Sort(Sort.Direction.DESC, "limitAmount"));

    private final List<SupportedItem> supportedItems =
            List.of(SupportedItem.builder().localGovernment(new LocalGovernment("경기도")).limitAmount(100L).build(),
                    SupportedItem.builder().localGovernment(new LocalGovernment("제주")).limitAmount(50L).build(),
                    SupportedItem.builder().localGovernment(new LocalGovernment("국토교통부")).limitAmount(10L).build());

    @Mock
    private SupportedItemRepository supportedItemRepository;

    @InjectMocks
    private SupportedItemService supportedItemService;

    @BeforeEach
    public void setUp() {
        given(supportedItemRepository.findAll(pageable)).willReturn(new PageImpl(supportedItems));
    }

    @Test
    public void givenLocalGovernmentAndSupportedItem_whenFindWithSize_thenReturnsSortedList() {
        List<String> actual = supportedItemService.findAll(pageable);

        assertEquals(SIZE, actual.size());
        IntStream.range(0, SIZE).forEach(index -> {
            LocalGovernment expected = supportedItems.get(index).getLocalGovernment();
            assertEquals(expected.getName(), actual.get(index));
        });
    }
}
