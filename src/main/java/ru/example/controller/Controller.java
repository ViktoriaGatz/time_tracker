package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.TaskServiceImpl;
import ru.example.service.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Класс-контроллер с основными REST запросами
 * @author ViktoriaGatz
 * @version 1.0
 */
@RestController
@RequestMapping("/")
public class Controller {

    /** Поле для сервиса задач */
    private TaskServiceImpl taskServiceImpl;

    /** Поле для сервиса пользователей */
    private UserServiceImpl userServiceImpl;

    /** Конструктор - создание объекта Controller */
    @Autowired
    public Controller(TaskServiceImpl taskServiceImpl, UserServiceImpl userServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Get метод для создания новой задачи для пользователя
     * @param user_id - идентификатор пользователя, для которого создаётся задача
     * @param title - заголовок для задачи
     * @param desc - описание задачи
     * @return тело ответа - сохранённая задача (MediaType.APPLICATION_JSON), либо Страница 404
     */
    @GetMapping("/add_task_simple/user_id={user_id}/title={title}/desc={desc}")
    public ResponseEntity add_task_simple(@PathVariable Long user_id, @PathVariable String title, @PathVariable String desc) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        return !byId.isPresent()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(taskServiceImpl.save(new Task(byId.get(), title, desc)));
    }

    /**
     * Post метод для сохранения новой задачи
     * @param task - кандидат на сохранение (новая задача)
     * @return тело ответа - сохранённая задача (MediaType.APPLICATION_JSON)
     */
    @PostMapping("/saveTask")
    public ResponseEntity saveTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskServiceImpl.save(task));
    }

    /**
     * Get метод поиска задачи по идентификатору
     * @param id - идентификатор искомой задачи
     * @return тело ответа - найденная задача в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404
     */
    @GetMapping("/task_id={task_id}")
    public ResponseEntity findTaskById(@PathVariable Long id) {
        Optional<Task> byId = taskServiceImpl.findById(id);
        return !byId.isPresent()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(Optional.of(byId));

    }

    /**
     * Get метод для отображения списка всех задач
     * @return тело ответа - список найденных задач
     */
    @GetMapping("/show_task")
    public ResponseEntity findAllTask() {
        return ResponseEntity.ok(taskServiceImpl.findAll());
    }

    /**
     * Get метод для запуска задачи
     * @param task_id - идентификатор задачи, которую нужно запустить
     * @return тело ответа - запущенная задача в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404
     */
    @GetMapping("/start_task/task_id={task_id}")
    public ResponseEntity start_task(@PathVariable Long task_id) {
        Optional<Task> byId = taskServiceImpl.findById(task_id);
        return !byId.isPresent()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(taskServiceImpl.startTime(byId.get()));
    }

    /**
     * Get метод для остановки задачи
     * @param task_id - идентификатор задачи, которую нужно остановить
     * @return тело ответа - остановленная задача в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404
     */
    @GetMapping("/stop_task/task_id={task_id}")
    public ResponseEntity stop_task(@PathVariable Long task_id) {
        Optional<Task> byId = taskServiceImpl.findById(task_id);
        return !byId.isPresent()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(taskServiceImpl.stopTime(byId.get()));
    }

    /**
     * Get метод для добавления пользователя треккера
     * @param fio - ФИО пользователя
     * @return тело ответа - добавленный пользователь (MediaType.APPLICATION_JSON)
     */
    @GetMapping("/add/fio={fio}")
    public ResponseEntity addUser(@PathVariable String fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(fio)));
    }

    /**
     * Get метод для редактирования информации о пользоваетеле
     * @param id - идентификатор пользователя, данные о котором будут изменены
     * @param new_fio - новые ФИО
     * @return тело ответа - изменённый пользователь или новый пользователь (MediaType.APPLICATION_JSON)
     */
    @GetMapping("/edit/id={id}/new_fio={new_fio}")
    public ResponseEntity editUser(@PathVariable Long id, @PathVariable String new_fio) {
        return ResponseEntity.ok(userServiceImpl.save(new User(id, new_fio)));
    }

    /**
     * Post метод для сохранения нового пользователя треккинга
     * @param user - кандидат на сохранение (новый пользователь)
     * @return тело ответа - сохранённый пользователь (MediaType.APPLICATION_JSON)
     */
    @PostMapping("/saveUser")
    public ResponseEntity saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.save(user));
    }

    /**
     * Get метод для поиска пользователя по идентификатору
     * @param user_id - идентификатор искомого пользователя
     * @return тело ответа - найденный пользователь в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404
     */
    @GetMapping("/user_id={user_id}")
    public ResponseEntity findUserById(@PathVariable Long user_id) {
        Optional<User> byId = userServiceImpl.findById(user_id);
        return !byId.isPresent()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(Optional.of(byId));
    }

    /**
     * Get метод для вывода всех пользователей треккинга
     * @return тело ответа - список пользователей (MediaType.APPLICATION_JSON)
     */
    @GetMapping("/show_user")
    public ResponseEntity findAllUser() {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }

    // Не завершено
    /**
     * Показать все трудозатраты пользователя за период N..M
     * @param user_id - идентификатор искомого пользователя
     * @param date1 - дата с которой начинается поиск
     * @param date2 - дата по которуюю идёт поиск
     * @return тело ответа - список задач (сортировка по времени поступления в треккер)
     */
    @GetMapping("/work_time_for_user/user_id={user_id}/from={date1}/to={date2}")
    public ResponseEntity work_time_for_user(@PathVariable Long user_id, @PathVariable Date date1, @PathVariable Date date2) {
        return ResponseEntity.ok(userServiceImpl.view_work_time_for_user(user_id, date1, date2));
    }

    /**
     * Показать все трудозатраты пользователя за период N..M
     * @param user_id - идентификатор искомого пользователя
     * @param date1 - дата с которой начинается поиск
     * @param date2 - дата по которуюю идёт поиск
     * @return тело ответа - список задач (сортировка по времени поступления в треккер)
     */
    @GetMapping("/no_work_time_for_user={user_id}/from={date1}/to={date2}")
    public ResponseEntity not_work_time_for_user(@PathVariable Long user_id, @PathVariable Date date1, @PathVariable Date date2) {
        return ResponseEntity.ok(userServiceImpl.view_not_work_time_for_user(user_id, date1, date2));
    }

    /**
     * Get метод для удаления пользователя
     * @param user_id - идентификатор пользователя, которого нужно удалить
     * @return тело ответа - сообщение об удалении пользователя
     */
    @GetMapping("/delete/user_id={user_id}")
    public ResponseEntity delete_user_by_id(@PathVariable Long user_id) {
        userServiceImpl.deleteById(user_id);
        return ResponseEntity.ok("User with id = " + user_id + " delete!");
    }

    /**
     * Get метод для удаления задач у пользователя треккера
     * @param user_id - идентификатор пользователя
     * @return тело ответа - сообщение о том, что данные треккинга пользователя удалены
     */
    @GetMapping("/delete_info_for_user/user_id={user_id}")
    public ResponseEntity delete_info_for_user_by_id(@PathVariable Long user_id) {
        userServiceImpl.deleteInfoForUser(user_id);
        return ResponseEntity.ok("Task info about user with id = " + user_id + " delete!");
    }
}
