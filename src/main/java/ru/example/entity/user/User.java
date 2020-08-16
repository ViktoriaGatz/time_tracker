package ru.example.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.example.entity.WorkTime;
import ru.example.entity.task.Task;

import javax.persistence.*;
import java.util.*;

/**
 * Класс для сущности Пользователь
 * @author ViktoriaGatz
 * @version 1.0
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class User {

    /** Поле идентификатора пользователя */
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

    /** Фамилия пользователя */
    @Column(name = "fio")
    private String fio;

    // @Transient // указывает, что свойство не нужно записывать

    /** Список задач пользователя */
    // @JsonIgnoreProperties - для того, чтобы избежать зацикливания при выводе
    @JsonIgnoreProperties(value = "masterUser", allowSetters = true)
    @OneToMany (mappedBy="masterUser", fetch = FetchType.EAGER)
    private List<Task> task_list = new ArrayList<>();

    /**
     * Конструктор - создание нового объекта User
     * @param user_id - идентификатор пользователя
     * @param fio - фамилия пользователя
     */
    public User(Long user_id, String fio) {
        this.user_id = user_id;
        this.fio = fio;
    }

    /**
     * Конструктор - создание нового объекта User
     * @param fio - фамилия пользователя
     */
    public User(String fio) {
        this.fio = fio;
    }

    /**
     * Конструктор - создание нового объекта User
     * @param user_id - идентификатор пользователя
     */
    public User(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * Метод для добавления задачи пользователю
     * @param title - заголовок новой задачи
     * @param desc - описание новой задачи
     */
    public void addTask(String title, String desc) {
        task_list.add(new Task(title, desc));
    }


    /**
     * Метод для добавления задачи пользователю
     * @param task - добавляемая задачи
     */
    public void addTask(Task task) {
        task_list.add(task);
    }

    /**
     * Метод для вывод списка задач (сортировка по времени добавления задачи в трекер)
     * @return - список найденных задач
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

    /**
     * Метод для вывода информации о задачах пользователя за период N..M
     * @param id - идентификатор пользователя
     * @param date1 - дата, с которой начинается поиск
     * @param date2 - дата, по которую идёт поиск
     * @return - список найденных задач
     */
    public List<Task> time_for_user(Long id, Date date1, Date date2) {
        List<Task> responseList = new ArrayList<>();
        for (Task task : task_list) {
            if (task.getDate_start_task().after(date1) && task.getDate_start_task().before(date2)) {
                responseList.add(task);
            }
        }
        return responseList;
    }

    /**
     * Метод для вывода информации о трудозатратах пользователя за период N..M
     * @param id - идентификатор пользователя
     * @param date1 - дата, с которой начинается поиск
     * @param date2 - дата, по которую идёт поиск
     * @return - список найденных простоев
     */
    public LinkedList<WorkTime> view_work_time_for_user(Long id, Date date1, Date date2) {
        LinkedList<WorkTime> responseList = new LinkedList<>();

        for (Task task : task_list) {
            if (task.getDate_start_task().after(date1) && task.getDate_start_task().before(date2)) {
                responseList.add(new WorkTime(task.getTitle(), task.getTime()));
            }
        }
        return responseList;
    }

}