--The script to initialize the schema was sourced from the Spring Batch Core dependency: org.springframework.batch.core.

CREATE SCHEMA document_schema;
SET SCHEMA 'document_schema';

CREATE TABLE documents
(
    id            UUID NOT NULL,
    "user"        VARCHAR(255),
    document_name VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_documents PRIMARY KEY (id)
);

CREATE INDEX idx_user ON documents("user");


CREATE TABLE tags
(
    id          UUID NOT NULL,
    name        VARCHAR(255),
    document_id UUID,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tags PRIMARY KEY (id),
        CONSTRAINT fk_document_id FOREIGN KEY (document_id) REFERENCES documents(id)
);

CREATE INDEX idx_name ON tags("name");