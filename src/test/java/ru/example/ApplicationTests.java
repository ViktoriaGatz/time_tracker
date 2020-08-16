package ru.example;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.repository.TaskRepository;
import ru.example.repository.UserRepository;
import ru.example.service.TaskServiceImpl;
import ru.example.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.refEq;

/**
 * MVC тестирование
 *
 * @author ViktoriaGatz
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
    /**
     * Способ тестирования конечных точек:
     * имитационная среда класс MockMvc
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Аннотация @MockBean, описывает имитационный объект Mockito
     * для компонента UserService в Applicationcontext
     */
    @MockBean
    private UserServiceImpl userService;

    /**
     * Аннотация @MockBean, описывает имитационный объект Mockito
     * для компонента TaskService в Applicationcontext
     */
    @MockBean
    private TaskServiceImpl taskService;

    /**
     * Тест для метода findById для класса UserService
     */
    @Test
    public void testUserFindById() {
        given(this.userService.findById(1L)).willReturn(java.util.Optional.of(new User(1L, "Viki")));
        assertThat(this.userService.findById(1L).get().getFio()).isEqualTo("Viki");
    }

    /**
     * Тест для метода findAll для класса UserService
     */
    @Test
    public void testUserFindAll() {
        ArrayList<User> list = new ArrayList();
        list.add(new User(1L, "Viki"));
        list.add(new User(2L, "Lili"));
        given(this.userService.findAll()).willReturn(list);
        assertThat(this.userService.findAll()).isEqualTo(list);
    }

    /**
     * Тест для метода findById для класса TaskService
     */
    @Test
    public void taskFindByIdTestTitle() {
        given(this.taskService.findById(1L)).willReturn(java.util.Optional.of(new Task("title1", "desc1")));
        assertThat(this.taskService.findById(1L).get().getTitle()).isEqualTo("title1");
        assertThat(this.taskService.findById(1L).get().getDescription()).isEqualTo("desc1");
    }

    /**
     * Тест для метода findAll для класса TaskService
     */
    @Test
    public void testTaskFindAll() {
        ArrayList<Task> list = new ArrayList();
        list.add(new Task("title1", "desc1"));
        list.add(new Task("title2", "desc2"));
        given(this.taskService.findAll()).willReturn(list);
        assertThat(this.taskService.findAll()).isEqualTo(list);
    }

    /**
     * Тестирование отображения страницы "/show_task"
     *
     * @throws Exception - любые исключения
     */
    @Test
    public void testViewShowTask() throws Exception {
        this.mvc
                .perform(get("/show_task"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Тестирование отображения страницы "/show_user"
     *
     * @throws Exception - любые исключения
     */
    @Test
    public void testViewShowUser() throws Exception {
        this.mvc
                .perform(get("/show_user"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Тестирование get запроса по поиску пользователя по id - "/user_id=1"
     *
     * @throws Exception - любые исключения
     */
    @Test
    public void controllerTest() throws Exception {
        given(this.userService.findById(1L))
                .willReturn(java.util.Optional.of(new User(1L, "Viktoria")));
        this.mvc.perform(get("/user_id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().
                string("{\"user_id\":1,\"fio\":\"Viktoria\",\"task_list\":[]}"));
    }

    /**
     * Тестирование get запроса по поиску пользователя по id - "/user_id=1",
     * в случае, если пользователь не найден
     *
     * @throws Exception - любые исключения
     */
    @Test
    public void testFindByIdOnController() throws Exception {
        this.mvc.perform(get("/user_id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testReturnTimeSpentOnWorkFound() throws Exception {
//        User user = new User(1L, "Viki");
//        Task task1 = new Task(user, 1L, "title1", "desc1", null, new Date(0), null, null);
//        Task task2 = new Task(user, 2L, "title2", "desc2", null, new Date(86400000 * 2 + 100000), null, null);
//        Task task3 = new Task(user, 3L, "title3", "desc3", null, new Date(999000000), null, null);
//        List<Task> requestList = new ArrayList<>();
//        List<Task> responseList = new ArrayList<>();
//        requestList.add(task1);
//        requestList.add(task2);
//        requestList.add(task3);
//        responseList.add(task2);
//        user.setTask_list(requestList);
//
//
////        given(this.userService.findById(1L)).willReturn(java.util.Optional.of(user));
//        when(this.userService.findById(1L)).thenReturn(java.util.Optional.of(user));
//        when(this.userService.time_for_user(1L, new Date(86400000 * 2), new Date(86400000 * 4))).thenReturn(responseList);
//        this.mvc.perform(get("/view_time_for_user=1/from=1970-01-02/to=1970-01-04"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        verify(this.userService).time_for_user(1L, new Date(86400000 * 2), new Date(86400000 * 4));
//    }
//

    /**
     * Тестирование POST метода по добавлению новой задачи
     * @throws Exception - любые исключения
     */
    @Test
    public void testSaveTaskPost() throws Exception {
        Task movie1 = new Task(null, 1L, "title", "description", null, null, null, null);

        when(taskService.save(movie1)).thenReturn(movie1);
        this.mvc
                .perform(post("/saveTask").contentType(MediaType.APPLICATION_JSON).content("{" +
                        " \"task_id\":1," +
                        " \"title\":\"title\"," +
                        " \"description\":\"description\"," +
                        " \"date_add_task\":null," +
                        " \"date_start_task\":null," +
                        " \"date_stop_task\":null," +
                        " \"time\":null" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
        verify(this.taskService).save(ArgumentMatchers.refEq(movie1));
    }

    /**
     * Тестирование POST метода по добавлению нового пользователя
     * @throws Exception - любые исключения
     */
    @Test
    public void testSaveUserPost() throws Exception {
        User movie1 = new User(1L, "fio");
        when(userService.save(movie1)).thenReturn(movie1);
        this.mvc
                .perform(post("/saveUser").contentType(MediaType.APPLICATION_JSON).content("{" +
                        "\"user_id\":1," +
                        "\"fio\":\"fio\"," +
                        "\"task_list\":[]" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
        verify(this.userService).save(ArgumentMatchers.refEq(movie1));
    }

    /**
     * Тестирование метода time_for_user для класса UserServiceImpl
     * На предмет выявления из всех задач тех, что были начаты в конкретный период
     *
     * @throws Exception - любые исключения
     */
    @Test
    public void testTimeForUser() throws Exception {
        User user = new User(1L, "Viki");
        Task task2 = new Task(user, 2L, "title2", "desc2", null, new Date(86400000 * 2 + 100000), null, null);
        List<Task> list = new ArrayList<>();
        list.add(task2);
        user.setTask_list(list);

        given(this.userService.findById(1L)).willReturn(java.util.Optional.of(user));

        this.userService.time_for_user(1L, new Date(86400000 * 2), new Date(86400000 * 4));

        assertThat(this.userService.findById(1L).get().getTask_list()).isEqualTo(list);

    }

}

// .andExpect(status().isOk()) - код возврата был 200?
