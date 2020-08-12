package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.task.Task;

/**
 * Класс-репозиторий для сущности Task (задачи)
 * @author ViktoriaGatz
 * @version 1.0
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
