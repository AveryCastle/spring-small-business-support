package com.example.springlocalgovernmentsupport.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentInputDto;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportItemOutputDto;
import com.example.springlocalgovernmentsupport.services.LocalGovernmentFacadeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LocalGovernmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocalGovernmentFacadeService localGovernmentFacadeService;

    @InjectMocks
    private LocalGovernmentController localGovernmentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(localGovernmentController).build();
    }

    @Test
    public void shouldUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temp.csv", "text/csv", InputStream.nullInputStream());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/v1/upload").file(file);

        // When & Then
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenLocalGovernment_whenFind_thenReturnsOne() throws Exception {
        // Given.
        final String localGovernmentName = "강릉시";
        LocalGovernmentSupportItemOutputDto localGovernmentSupportItemOutputDto =
                LocalGovernmentSupportItemOutputDto.builder().region(localGovernmentName).build();
        when(localGovernmentFacadeService.findByName(localGovernmentName)).thenReturn(localGovernmentSupportItemOutputDto);

        // When & Then.
        LocalGovernmentInputDto inputDto = new LocalGovernmentInputDto(localGovernmentName);
        String jsonString = convertObjectToString(inputDto);
        mockMvc.perform(post("/v1/local-government").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region", is(localGovernmentName)));
    }

    private String convertObjectToString(LocalGovernmentInputDto localGovernmentInputDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(localGovernmentInputDto);
    }
}
