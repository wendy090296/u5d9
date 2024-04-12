package wendydeluca.u5d9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wendydeluca.u5d9.entities.User;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserDAO extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

}
