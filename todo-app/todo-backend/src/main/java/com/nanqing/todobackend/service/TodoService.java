package com.nanqing.todobackend.service;

import com.nanqing.todobackend.entity.Todo;
import com.nanqing.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(String content) {
        Todo todo = new Todo();
        todo.setContent(content);
        return todoRepository.save(todo);
    }
}
