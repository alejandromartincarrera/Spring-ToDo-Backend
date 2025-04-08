package com.example.Todo_server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ListTasksRepository extends MongoRepository<ListTasks, String> {
    public List<ListTasks> findByUserId(String userId);
    public ListTasks findByUserIdAndName(String userId,String name);
    @Query("{ 'userId': ?0, 'items.name': ?1 }")
    public ListTasks findByUserIdAndItemName(String userId, String itemName);
}
