package ru.study.base.tgjavabot.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

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
    @GeneratedValue(generator = "history_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "history_id_seq", sequenceName = "history_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "joke_id", nullable = false)
    private Joke joke;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "call_time", nullable = false)
    private Date callTime;
}
