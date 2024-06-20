package ru.study.base.tgjavabot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.study.base.tgjavabot.service.JokeService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JokeControllerTest {
    @Mock
    private JokeService jokeService;

    @InjectMocks
    private JokeController jokeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jokeController).build();
    }

    @Test
    void getJokesShouldReturnOkStatus() throws Exception {
        int page = 1;
        mockMvc.perform(get("/jokes").param("page", "1" ))
                .andExpect(status().isOk());
        verify(jokeService, times(1)).getAll(page, true);
    }

    @Test
    void createJoke() {
    }

    @Test
    void getJokes() {
    }

    @Test
    void getJokeById() {
    }

    @Test
    void getTopJokes() {
    }

    @Test
    void getRandomJoke() {
    }

    @Test
    void updateJoke() {
    }

    @Test
    void deleteJoke() {
    }

    @Test
    void resourceNotFoundException() {
    }
}
