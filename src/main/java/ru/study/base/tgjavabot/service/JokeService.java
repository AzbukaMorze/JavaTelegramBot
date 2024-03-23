package ru.study.base.tgjavabot.service;

import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {

    Joke save(JokeDto jokeDto);

    boolean delete(Long id);

    List<Joke> getAll();

    Joke getById(Long id);

    Joke update(Long id, JokeDto jokeDto);
}
