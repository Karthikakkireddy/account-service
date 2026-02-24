package com.karthik.account.domain;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalTime;

@Data
@MappedSuperclass
public class BaseEntity
{

    @Column(name = "created_at", updatable = false)
    private LocalTime createdAt;
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at", updatable = false)
    private LocalTime updatedAt;

    @Column(name = "updated_by", updatable = false)
    private String updatedBy;
}
