package ru.study.base.tgjavabot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "joke_history")
@Table(name = "joke_history")
public class JokeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "joke_id", nullable = false)
    private Joke joke;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "call_time", nullable = false)
    private LocalDateTime callTime = LocalDateTime.now();
}
