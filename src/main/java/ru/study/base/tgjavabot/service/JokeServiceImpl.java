package ru.study.base.tgjavabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.exception.ResourceNotFoundException;
import ru.study.base.tgjavabot.mapper.JokeMapper;
import ru.study.base.tgjavabot.model.Joke;
import ru.study.base.tgjavabot.model.JokeHistory;
import ru.study.base.tgjavabot.repository.JokeHistoryRepository;
import ru.study.base.tgjavabot.repository.JokeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;
    private final JokeMapper jokeMapper;
    private final JokeHistoryRepository jokeHistoryRepository;

    @Override
    public Joke save(JokeDto jokeDto) {
        return jokeRepository.save(jokeMapper.toEntity(jokeDto));
    }

    @Override
    public Joke update(Long id, JokeDto jokeDto) {
        Joke jokeFromDB = jokeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Joke not found by id = %s", id))
        );
        jokeFromDB.setTitle(jokeDto.getTitle());
        jokeFromDB.setText(jokeDto.getText());
        jokeFromDB.setChangedData(LocalDateTime.now());
        return jokeRepository.save(jokeFromDB);
    }

    @Override
    public void delete(Long id) {
        Joke jokeFromDB = jokeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Joke not found by id = %s", id))
        );
        jokeRepository.deleteById(id);
    }

    @Override
    public List<Joke> getAll() {
        return jokeRepository.findAll();
    }

    @Override
    public Joke getById(Long id, Long userId) {
        Joke joke = jokeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Joke not found by id = %s", id))
        );
        recordJokeCall(joke, userId);
        return joke;
    }

    private void recordJokeCall(Joke joke, Long userId) {
        JokeHistory jokeHistory = JokeHistory.builder()
                .joke(joke)
                .userId(userId)
                .callTime(LocalDateTime.now())
                .build();
        jokeHistoryRepository.save(jokeHistory);
    }

    @Override
    public List<Joke> getTopJokes(int limit) {
        return jokeHistoryRepository.findTopJokes().stream()
                .limit(limit)
                .map(result -> jokeRepository.findById((Long) result[0]).orElseThrow(
                        () -> new ResourceNotFoundException("Joke not found by id = " + result[0])
                ))
                .collect(Collectors.toList());
    }
}
