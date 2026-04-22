package org.example.test10.Repository;

import org.example.test10.Enum.EtatCompe;
import org.example.test10.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    List<User> findByEtatCompe(EtatCompe etatCompe);

}
