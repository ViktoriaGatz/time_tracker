package ru.example.service;

import ru.example.entity.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    public Task save(Task task);

    public Optional<Task> findById(Long id);

    public List<Task> findAll();

    public void deleteById(Long id);
}
