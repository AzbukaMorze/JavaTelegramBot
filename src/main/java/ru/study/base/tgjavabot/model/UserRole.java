package ru.study.base.tgjavabot.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.memory.UserAttribute;

@Entity(name = "user_roles")
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class UserRole {

    @Id
    @GeneratedValue(generator = "user_role_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_role_id_seq", allocationSize = 1, sequenceName = "user_role_id_seq")
    private Long id;

    @Enumerated
    private UserAuthority userAuthority;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
