package ru.study.base.tgjavabot.mapper;

import org.springframework.stereotype.Component;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.model.Joke;

@Component
public class JokeMapper {

    public Joke toEntity(JokeDto jokeDto) {
        Joke joke = new Joke();
        joke.setTitle(jokeDto.getTitle());
        joke.setText(jokeDto.getText());
        return joke;
    }

}
