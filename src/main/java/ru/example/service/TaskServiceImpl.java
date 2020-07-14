package ru.example.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.been.AppContext;
import ru.example.entity.task.Task;
import ru.example.repository.TaskRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger log = Logger.getLogger(AppContext.class.getName());

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

    @Override
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
                log.info("Delete task + '" + task.getTitle() + "', date_add_task = " + task.getDate_add_task().toString());
                taskRepository.delete(task);
            }
        }
    }

}
