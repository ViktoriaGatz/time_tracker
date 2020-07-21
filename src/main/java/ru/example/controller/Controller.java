package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.TaskServiceImpl;
import ru.example.service.UserServiceImpl;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class Controller {

    private TaskServiceImpl taskServiceImpl;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public Controller(TaskServiceImpl taskServiceImpl, UserServiceImpl userServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/add_task_simple/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task_simple(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(taskServiceImpl.save(new Task(byId.get(), title, desc)));
    }

    @PostMapping("/saveTask")
    public ResponseEntity saveTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskServiceImpl.save(task));
    }

    @GetMapping("/{task_id}")
    public ResponseEntity findTaskById(@PathVariable Long id) {
        Optional<Task> byId = taskServiceImpl.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);
    }

    @GetMapping("/show_task")
    public ResponseEntity findAllTask() {
        return ResponseEntity.ok(taskServiceImpl.findAll());
    }

    @GetMapping("/start_task/task_id={task_id}")
    public ResponseEntity start_task(@PathVariable Long task_id) {
        Optional<Task> byId = taskServiceImpl.findById(task_id);
        if (Objects.isNull(byId))
            return ResponseEntity.notFound().build();
        else {
            return ResponseEntity.ok(taskServiceImpl.startTime(byId.get()));
        }
    }

    @GetMapping("/stop_task/task_id={task_id}")
    public ResponseEntity stop_task(@PathVariable Long task_id) {
        Optional<Task> byId = taskServiceImpl.findById(task_id);
        if (Objects.isNull(byId))
            return ResponseEntity.notFound().build();
        else {
            return ResponseEntity.ok(taskServiceImpl.stopTime(byId.get()));
        }
    }

    @GetMapping("/add/fio={fio}")
    public ResponseEntity addUser(@PathVariable String fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(fio)));
    }

    @GetMapping("/edit/id={id}/new_fio={new_fio}")
    public ResponseEntity editUser(@PathVariable Long id, @PathVariable String new_fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(id, new_fio)));
    }

    @PostMapping("/saveUser")
    public ResponseEntity saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.save(user));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity findUserById(@PathVariable Long user_id) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);

    }
    @GetMapping("/show_user")
    public ResponseEntity findAllUser() {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }

    @GetMapping("/add_task/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        if (Objects.isNull(byId)) {
            System.out.println("LOL");
            return ResponseEntity.notFound().build();
        } else {
            byId.get().addTask(title, desc);
            userServiceImpl.save(byId.get());
            return ResponseEntity.ok(byId);
        }
    }


}
