package ru.study.base.tgjavabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.exception.ResourceNotFoundException;
import ru.study.base.tgjavabot.mapper.JokeMapper;
import ru.study.base.tgjavabot.model.Joke;
import ru.study.base.tgjavabot.repository.JokeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;
    private final JokeMapper jokeMapper;

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
        jokeFromDB.setText(jokeFromDB.getText());
        jokeFromDB.setChangedData(LocalDateTime.now());
        return jokeRepository.save(jokeFromDB);
    }

    @Override
    public boolean delete(Long id) {
        Joke jokeFromDB = jokeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Joke not found by id = %s", id))
        );
        jokeRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Joke> getAll() {
        return jokeRepository.findAll();
    }


    @Override
    public Joke getById(Long id) {
        return jokeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Joke not found by id = %s", id))
        );
    }
}
