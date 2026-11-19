package com.sentana.job.author;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Author implements Serializable {

    @Column(name = "created_at", nullable = false, updatable = false)
    private final Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;
}
