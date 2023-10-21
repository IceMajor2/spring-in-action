package spring.in.action.taco.cloud.auth.server;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.in.action.taco.cloud.auth.server.user.User;
import spring.in.action.taco.cloud.auth.server.user.UserRepository;

@SpringBootApplication
public class TacoCloudAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudAuthServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(
            UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            repository.save(new User("user", encoder.encode("user"), "ROLE_ADMIN"));
            repository.save(new User("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
