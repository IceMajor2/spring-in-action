package spring.in.action.taco.cloud;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.in.action.taco.cloud.data.IngredientRepository;
import spring.in.action.taco.cloud.data.UserRepository;
import spring.in.action.taco.cloud.domain.Ingredient;
import spring.in.action.taco.cloud.security.User;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository ingredientRepository, UserRepository userRepository) {
        return args -> {
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

            // "admin", "admin"
            userRepository.save(new User("admin", "$2a$10$jXi/h0Gt948Sqy82R3Pi/OseqaWizLUAdfa1oXFw/JGMxFd9ejvPi",
                    "Admin Admin", "ul. Administratorska 16", "Administratorow Wielki",
                    "administratorowsko-administratorowskie", "64-128", "512256128"));
        };
    }
}
