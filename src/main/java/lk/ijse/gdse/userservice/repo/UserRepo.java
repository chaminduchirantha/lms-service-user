package lk.ijse.gdse.userservice.repo;

import lk.ijse.gdse.userservice.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {

     boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
