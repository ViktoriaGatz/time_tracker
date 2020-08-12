package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.entity.user.User;
import ru.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис для репозитория пользователей
 * @author ViktoriaGatz
 * @version 1.0
 */
@Service
public class UserServiceImpl{

    /** Поле для репозитория пользователей */
    private UserRepository userRepository;

    /**
     * Конструктор - создание нового объекта UserServiceImpl
     * @param userRepository - репозиторий пользователя
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод для сохранения пользователя
     * @param user - пользователь - кандидат на сохранение
     * @return - сохранённый пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Поиск пользователя по идентификатору
     * @param id - идентификатор пользователя
     * @return - найденный пользователь
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Поиск всех пользователей в БД
     * @return - список найденных пользователей
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Удаление пользователя по идентификатору
     * @param id - идентификатор пользователя
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Удаление информации о пользователе треккера
     * @param id - идентификатор пользователя
     */
    public void deleteInfoForUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) byId.get().setTask_list(null);
    }

}
