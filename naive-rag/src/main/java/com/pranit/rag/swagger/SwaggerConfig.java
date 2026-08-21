package com.pranit.rag.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Naive RAG System.",
                description = "Document Based RAG System.",
                contact = @Contact(
                        name = "Pranit Bhangale.",
                        url = "https://pranitbhangale.vercel.app",
                        email = "pranitbhangale1453@gmail.com"
                ),
                version = "1.0",
                summary = "Naive RAG module."
        )
)
public class SwaggerConfig {
}
