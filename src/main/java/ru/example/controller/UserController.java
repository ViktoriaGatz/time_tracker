package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.user.User;
import ru.example.service.UserService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/add/id={id}/fio={fio}")
    public ResponseEntity add(@PathVariable Long id, @PathVariable String fio) {
        return ResponseEntity.ok(userService.save(new User(id, fio)));
    }

    @GetMapping("/edit/id={id}/new_fio={new_fio}")
    public ResponseEntity edit(@PathVariable Long id, @PathVariable String new_fio) {
        return ResponseEntity.ok(userService.save(new User(id, new_fio)));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> byId = userService.findById(id);
        return Objects.isNull(byId)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(byId);

    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}