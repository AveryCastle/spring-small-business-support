package com.example.springlocalgovernmentsupport.commons;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseTest {

    protected InputStream readInputStreamFromFile(String filePath) throws IOException {
        File resource = readFile(filePath);
        final InputStream fileInputStream = new FileInputStream(resource);
        return fileInputStream;
    }

    protected File readFile(String filePath) throws IOException {
        return new ClassPathResource(filePath).getFile();
    }
}
