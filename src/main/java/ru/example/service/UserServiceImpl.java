package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;
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

    /**
     * Метод для вывода информации о задачах пользователя за период N..M
     * @param id - идентификатор пользователя
     * @param date1 - дата, с которой начинается поиск
     * @param date2 - дата, по которую идёт поиск
     * @return - список найденных задач
     */
    public List<Task> view_work_time_for_user(Long id, Date date1, Date date2) {
//        boolean after(Date date) - если объект содержит более позднюю дату, чем указано в параметре date, то возвращается true;
//        boolean before(Date date) - если объект содержит более раннюю дату, чем указано в параметре date, то возвращается true;
//        int compareTo(Date date) - сравнивает даты. Возвращает 0, если совпадают, отрицательное значение - если вызывающая дата более ранняя, положительное значение - если вызывающая дата более поздняя, чем в параметре;
//        boolean equals(Object object) - если даты совпадают, то возвращается true;
//        long getTime() - возвращает количество миллисекунд, прошедших с полуночи 1 января 1970 года;
//        void setTime(long milliseconds) - устанавливает время и дату в виде числа миллисекунд, прошедших с полночи 1 января 1970 года.

    }

    /**
     * Метод для вывода информации о простоях пользователя за период N..M
     * @param id - идентификатор пользователя
     * @param date1 - дата, с которой начинается поиск
     * @param date2 - дата, по которую идёт поиск
     * @return - список найденных простоев
     */
    public List<Task> view_not_work_time_for_user(Long id, Date date1, Date date2) {

    }

}
