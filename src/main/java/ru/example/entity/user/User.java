package ru.example.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.example.entity.task.Task;

import javax.persistence.*;
import java.util.List;

// @Table (name = "UserTable")
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
    private List<Task> task_list;

    public User(Long user_id, String fio) {
        this.user_id = user_id;
        this.fio = fio;
    }

    public void addTask(String title, String desc) {
        task_list.add(new Task(title, desc));
    }
}