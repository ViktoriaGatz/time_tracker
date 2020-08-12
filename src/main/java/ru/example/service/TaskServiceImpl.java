package ru.example.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.example.entity.task.Task;
import ru.example.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис для репозитория задач
 * @author ViktoriaGatz
 * @version 1.0
 */
@Service
public class TaskServiceImpl {

    /** Поле репозитория задач */
    private TaskRepository taskRepository;

    /** Конструктор - создание нового объекта TaskServiceImpl */
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Метод для сохранения новой задачи
     * @param task - сохраняемая задача
     * @return - сохранйнная задача
     */
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Метод для поиска задачи по её идентификатору
     * @param id - идентификатор задачи
     * @return - найденная задача
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Метод для поиска всех задач
     * @return - списко найденных задач
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Метод для удаления задачи по её идентификатору
     * @param id - идентификатор удаляемой задачи
     */
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Метод для начала отсчёта времени по задаче
     * @param task - задача, которая должна запуститься
     * @return - запущенная задача
     */
    public Task startTime(Task task) {
        task.startTime();
        return taskRepository.save(task);
    }

    /**
     * Метод для завершения отсчёта времени по задаче
     * @param task - задача, которая должна быть остановленна
     * @return - остановленная задача
     */
    public Task stopTime(Task task) {
        task.stopTime();
        return taskRepository.save(task);
    }

    /**
     * Метод для удаления информации о задачах (запускается по времени)
     * @param l - лонг, обозначающий промежуток времени, через который информация будет удалена
     */
    public void delete_task_info_TIME_LIMIT(Long l) {
        List<Task> taskList = taskRepository.findAll();
        for (Task task : taskList) {
            Date tmpDate = new Date(new Date().getTime() - task.getDate_add_task().getTime());
            if (tmpDate.getTime() > new Date(l).getTime()) {
                taskRepository.deleteById(task.getTask_id());
            }
        }
    }

}
