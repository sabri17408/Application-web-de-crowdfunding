package com.springboot.model;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *      Spring Data provides sophisticated support to transparently keep
 *      track of who created or changed an entity and when the change happened.
 *      - MappedSuperclass:
 *          This class will be mapped by audible subclasses.
 *          Allow common mappings to be defined for its subclasses.
 *      - EntityListeners:
 *          To specify callback listener classes,
 *          which we use to register our AuditingEntityListener class.
 *      - AuditingEntityListener:
 *          Contains the callback methods (@PrePersist and @PreUpdate annotations),
 *          which will be used to persist and update these properties
 *          when we will persist or update our entity
 */


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    

    @CreatedDate
    private LocalDateTime creationDate = LocalDateTime.now();

    

   

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    

    
}