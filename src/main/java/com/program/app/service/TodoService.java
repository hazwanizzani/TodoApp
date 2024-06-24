package com.program.app.service;

import com.program.app.model.Todo;
import com.program.app.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    private TodoRepository repository;

    @Autowired
    public void setRepository(TodoRepository repository) {
        this.repository = repository;
    }

    private NotificationService notificationService;

    @Autowired
    @Qualifier("emailNotificationService")
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Transactional
    public Todo createTodo(Todo todo) {
        notificationService.notifyCreation("Todo object is created: " + todo.getTitle());
        return repository.save(todo);

    }

    @Transactional
    public Todo updateTodo(Long id, Todo todo) {
        Optional<Todo> todoOptional = repository.findById(id);
        if (todoOptional.isPresent()) {
            Todo todoList = todoOptional.get();
            todoList.setTitle(todo.getTitle());
            todoList.setDescription(todo.getDescription());
            todoList.setCompleted(todo.getCompleted());
            todoList.setDueDate(todo.getDueDate());
            return repository.save(todoList);
        } else {
            throw new RuntimeException("Todo Object is not found with id " + id);
        }
    }

    @Transactional
    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }

    public Iterable<Todo> getAllTodos(){
        return repository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public void markAsCompleted(Long id) {
        Optional<Todo> todoOptional = repository.findById(id);
        if (todoOptional.isPresent()) {
            Todo todoList = todoOptional.get();
            todoList.setCompleted(true);
            repository.save(todoList);
            notificationService.notifyCompletion("Todo object is completed: " + todoList.getTitle());
        } else {
            throw new RuntimeException("Todo Object is not found");
        }
    }


}
