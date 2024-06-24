package com.program.app.controller;

import com.program.app.model.Todo;
import com.program.app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService =  todoService;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @GetMapping
    public Iterable<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodoById(@PathVariable Long id){
        return todoService.getTodoById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id ){
        todoService.deleteTodo(id);
    }

    @PatchMapping("/{id}/complete")
    public void markAsCompleted(@PathVariable Long id){
        todoService.markAsCompleted(id);
    }

}
