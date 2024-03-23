package ru.study.base.tgjavabot.contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/app")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService botService;

    @PostMapping
    ResponseEntity<Joke> createJoke(@RequestBody JokeDto jokeDto) {
        return ResponseEntity.ok().body(botService.save(jokeDto));
    }

    @GetMapping
    ResponseEntity<List<Joke>> getJokes() {
        return ResponseEntity.ok(botService.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(botService.getById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<Joke> updateJoke(@PathVariable Long id,
                                    @RequestBody JokeDto jokeDto) {
        return ResponseEntity.ok().body(botService.update(id, jokeDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        botService.delete(id);
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