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
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Task {
    private static final Logger log = Logger.getLogger(Task.class.getName());

    @JsonIgnoreProperties(value = "task_list", allowSetters = true)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User masterUser;

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

    @Column(name = "title")
    private String title = "title";

    @Column(name = "description")
    private String description = "description";

    @Column(name = "date_add_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_add_task = new Date();

    @Column(name = "date_start_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_start_task;

    @Column(name = "date_stop_task")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_stop_task;

    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;

    public Task(String title) {
        this.title = title;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(long task_id, String title) {
        this.task_id = task_id;
        this.title = title;
    }

    public Task(User masterUser, String title, String description) {
        this.masterUser = masterUser;
        this.title = title;
        this.description = description;
    }

    public void startTime() {
        date_start_task = new Date();
        log.info("Task '" + getTitle() + "' start in " + date_start_task.toString());
    }

    public void stopTime() {
        date_stop_task = new Date();
        if (Objects.isNull(date_start_task)) return;
        time = new Date(date_stop_task.getTime() - date_start_task.getTime());
        log.info("Task '" + getTitle() + "' stop in " + date_stop_task.toString() + " time = " + time.toString());
    }

    @Override
    public String toString() {
        return Objects.isNull(masterUser)
                ? "title = " + title + " desc = " + description
                : "masterUser_id = " + masterUser.getUser_id() + "title = " + title + " desc = " + description;
    }

}
