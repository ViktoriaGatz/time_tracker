package ru.example;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.service.TaskServiceImpl;
import ru.example.service.UserServiceImpl;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
    /*
        Способ тестирования конечных точек:
        имитационная среда класс MockMvc
    */
    @Autowired
    private MockMvc mvc;

    /*
        Аннотация @MockBean, описывает имитационный объект Mockito
        для компонента в Applicationcontext
    */
    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private TaskServiceImpl taskService;

    @Test
    public void testUserFindById() {
        given(this.userService.findById(1L)).willReturn(java.util.Optional.of(new User(1L, "Viki")));
        assertThat(this.userService.findById(1L).get().getFio()).isEqualTo("Viki");
    }

    @Test
    public void testUserFindAll() {
        ArrayList<User> list = new ArrayList();
        list.add(new User(1L, "Viki"));
        list.add(new User(2L, "Lili"));
        given(this.userService.findAll()).willReturn(list);
        assertThat(this.userService.findAll().equals(list));
    }

    @Test
    public void taskFindByIdTestTitle() {
        given(this.taskService.findById(1L)).willReturn(java.util.Optional.of(new Task("title1", "desc1")));
        assertThat(this.taskService.findById(1L).get().getTitle()).isEqualTo("title1");
    }

    @Test
    public void taskFindByIdTestDesc() {
        given(this.taskService.findById(1L)).willReturn(java.util.Optional.of(new Task("title1", "desc1")));
        assertThat(this.taskService.findById(1L).get().getDescription()).isEqualTo("desc1");
    }

    @Test
    public void testTaskFindAll() {
        ArrayList<Task> list = new ArrayList();
        list.add(new Task("title1", "desc1"));
        list.add(new Task("title2", "desc2"));
        given(this.taskService.findAll()).willReturn(list);
        assertThat(this.taskService.findAll().equals(list));
    }

    @Test
    public void testViewShowTask() throws Exception {
        this.mvc
                .perform(get("/show_task"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testViewShowUser() throws Exception {
        this.mvc
                .perform(get("/show_user"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void controllerTest() throws Exception {
        given(this.userService.findById(1L))
                .willReturn(java.util.Optional.of(new User(1L, "Viktoria")));
        this.mvc.perform(get("/user_id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().
                string("{\"user_id\":1 ,\"fio\":\"Viktoria\",\"task_list\":null}"));
    }

}

// .andExpect(status().isOk()) - код возврата был 200?