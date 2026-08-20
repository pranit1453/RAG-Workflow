package com.pranit.rag.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
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