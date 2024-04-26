package com.example.artconnect.service.collection;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CollectionDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime creationDate;


    public CollectionDTO(Long id,String name, String description, LocalDateTime creationDate) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;

    }


    public CollectionDTO() {
    }
}
