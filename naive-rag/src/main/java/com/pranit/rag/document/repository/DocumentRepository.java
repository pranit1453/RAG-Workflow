package com.pranit.rag.document.repository;

import com.pranit.rag.entities.Document;
import com.pranit.rag.entities.FileStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    boolean findByFileName(@Nullable String originalFilename);

    @Modifying
    @Query("""
                update Document d
                set d.fileStatus = :status
                where d.documentId = :documentId
            """)
    int updateFileStatus(@Param("documentId") UUID documentId, @Param("status") FileStatus status);
}
