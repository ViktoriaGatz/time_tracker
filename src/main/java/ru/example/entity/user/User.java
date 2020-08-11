package ru.example.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.example.entity.task.Task;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_id"), /* user_sequence*/
                    @Parameter(name = "initial_value", value = "1"), /* Начинаем с "1" */
                    @Parameter(name = "increment_size", value = "1") /* Шаг = "1" */
            }
    )
    private Long user_id;

    @Column(name = "fio")
    private String fio;

    // @Transient // указывает, что свойство не нужно записывать

    // Для того, чтобы избежать зацикливания при выводе
    @JsonIgnoreProperties(value = "masterUser", allowSetters = true)
    @OneToMany (mappedBy="masterUser", fetch = FetchType.EAGER)
    private List<Task> task_list = new ArrayList<>();

    public User(Long user_id, String fio) {
        this.user_id = user_id;
        this.fio = fio;
    }

    public User(String fio) {
        this.fio = fio;
    }

    public User(Long user_id) {
        this.user_id = user_id;
    }

    public void addTask(String title, String desc) {
        task_list.add(new Task(title, desc));
    }

    /*
        Вывод списка задач (сортировка по времени добавления задачи в трекер)
     */
    public String retTimeSpentOnWork() {
        /*
            Сортировка по количеству времени, затраченного на задачу
            Comparator<Task> comparator = Comparator.comparing(obj -> obj.getTime().getTime());
        */
        Comparator<Task> comparator = Comparator.comparing(obj -> obj.getDate_add_task().getTime());
        Collections.sort(task_list, comparator);

        return task_list.toString();
    }

    public void addTask(Task task) {
        task_list.add(task);
    }
}