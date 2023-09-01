package spring.in.action.taco.cloud.data;

import org.springframework.data.repository.CrudRepository;
import spring.in.action.taco.cloud.security.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
