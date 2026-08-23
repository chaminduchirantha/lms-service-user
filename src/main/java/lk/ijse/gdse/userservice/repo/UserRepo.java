package lk.ijse.gdse.userservice.repo;

import lk.ijse.gdse.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

     boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
