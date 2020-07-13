package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.UserServiceImpl;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/add/fio={fio}")
    public ResponseEntity add(@PathVariable String fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(fio)));
    }

    @GetMapping("/edit/id={id}/new_fio={new_fio}")
    public ResponseEntity edit(@PathVariable Long id, @PathVariable String new_fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(id, new_fio)));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> byId = userServiceImpl.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);

    }

    @GetMapping
    public ResponseEntity findAll() {
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