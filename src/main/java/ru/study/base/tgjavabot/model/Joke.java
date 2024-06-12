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
@Entity(name = "jokes")
@Table(name = "jokes")
public class Joke {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "joke_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "joke_id_seq", sequenceName = "joke_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created_date")
    private LocalDateTime createdData = LocalDateTime.now();;

    @Column(name = "changed_date")
    private LocalDateTime changedData;

}
