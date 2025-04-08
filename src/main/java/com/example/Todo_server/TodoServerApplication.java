package com.example.Todo_server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoServerApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ListTasksRepository repository) {
		return args -> {
			repository.deleteAll();
			List<Item> items = new ArrayList<>();
			items.add(new Item("item1"));
			repository.save(new ListTasks("1", "list1", items));
			for (ListTasks list: repository.findAll()){
				System.out.println(list);
			}
		};
	}



}
