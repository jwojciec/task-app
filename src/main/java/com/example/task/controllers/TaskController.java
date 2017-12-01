package com.example.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.model.Task;
import com.example.task.repositories.TaskRepository;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    private final TaskRepository repository;

    @Autowired
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok(repository.findOne(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insertTask(@RequestBody Task task) {
        if (StringUtils.isEmpty(task.getName())) {
            return ResponseEntity.badRequest().body("Task name cannot be empty");
        } else {
            repository.save(task);
            return ResponseEntity.ok(task);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable("id") Long id) {
        try {
            repository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Task with id [%d] not found", id));
        }
        return ResponseEntity.status(HttpStatus.GONE).body(String.format("Task with id [%d] deleted", id));
    }
}

