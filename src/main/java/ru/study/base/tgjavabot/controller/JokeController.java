package ru.study.base.tgjavabot.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.base.tgjavabot.dto.JokeDto;
import ru.study.base.tgjavabot.exception.ResourceNotFoundException;
import ru.study.base.tgjavabot.model.Joke;
import ru.study.base.tgjavabot.service.JokeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
@Transactional
public class JokeController {

    private final JokeService jokeService;

    @PostMapping
    ResponseEntity<Joke> createJoke(@RequestBody JokeDto jokeDto) {
        return ResponseEntity.ok().body(jokeService.save(jokeDto));
    }

    @GetMapping
    ResponseEntity<Page<Joke>> getJokes(@RequestParam int page) {
        return ResponseEntity.ok(jokeService.getAll(page, true));
    }

    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id, @RequestParam Long userId) {  // Обновлено
        return ResponseEntity.ok().body(jokeService.getById(id, userId));
    }

    @GetMapping("/top")
    ResponseEntity<List<Joke>> getTopJokes(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(jokeService.getTopJokes(limit));
    }

    @GetMapping("/random")
    ResponseEntity<Joke> getRandomJoke() {
        return ResponseEntity.ok(jokeService.getRandomJoke());
    }

    @PutMapping("/{id}")
    ResponseEntity<Joke> updateJoke(@PathVariable Long id,
                                    @RequestBody JokeDto jokeDto) {
        return ResponseEntity.ok().body(jokeService.update(id, jokeDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        jokeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseBody
    public ResponseEntity<String> resourceNotFoundException(Exception e) {
        log.error("Ошибка поиска сущности по id");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getLocalizedMessage());
    }
}
