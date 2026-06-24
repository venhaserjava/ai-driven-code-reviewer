package com.reviewer.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "reviews")
public class Review extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "repository_name")
    public String repositoryName;

    @Column(name = "pull_request_id")
    public Long pullRequestId;

    @Column(name = "file_path")
    public String filePath;

    @Column(name = "code_content", columnDefinition = "TEXT")
    public String codeContent;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    public String aiFeedback;

    public String status;
}