package com.pranit.rag.document.repository;

import com.pranit.rag.entities.constant.FileStatus;
import com.pranit.rag.entities.entity.Document;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {

    boolean findByFileName(@Nullable String originalFilename);

    @Modifying
    @Query("""
                update Document d
                set d.fileStatus = :status
                where d.documentId = :documentId
            """)
    void updateFileStatus(@Param("documentId") UUID documentId, @Param("status") FileStatus status);

    boolean existsByDocumentId(UUID documentId);

    Optional<Document> findByDocumentId(UUID documentId);
}
