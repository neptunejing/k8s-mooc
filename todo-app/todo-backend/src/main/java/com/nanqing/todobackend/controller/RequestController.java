package com.nanqing.todobackend.controller;

import com.nanqing.todobackend.entity.Todo;
import com.nanqing.todobackend.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/todo-api")
public class RequestController {
    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public List<Todo> getTodoList() {
        return todoService.getAllTodos();
    }

    @PostMapping("")
    public void addTodoItem(@RequestBody String todoContent) {
        todoService.addTodo(todoContent);
        log.info("Todo added: {}", todoContent);
    }
}
