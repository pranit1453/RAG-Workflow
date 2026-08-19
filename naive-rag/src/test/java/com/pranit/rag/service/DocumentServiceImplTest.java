package com.pranit.rag.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class DocumentServiceImplTest {

    @Autowired
    private DocumentServiceImpl service;

    @Test
    void shouldPerformEtlProcessOnFile() throws IOException {
        this.service.performEtlProcessOnFile();
    }

}
