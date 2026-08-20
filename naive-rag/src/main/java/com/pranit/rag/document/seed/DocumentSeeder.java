package com.pranit.rag.document.seed;

import com.pranit.rag.document.exception.DocumentProcessingException;
import com.pranit.rag.document.repository.DocumentRepository;
import com.pranit.rag.document.repository.SeedHistoryRepository;
import com.pranit.rag.document.service.DocumentStatusService;
import com.pranit.rag.entities.Document;
import com.pranit.rag.entities.FileStatus;
import com.pranit.rag.entities.SeedHistory;
import com.pranit.rag.pipeline.factory.DocumentPipelineFactory;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class DocumentSeeder implements ApplicationRunner {

    private final DocumentPipelineFactory factory;
    private final SeedHistoryRepository seedHistoryRepository;
    private final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private final DocumentRepository documentRepository;
    private final DocumentStatusService documentStatusService;

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        Resource[] resources = resourceResolver.getResources("classpath:file/*");
        final List<Future<?>> futures = new ArrayList<>();

        for (Resource resource : resources) {
            final String seedName = resource.getFilename();
            if (seedName == null) continue;
            if (seedHistoryRepository.existsBySeedName(seedName)) continue;
            futures.add(executor.submit(() -> {
                try {
                    uploadFileAndProcessIndexing(resource, seedName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.close();
    }

    private void uploadFileAndProcessIndexing(final Resource resource, final String seedName) throws IOException {
        try {
            final Document document = Document.builder()
                    .fileName(seedName)
                    .fileSize(resource.contentLength())
                    .fileStatus(FileStatus.UPLOADING)
                    .chunksCreated(0)
                    .build();
            final Document savedDocument = documentRepository.save(document);
            documentStatusService.markProcessing(savedDocument.getDocumentId());
            final long chunkSize = factory.getPipeline(savedDocument.getDocumentId(), resource);
            if (chunkSize <= 0) {
                documentStatusService.markFailed(savedDocument.getDocumentId());
                throw new DocumentProcessingException("No chunks were created: " + resource.getFilename());
            }
            savedDocument.setChunksCreated(chunkSize);
            savedDocument.setFileStatus(FileStatus.INDEXED);
            documentRepository.save(savedDocument);
            final SeedHistory history = SeedHistory.builder()
                    .seedName(seedName)
                    .seedAt(Instant.now())
                    .build();
            seedHistoryRepository.save(history);
        } catch (Exception e) {
            throw new DocumentProcessingException("Failed to process: " + seedName + "{" + e.getMessage() + "}");
        }
    }
}
