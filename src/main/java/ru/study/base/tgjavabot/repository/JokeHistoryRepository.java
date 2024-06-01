package ru.study.base.tgjavabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.base.tgjavabot.model.JokeHistory;

import java.util.List;

public interface JokeHistoryRepository extends JpaRepository<JokeHistory, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT jh.joke_id AS jokeId, \n" +
                    "COUNT(jh.id) AS callCount \n" +
                    "FROM joke_history jh \n" +
                    "GROUP BY jh.joke_id \n" +
                    "ORDER BY callCount DESC;")
    List<Object[]> findTopJokes();
}


