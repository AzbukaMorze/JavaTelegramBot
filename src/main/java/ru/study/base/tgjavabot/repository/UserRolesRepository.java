package ru.study.base.tgjavabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.study.base.tgjavabot.model.User;
import ru.study.base.tgjavabot.model.UserRole;

import java.util.Optional;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByUser(User user);
}
