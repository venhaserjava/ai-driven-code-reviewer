-- Substitua o conteúdo do arquivo V1.0.0 pelo DDL abaixo:
CREATE TABLE public.reviews (
    id BIGSERIAL NOT NULL,
    repository_name VARCHAR(255),
    pull_request_id BIGINT,
    file_path VARCHAR(255),
    code_content TEXT,
    ai_feedback TEXT,
    status VARCHAR(50),
    CONSTRAINT reviews_pkey PRIMARY KEY (id)
);
