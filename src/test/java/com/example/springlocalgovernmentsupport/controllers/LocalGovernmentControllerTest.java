package com.example.springlocalgovernmentsupport.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springlocalgovernmentsupport.services.LocalGovernmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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

    @InjectMocks
    private LocalGovernmentController localGovernmentController;

    @Mock
    private LocalGovernmentService localGovernmentService;

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
}
