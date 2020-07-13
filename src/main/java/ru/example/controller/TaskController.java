package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.TaskServiceImpl;
import ru.example.service.UserServiceImpl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskServiceImpl taskServiceImpl;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl, UserServiceImpl userServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/add_task/user_id={user_id}/fio={fio}/task_id={task_id}/title={title}/desc={desc}")
    public ResponseEntity add_task(@PathVariable Long user_id, @PathVariable String fio, @PathVariable Long task_id, @PathVariable String title, @PathVariable String desc) {
        return ResponseEntity.ok(taskServiceImpl.save(new Task(new User(user_id, fio), task_id, title, desc, new Date(), 0L, 0L)));
    }

    @GetMapping("/add_task_simple/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task_simple(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(taskServiceImpl.save(new Task(byId.get(), title, desc)));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Task task) {
        return ResponseEntity.ok(taskServiceImpl.save(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Task> byId = taskServiceImpl.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);

    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(taskServiceImpl.findAll());
    }
}
