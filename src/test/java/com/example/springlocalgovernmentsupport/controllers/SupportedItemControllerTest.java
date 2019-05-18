package com.example.springlocalgovernmentsupport.controllers;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springlocalgovernmentsupport.services.LimitAmountSupportedItemService;
import com.example.springlocalgovernmentsupport.services.RateSupportedItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class SupportedItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LimitAmountSupportedItemService limitAmountSupportedItemService;

    @Mock
    private RateSupportedItemService rateSupportedItemService;

    @InjectMocks
    private SupportedItemController supportedItemController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(supportedItemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void givenLocalGovernments_whenFindByLimitAmountDescWithSize_thenReturnsOrderingCollection() throws Exception {
        int SIZE = 3;
        PageRequest pageable = PageRequest.of(0, SIZE, new Sort(Sort.Direction.DESC, "limitAmount"));
        given(limitAmountSupportedItemService.findAll(pageable)).willReturn(Arrays.asList("경기도", "제주도", "국토교통부"));

        mockMvc.perform(get("/v1/local-governments/names").param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(SIZE)))
                .andExpect(jsonPath("$", contains("경기도", "제주도", "국토교통부")));
    }

    @Test
    public void givenLocalGovernments_whenFindByFromRateAscWithSize_thenReturnsOrderingCollection() throws Exception {
        int SIZE = 3;
        PageRequest pageable = PageRequest.of(0, SIZE, new Sort(Sort.Direction.ASC, "fromRate"));
        given(rateSupportedItemService.findAll(pageable)).willReturn(Arrays.asList("경기도", "제주도", "국토교통부"));

        mockMvc.perform(get("/v1/local-governments/institutes").param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(SIZE)))
                .andExpect(jsonPath("$", contains("경기도", "제주도", "국토교통부")));
    }
}
