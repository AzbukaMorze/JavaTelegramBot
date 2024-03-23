package ru.study.base.tgjavabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.base.tgjavabot.model.Joke;

public interface JokeRepository extends JpaRepository<Joke, Long> {
}
