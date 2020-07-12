package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.TaskService;
import ru.example.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add_task/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> UserById = userService.findById(user_id);
        if (Objects.isNull(UserById))
            return ResponseEntity.notFound().build();
        else {
            UserById.get().addTask(title, desc);
            return ResponseEntity.ok(UserById);
        }
    }

    @GetMapping("/edit_title_by_id_task/id={id}/new_title={new_title}")
    public ResponseEntity edit_title_by_id_task(@PathVariable Long id, @PathVariable String new_title) {
        return ResponseEntity.ok(taskService.save(new Task(id, new_title)));
    }

    // @GetMapping("/edit_desk_by_id_task/id={id}/new_desc={new_desc}")
    // public ResponseEntity edit_desc_by_id_task(@PathVariable Long id, @PathVariable String new_desc) {
    //     return ResponseEntity.ok(taskService.save(new Task(id, new_desc)));
    // }

    @PostMapping
    public ResponseEntity save(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Task> byId = taskService.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);

    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }
}
