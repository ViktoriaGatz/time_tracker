package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.user.User;

/**
 * Класс-репозиторий для сущности User (пользователь)
 * @author ViktoriaGatz
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
