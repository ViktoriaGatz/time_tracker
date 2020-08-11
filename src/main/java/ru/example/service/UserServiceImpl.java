package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.entity.user.User;
import ru.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteInfoForUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        byId.ifPresent(user -> user.setTask_list(null));
    }

}
