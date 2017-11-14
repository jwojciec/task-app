package com.example.task.datasources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Component;

import com.example.task.model.Task;

@Component
public class SimpleDataSource {
    private static List<Task> taskList = new ArrayList<Task>() {{
        add(new Task(generateUUID(), "zmywanie", "pozmywac naczynia"));
        add(new Task(generateUUID(), "odkurzanie", "odkurzyc duzy pokoj"));
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

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
