package ru.study.base.tgjavabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.base.tgjavabot.model.JokeHistory;

import java.util.List;

public interface JokeHistoryRepository extends JpaRepository<JokeHistory, Long> {

    long countByJokeId(Long jokeId);

    @Query("SELECT jh.joke.id AS jokeId, COUNT(jh.id) AS callCount " +
            "FROM joke_history jh " +
            "GROUP BY jh.joke.id " +
            "ORDER BY callCount DESC")
    List<Object[]> findTopJokes();
}


