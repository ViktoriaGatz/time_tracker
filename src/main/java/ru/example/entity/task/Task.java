package ru.example.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import ru.example.entity.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Класс для одной из задач
 * @author ViktoriaGatz
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Task {

    /**
     * Поле для логирования
     */
    private static final Logger log = Logger.getLogger(Task.class.getName());

    /**
     * Поле для определения хозяина задачи
     */
    @JsonIgnoreProperties(value = "task_list", allowSetters = true)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User masterUser;

    /**
     * Поле идентификатора задачи
     */
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "task_id"), /* user_sequence*/
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"), /* Начинаем с "1" */
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1") /* Шаг = "1" */
            }
    )
    private long task_id;

    /**
     * Заголовок задачи
     */
    @Column(name = "title")
    private String title = "title";

    /**
     * Описание задачи
     */
    @Column(name = "description")
    private String description = "description";

    /**
     * Дата добавления задачи в треккер
     */
    @Column(name = "date_add_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_add_task = new Date();

    /**
     * Дата начала выполнения задачи
     */
    @Column(name = "date_start_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_start_task;

    /**
     * Дата остановки выполнения задачи
     */
    @Column(name = "date_stop_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_stop_task;

    /**
     * Время, затраченное на выполнение задачи
     */
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;

    /**
     * Конструктор - создание нового объекта Task
     *
     * @param title - заголовок задачи
     */
    public Task(String title) {
        this.title = title;
    }

    /**
     * Конструктор - создание нового объекта Task
     *
     * @param title       - заголовок задачи
     * @param description - описание задачи
     */
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Конструктор - создание нового объекта Task
     *
     * @param task_id - идентификатор задачи
     * @param title   - заголовок задачи
     */
    public Task(long task_id, String title) {
        this.task_id = task_id;
        this.title = title;
    }

    /**
     * Конструктор - создание нового объекта Task
     *
     * @param masterUser  - хозяин задачи
     * @param title       - заголовок задачи
     * @param description - описание задачи
     */
    public Task(User masterUser, String title, String description) {
        this.masterUser = masterUser;
        this.title = title;
        this.description = description;
    }

    /**
     * Конструктор - создание нового объекта Task
     *
     * @param user  - хозяин задачи
     * @param id    - идентификатор задачи
     * @param title - заголовок задачи
     * @param desc  - описание задачи
     * @param time  - время, затраченное на выполнение задачи
     */
    public Task(User user, long id, String title, String desc, Date time) {
        this.masterUser = user;
        this.task_id = id;
        this.title = title;
        this.description = desc;
        this.time = time;
    }

    /**
     * Метод для старта выполнения задачи
     */
    public void startTime() {
        date_start_task = new Date();
        log.info("Task '" + getTitle() + "' start in " + date_start_task.toString());
    }

    /**
     * Метод для остановки выполнения задачи
     */
    public void stopTime() {
        date_stop_task = new Date();
        if (Objects.isNull(date_start_task)) return;
        time = new Date(date_stop_task.getTime() - date_start_task.getTime());
        log.info("Task '" + getTitle() + "' stop in " + date_stop_task.toString() + " time = " + time.toString());
    }

    /**
     * Метод для вывода описания задачи
     * @return - строка с описанием задачи
     */
    @Override
    public String toString() {
        return Objects.isNull(masterUser)
                ? "title = " + title + " desc = " + description
                : "masterUser_id = " + masterUser.getUser_id() + "title = " + title + " desc = " + description;
    }
}
