package com.example.springlocalgovernmentsupport.commons;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseTest {

    protected InputStream readInputStreamFromFile(String filePath) throws IOException {
        final File resource = new ClassPathResource(filePath).getFile();
        final InputStream fileInputStream = new FileInputStream(resource);
        return fileInputStream;
    }
}
