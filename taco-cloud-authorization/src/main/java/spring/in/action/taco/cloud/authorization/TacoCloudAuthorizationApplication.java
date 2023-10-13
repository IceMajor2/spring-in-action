package spring.in.action.taco.cloud.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.in.action.taco.cloud.authorization.users.User;
import spring.in.action.taco.cloud.authorization.users.UserRepository;

@SpringBootApplication
public class TacoCloudAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudAuthorizationApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(
            UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            repository.save(new User("habuma", encoder.encode("password"), "ROLE_ADMIN"));
            repository.save(new User("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
