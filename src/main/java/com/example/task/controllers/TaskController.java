package com.example.task.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Task> getAllTasks() {
        return simpleDataSource.getAllTasks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable("id") String id) {
        return simpleDataSource.getTask(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Task insertTask(@RequestBody Task task) {
        simpleDataSource.insertTask(task);
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Task updateTask(@PathVariable("id") String id, @RequestBody Task task) {
        simpleDataSource.updateTask(id, task);
        return task;
    }
}
