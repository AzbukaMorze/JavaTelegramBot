package ru.study.base.tgjavabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.study.base.tgjavabot.model.Joke;

import java.awt.print.Pageable;

public interface JokeRepository extends PagingAndSortingRepository<Joke, Long>, JpaRepository<Joke, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM jokes " +
                    "ORDER BY RANDOM() " +
                    "LIMIT 1"
    )
    Joke getRandomJoke();
}
