package melo.rodrigues.guilherme.multitenantapp.repositories;

import melo.rodrigues.guilherme.multitenantapp.entities.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByUsername(String username);

    User save(User user);

    List<User> findAll();
}
