package org.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.userservice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User getUserById(Integer id);

    User getUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
