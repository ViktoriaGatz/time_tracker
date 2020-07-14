package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.repository.TaskRepository;
import ru.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task startTime(Task task) {
        task.startTime();
        return taskRepository.save(task);
    }

    public Task stopTime(Task task) {
        task.stopTime();
        return taskRepository.save(task);
    }
}
