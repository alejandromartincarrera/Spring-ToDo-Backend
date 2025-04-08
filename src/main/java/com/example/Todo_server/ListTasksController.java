package com.example.Todo_server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ListTasksController {

    @Value("${GOOGLE_MAPS_API_KEY}")
    private String GOOGLE_MAPS_API_KEY;


    private final ListTasksRepository repository;

    ListTasksController(ListTasksRepository repository){
        this.repository=repository;
    }

    @GetMapping("/{id}/lists")
    List<ListTasks> all(@PathVariable String id) {
        List<ListTasks> list = repository.findByUserId(id);
        return list;
    }

    @PostMapping("/{id}/lists")
    ListTasks create(@PathVariable String id, @RequestBody NamePost namePost) {
        ListTasks listTasks = new ListTasks(id, namePost.getName());
        return repository.save(listTasks);
    }

    @GetMapping("/{id}/listName/{name}")
    ResponseEntity<ListTasks> findName(@PathVariable String id, @PathVariable String name) {
        ListTasks listTasks = repository.findByUserIdAndName(id, name);
        if (listTasks==null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(listTasks);
        }
    }

    @GetMapping("/{id}/lists/{listId}/items")
    ResponseEntity<List<Item>> getItems(@PathVariable String id, @PathVariable String listId) {
        Optional<ListTasks>  result = repository.findById(listId);
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(result.get().getItems());
        }
    }

    @PostMapping("/{id}/lists/{listId}/items")
    ResponseEntity<ListTasks> addItem(@PathVariable String id, @PathVariable String listId, @RequestBody ItemPost itemPost){
        Item item = new Item(itemPost.getItem());
        Optional<ListTasks> result = repository.findById(listId);
        if (result.isPresent()) {
            ListTasks listTasks = result.get();
            listTasks.getItems().add(item);
            return ResponseEntity.ok(repository.save(listTasks));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/lists/{listId}/items/{item}")
    void deleteItem(@PathVariable String id, @PathVariable String listId, @PathVariable String item) {
        Optional<ListTasks> result = repository.findById(listId);
        if (result.isPresent()){
            ListTasks listTasks = result.get();
            listTasks.getItems().removeIf(itemEl -> itemEl.getName().equals(item));
            repository.save(listTasks);
        }
    }

    @DeleteMapping("/{id}/lists/{listId}")
    void deleteList(@PathVariable String id, @PathVariable String listId){
        Optional<ListTasks> listTasks = repository.findById(listId);
        if (listTasks.isPresent()){
            ListTasks listTasksCasted = listTasks.get();
            repository.deleteById(listTasksCasted.getId());
        }
    }

    @GetMapping("/places/{lat}/{lon}")
    ResponseEntity<String> sendData(@PathVariable String lat, @PathVariable String lon){
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius=1500&key="+GOOGLE_MAPS_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response;
    };

    @GetMapping("/{id}/items/{item}")
    ResponseEntity<ListTasks> getItem(@PathVariable String id, @PathVariable String item){
        ListTasks list = repository.findByUserIdAndItemName(id, item);
        if (list==null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(list);
        }
    }
    //comment 2 new-branch



}
