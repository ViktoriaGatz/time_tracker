package ru.example.entity.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.example.entity.user.User;

import javax.persistence.*;

import java.util.Date;

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
    private String title;

    @Column(name = "description")
    private String description;

//    @Column(name = "date_start")
//    @Temporal(TemporalType.DATE)
//    private Date date_start;
//
//    @Column(name = "date_finish")
//    @Temporal(TemporalType.DATE)
//    private Date date_start;

    @Column(name = "date_add_task")
    @Temporal(TemporalType.DATE)
    private Date date_add_task = new Date();

    @Transient
    private Long ollTime = 0L;

    @Transient
    private Long time = 0L;


    public String taskTimeToString() {
        return new Date(time).toString();
    }

    public Task(String title) {
        this.title = title;
    }


    public void startTime() {
        Date dateStart = new Date();
        time = dateStart.getTime();
        log.info("Task " + getTitle() + " start in " + dateStart.toString());
    }

    public void stopTime() {
        Date dateStop = new Date();
        time = dateStop.getTime() - time;
        ollTime += time;
        log.info("Task " + getTitle() + " stop in " + dateStop.toString() + " time over = " + ollTime);
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


}
