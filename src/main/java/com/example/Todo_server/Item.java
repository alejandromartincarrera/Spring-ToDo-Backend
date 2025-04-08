package com.example.Todo_server;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;

public class Item {
    @Id private String id;
    @NotNull(message="name is required")
    private String name;

    public Item(String name){
        this.name = name;
    }

    public Item(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
