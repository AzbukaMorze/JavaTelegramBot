package ru.study.base.tgjavabot.service;

import org.springframework.data.domain.Page;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.model.Joke;

import java.util.List;

public interface JokeService {
    Joke save(JokeDto jokeDto);
    Joke update(Long id, JokeDto jokeDto);
    void delete(Long id);
    Page<Joke> getAll(int page, boolean sortByDate);
    List<Joke> getAll4Bot();
    Joke getById(Long id, Long userId);  // Обновлено
    List<Joke> getTopJokes(int limit); // Новый метод
    Joke getRandomJoke();
}

