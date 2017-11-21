package com.example.task.datasources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Component;

import com.example.task.model.Task;

@Component
public class SimpleDataSource {
    private static List<Task> taskList = new ArrayList<Task>() {{
        add(new Task("cd502cc3-99f8-4921-a66f-61f424937ebd", "zmywanie", "pozmywac naczynia"));
        add(new Task("91c9a6dc-92c6-4c81-a874-595e0b47cb25", "odkurzanie", "odkurzyc duzy pokoj"));
    }};

    public Task getTask(String id) {
        return taskList.stream()
            .filter(task -> task.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(String.format("Task with id: [%s] not found", id)));
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public Task insertTask(Task task) {
        taskList.stream()
            .filter(tsk -> tsk.getId().equals(task.getId()))
            .findAny()
            .ifPresent(o -> {
                throw new BadRequestException(String.format("Task with id: [%s] already exists", task.getId()));
            });

        taskList.add(task);
        return task;
    }

    public void updateTask(String id, Task task) {
        taskList.stream()
            .filter(tsk -> tsk.getId().equals(id))
            .findFirst()
            .map(obj -> taskList.set(taskList.indexOf(obj), task))
            .orElseThrow(() -> new NotFoundException(String.format("Task with id: [%s] not found", id)));
    }

    public void deleteTask(String id) {
        taskList.stream()
            .filter(tsk -> tsk.getId().equals(id))
            .findFirst()
            .map(obj -> taskList.remove(obj))
            .orElseThrow(() -> new NotFoundException(String.format("Task with id: [%s] not found", id)));
    }
}
