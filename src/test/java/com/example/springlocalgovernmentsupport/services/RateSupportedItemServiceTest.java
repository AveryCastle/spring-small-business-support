package com.example.springlocalgovernmentsupport.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

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
class RateSupportedItemServiceTest {

    private static final int SIZE = 3;

    private final PageRequest pageable = PageRequest.of(0, SIZE, new Sort(Sort.Direction.ASC, "fromRate"));

    private final List<SupportedItem> supportedItems =
            List.of(SupportedItem.builder().fromRate(2f).institute("강릉시").build(),
                    SupportedItem.builder().fromRate(3.0f).institute("농림수산식품부").build(),
                    SupportedItem.builder().fromRate(5.15f).institute("한국방송통신전파진흥원").build());

    @Mock
    private SupportedItemRepository supportedItemRepository;

    @InjectMocks
    private RateSupportedItemService rateSupportedItemService;

    @BeforeEach
    public void setUp() {
        given(supportedItemRepository.findAll(pageable)).willReturn(new PageImpl(supportedItems));
    }

    @Test
    public void givenLocalGovernmentAndSupportedItem_whenFindWithSize_thenReturnsSortedList() {
        List<String> actual = rateSupportedItemService.findAll(pageable);

        assertEquals(SIZE, actual.size());
        IntStream.range(0, SIZE).forEach(index -> {
            assertEquals(supportedItems.get(index).getInstitute(), actual.get(index));
        });
    }
}
