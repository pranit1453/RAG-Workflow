package com.pranit.rag.entities.entity;

import com.pranit.rag.entities.constant.FileStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "document",
        schema = "doc",
        indexes = {
                @Index(name = "idx_document_file_status", columnList = "file_status")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID documentId;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_status", nullable = false)
    private FileStatus fileStatus;

    @Column(name = "chunks_created", nullable = false)
    @Builder.Default
    private long chunksCreated = 0L;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Version
    @Builder.Default
    private Long version = 0L;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) this.createdAt = Instant.now();
    }
}