package ru.example.service;

import ru.example.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User save(User user);

    public Optional<User> findById(Long id);

    public List<User> findAll();
}
