package com.example.task.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.task.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
