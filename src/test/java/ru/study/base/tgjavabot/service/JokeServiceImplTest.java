package ru.study.base.tgjavabot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.study.base.tgjavabot.mapper.JokeMapper;
import ru.study.base.tgjavabot.model.Joke;
import ru.study.base.tgjavabot.repository.JokeHistoryRepository;
import ru.study.base.tgjavabot.repository.JokeRepository;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.utils.CurrentTimeService;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JokeServiceImplTest {

    @Mock
    private final JokeRepository jokeRepository = Mockito.mock(JokeRepository.class);
    private final JokeHistoryRepository jokeHistoryRepository = Mockito.mock(JokeHistoryRepository.class);
    private final JokeMapper jokeMapper = Mockito.mock(JokeMapper.class);
    private final CurrentTimeService currentTimeService = Mockito.mock(CurrentTimeService.class);
    private final JokeServiceImpl jokeService = new JokeServiceImpl(jokeRepository, jokeMapper, jokeHistoryRepository, currentTimeService);

    @Test
    @DisplayName("Joke saving test")
    void jokeSaveTestShouldReturnSavedJoke() {
        Joke expectedJoke = new Joke(1L, "SomeTitle", "SomeText", currentTimeService.getCurrentTime(), currentTimeService.getCurrentTime());
        JokeDto inputJokeDto = new JokeDto("SomeTitle", "SomeText");
        Mockito.when(jokeRepository.save(jokeMapper.toEntity(inputJokeDto))).thenReturn(expectedJoke);
        assertEquals(expectedJoke, jokeService.save(inputJokeDto));
    }

    @Test
    @DisplayName("Updating joke test")
    void jokeUpdateTestShouldReturnSavedJoke() {
        Joke existingJoke = new Joke(1L, "OldTitle", "OldText", currentTimeService.getCurrentTime(), currentTimeService.getCurrentTime());
        Joke expectedJoke = new Joke(1L, "SomeTitle", "SomeText", currentTimeService.getCurrentTime(), currentTimeService.getCurrentTime());
        Long jokeId = 1L;
        JokeDto inputJokeDto = new JokeDto("SomeTitle", "SomeText");

        Mockito.when(jokeRepository.findById(jokeId)).thenReturn(Optional.of(existingJoke));
        Mockito.when(jokeRepository.save(existingJoke)).thenReturn(expectedJoke);
        Mockito.when(jokeMapper.toEntity(inputJokeDto)).thenReturn(expectedJoke);

        assertEquals(expectedJoke, jokeService.update(jokeId, inputJokeDto));
    }


    @Test
    void delete() {

    }

    @Test
    void getAll() {
    }

    @Test
    void getAll4Bot() {
    }

    @Test
    void getById() {
    }

    @Test
    void getTopJokes() {
    }

    @Test
    @DisplayName("Getting random joke test")
    void getRandomJokeTestShouldReturnRandomJoke() {
        Joke inputJoke = new Joke(1L, "SomeTitle", "SomeText", currentTimeService.getCurrentTime(), currentTimeService.getCurrentTime());

        Mockito.when(jokeRepository.getRandomJoke()).thenReturn(inputJoke);

        assertEquals(inputJoke, jokeService.getRandomJoke());
    }
}