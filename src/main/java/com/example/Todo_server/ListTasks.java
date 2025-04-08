package com.example.Todo_server;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document
public class ListTasks {

    @Id private String id;
    
    @NotNull(message= "userID is required")
    private String userId;
    @NotNull(message= "name is required")
    private String name;
    private List<Item> items = new ArrayList<>();

    public ListTasks() {}

    public ListTasks(String userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public ListTasks(String userId, String name, List<Item> items){
        this.userId = userId;
        this.name = name;
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
