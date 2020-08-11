package ru.example.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.example.entity.task.Task;
import ru.example.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task startTime(Task task) {
        task.startTime();
        return taskRepository.save(task);
    }

    public Task stopTime(Task task) {
        task.stopTime();
        return taskRepository.save(task);
    }

    public void delete_task_info_TIME_LIMIT() {
        List<Task> taskList = taskRepository.findAll();
        for (Task task : taskList) {
            Date tmpDate = new Date(new Date().getTime() - task.getDate_add_task().getTime());
            if (tmpDate.getTime() > new Date(30000).getTime()) {
//                log.info("Delete task + '" + task.getTask_id() + "', date_add_task = " + task.getDate_add_task().toString());
                taskRepository.deleteById(task.getTask_id());
            }
        }
    }

}
