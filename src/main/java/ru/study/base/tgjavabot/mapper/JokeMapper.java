package ru.study.base.tgjavabot.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.model.Joke;
import ru.study.base.tgjavabot.utils.CurrentTimeService;

@Component
@AllArgsConstructor
public class JokeMapper {

    private final CurrentTimeService currentTimeService;

    public Joke toEntity(JokeDto jokeDto) {
        Joke joke = new Joke();
        joke.setTitle(jokeDto.getTitle());
        joke.setText(jokeDto.getText());
        joke.setCreatedData(currentTimeService.getCurrentTime());
        joke.setChangedData(currentTimeService.getCurrentTime());
        return joke;
    }

}
