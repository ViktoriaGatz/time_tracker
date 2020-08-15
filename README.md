# Time tracker

Данное приложение - это backend сервиса **Многопользовательский тайм трекер**, доступ к которому можно получить по REST.

## Основные запросы

1. Создать пользователя трекинга

    Post метод для сохранения нового пользователя треккинга
    
    @param user - кандидат на сохранение (новый пользователь)
    @return тело ответа - сохранённый пользователь (MediaType.APPLICATION_JSON)
    
    ````$ curl -H 'Content-Type:application/json' -d '{"user_id":4,"fio":"fio","task_list":[]}' 'localhost:8080/saveUser'````
    
    Get метод для добавления пользователя треккера

    @param fio - ФИО пользователя
    @return тело ответа - добавленный пользователь (MediaType.APPLICATION_JSON)

    ````$ curl 'localhost:8080/add/fio=fio'````


2. Добавить задачу пользователю
     
    Get метод для создания новой задачи для пользователя
     
    @param user_id - идентификатор пользователя, для которого создаётся задача
    @param title   - заголовок для задачи
    @param desc    - описание задачи
    @return тело ответа - сохранённая задача (MediaType.APPLICATION_JSON), либо Страница 404
     
    ````$ curl 'localhost:8080/add_task_simple/user_id=1/title=title/desc=desc'````
     
    Post метод для сохранения новой задачи

    @param task - кандидат на сохранение (новая задача)
    @return тело ответа - сохранённая задача (MediaType.APPLICATION_JSON)
    
    ````$ curl -H 'Content-Type:application/json' -d '{"task_id":1,"date_add_task":null,"date_start_task":null,"date_stop_task":null,"description":"description","time":null,"title":"title"}' 'localhost:8080/saveTask'````
2. изменить данные пользователя
3. начать отсчет времени по задаче Х
4. прекратить отсчет времени по задаче Х
5. показать все трудозатраты пользователя Y за период N..M в виде связного списка Задача - Сумма затраченного времени в виде (чч:мм), сортировка по времени поступления в трекер (для ответа на вопрос, на какие задачи я потратил больше времени)
6. показать все временные интервалы занятые работой за период N..M в виде связного списка Задача - Временной интервал (число чч:мм) (для ответа на вопрос, где за прошедшую неделю были ‘дыры’, когда я ничего не делал)
7. показать сумму трудозатрат по всем задачам пользователя Y за период N..M
8. удалить всю информацию о пользователе Z
9. очистить данные трекинга пользователя Z

## Используемые версии (перед запуском)

* Ubuntu 18.04.4 LTS
* openjdk версии "1.8.0_162"
* Среда выполнения OpenJDK (сборка 1.8.0_162-8u162-b12-1-b12)
* 64-разрядная серверная виртуальная машина OpenJDK (сборка 25.162-b12, смешанный режим)
* mySQL 5.7

## Процедура сборки и запуска

После установки всех необходимых приложений, необходимо:
1. Зайти в консоль mySQL из под root

    $ sudo mysql -u root
2. Создать пользователя БД

    $ CREATE USER 'user_name'@'localhost' IDENTIFIED BY 'password';
3. Чтобы назначить созданном пользователю неограниченные права доступа к базе данных, выполните следующую команду

    $ GRANT ALL PRIVILEGES ON * . * TO 'user_name'@'localhost';
4. Проверьте результат ещё раз войдя в mysql-консоль

    $ mysql -u user_name -p
5. Создайте новую БД

    $ CREATE DATABASES 'time_tracker'
    
**(не следует созадавать новую БД под root, т.к. в последних версиях mySQL для root пользователя используется не пароль а auth_socket)**  

Если вы всё делали по плану, то файл src/main/resources/application.yml нужно будет привести к следующему виду:

````
server:
  port: 8080
spring:
  thymeleaf:
    prefix: file:src/main/resources/templates/
    cache: false
  jpa:
    hibernate:
      ddl-auto: create
  datasource:
    url: jdbc:mysql://localhost:3306/time_tracker
    username: user_name
    password: password
    
````

7. Проект собирается с помощью gradle командной

    $ gradle build -x test
8. Запуск проекта

    $ gradle bootRun
