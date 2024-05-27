package ru.study.base.tgjavabot.service;

import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.model.Joke;

import java.util.List;

public interface JokeService {
    Joke save(JokeDto jokeDto);
    Joke update(Long id, JokeDto jokeDto);
    void delete(Long id);
    List<Joke> getAll();
    Joke getById(Long id, Long userId);  // Обновлено
    List<Joke> getTopJokes(int limit); // Новый метод
}
