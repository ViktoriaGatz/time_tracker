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
3. Изменить данные пользователя                               
    
    @param user_id - идентификатор пользователя                          
    @param fio - новые ФИО пользователя                                               
    @return тело ответа - обновлённый пользователь (MediaType.APPLICATION_JSON)               
    
    ````$ curl 'localhost:8080/update/user_id=1/fio=newfio'````
4. Начать отсчет времени по задаче Х                                        

    @param task_id - идентификатор задачи, которую нужно запустить                                                
    @return тело ответа - запущенная задача в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404                    
    
    ````$curl 'localhost:8080/start_task/task_id=1'````
5. Прекратить отсчет времени по задаче Х                                  
 
    @param task_id - идентификатор задачи, которую нужно остановить                        
    @return тело ответа - остановленная задача в случае успеха (MediaType.APPLICATION_JSON), либо Страница 404                       
    
    ````$ curl 'localhost:8080/stop_task/task_id=1'````
6. Показать все трудозатраты пользователя Y за период N..M в виде связного списка Задача - Сумма затраченного времени в виде (чч:мм), сортировка по времени поступления в трекер (для ответа на вопрос, на какие задачи я потратил больше времени)

    @param user_id - идентификатор искомого пользователя               
    @param date1   - дата с которой начинается поиск                   
    @param date2   - дата по которуюю идёт поиск                                            
    @return тело ответа - список задач (сортировка по времени поступления в треккер)                     

    ````$ cyrl 'localhost:8080/work_time_for_user/user_id=1/from=2020-08-01/to=2020-08-10'````
7. Показать все временные интервалы занятые работой за период N..M в виде связного списка Задача - Временной интервал (число чч:мм) (для ответа на вопрос, где за прошедшую неделю были ‘дыры’, когда я ничего не делал)

    @param user_id - идентификатор искомого пользователя                         
    @param date1   - дата с которой начинается поиск                             
    @param date2   - дата по которуюю идёт поиск                                           
    @return тело ответа - список задач (сортировка по времени поступления в треккер)                              
    
    ````$ curl 'localhost:8080/no_work_time_for_user=1/from=2020-08-01/to=2020-08-10")````
8. Показать сумму трудозатрат по всем задачам пользователя Y за период N..M

    @param user_id - идентификатор искомого пользователя                   
    @param date1   - дата с которой начинается поиск                  
    @param date2   - дата по которуюю идёт поиск                                             
    @return тело ответа - список задач (сортировка по времени поступления в треккер)                  
    
    ````$ curl 'localhost:8080/view_time_for_user=1/from=2020-08-01/to=2020-08-10")````
9. Удалить пользователя Z
    
    @param user_id - идентификатор пользователя, которого нужно удалить                 
    @return тело ответа - сообщение об удалении пользователя                           
    
    ````$ curl 'localhost:8080/delete/user_id=1'````
10. Очистить данные трекинга пользователя Z

    @param user_id - идентификатор пользователя                       
    @return тело ответа - сообщение о том, что данные треккинга пользователя удалены                               
   
    ````$ curl 'localhost:8080/delete_info_for_user/user_id=1'````

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

    ````$ gradle build -x test````
8. Запуск проекта

    ````$ gradle bootRun````
