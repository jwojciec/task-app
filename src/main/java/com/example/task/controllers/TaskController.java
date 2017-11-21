package com.example.task.controllers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.datasources.SimpleDataSource;
import com.example.task.model.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final SimpleDataSource simpleDataSource;

    @Autowired
    public TaskController(SimpleDataSource simpleDataSource) {
        this.simpleDataSource = simpleDataSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllTasks() {
        return ResponseEntity.ok(simpleDataSource.getAllTasks());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getTask(@PathVariable("id") String id) {
        return ResponseEntity.ok(simpleDataSource.getTask(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insertTask(@RequestBody Task task) {
        try {
            simpleDataSource.insertTask(task);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateTask(@PathVariable("id") String id, @RequestBody Task task) {
        try {
            simpleDataSource.updateTask(id, task);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable("id") String id) {
        try {
            simpleDataSource.deleteTask(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
