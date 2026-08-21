CREATE EXTENSION IF NOT EXISTS pgcrypto;
---------------------------------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS doc;
CREATE SCHEMA IF NOT EXISTS seed;

---------------------------------------------------------------------------------------
CREATE TABLE doc.document (
    document_id     UUID NOT NULL,
    file_name       VARCHAR(255) NOT NULL,
    file_size       BIGINT NOT NULL,
    file_status     VARCHAR(255) NOT NULL,
    chunks_created  BIGINT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    version         BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT pk_document
        PRIMARY KEY (document_id),

    CONSTRAINT uk_document_file_name
        UNIQUE (file_name)
);

CREATE INDEX idx_document_file_status
    ON doc.document (file_status);

---------------------------------------------------------------------------------------
CREATE TABLE seed.seed_history (
    seed_name   VARCHAR(255) NOT NULL,
    seeded_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    version     BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT pk_seed_history
        PRIMARY KEY (seed_name)
);

---------------------------------------------------------------------------------------