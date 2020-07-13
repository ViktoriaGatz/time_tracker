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
@RequestMapping("/task")
public class TaskController {

    private TaskServiceImpl taskServiceImpl;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping("/add_task/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> userById = userServiceImpl.findById(user_id);
        return Objects.isNull(userById)
                ? ResponseEntity.notFound().build()
                // : ResponseEntity.ok(user)
                : ResponseEntity.ok(taskServiceImpl.save(new Task(userById.get(), title, desc)));
    }

    @GetMapping("/edit_title_by_id_task/id={id}/new_title={new_title}")
    public ResponseEntity edit_title_by_id_task(@PathVariable Long id, @PathVariable String new_title) {
        return ResponseEntity.ok(taskServiceImpl.save(new Task(id, new_title)));
    }

    // @GetMapping("/edit_desk_by_id_task/id={id}/new_desc={new_desc}")
    // public ResponseEntity edit_desc_by_id_task(@PathVariable Long id, @PathVariable String new_desc) {
    //     return ResponseEntity.ok(taskService.save(new Task(id, new_desc)));
    // }

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
