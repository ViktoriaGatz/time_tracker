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
import ru.example.controller.Controller;
import ru.example.entity.user.User;
import ru.example.repository.TaskRepository;
import ru.example.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
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
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void toDoTest() {
        given(this.userRepository.findById(1L)).willReturn(java.util.Optional.of(new User(1L, "Viki")));
        assertThat(this.userRepository.findById(1L).get().getFio()).isEqualTo("Viki");
    }

    @Test
    public void test1() throws Exception {
        this.mvc
                .perform(get("/show_task"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test2() throws Exception {
        this.mvc
                .perform(get("/show_user"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

}

// .andExpect(status().isOk()) - код возврата был 200?