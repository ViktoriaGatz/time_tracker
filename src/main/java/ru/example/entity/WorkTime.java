package ru.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Доп. класс для упрощения вывода задач
 * @author ViktoriaGatz
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkTime {

    /** Поле заголовка */
    private String title;

    /** Поле времени */
    private Date time;

}
