package spring.in.action.taco.cloud;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.in.action.taco.cloud.data.IngredientRepository;
import spring.in.action.taco.cloud.data.TacoRepository;
import spring.in.action.taco.cloud.data.UserRepository;
import spring.in.action.taco.cloud.domain.Ingredient;
import spring.in.action.taco.cloud.domain.IngredientRef;
import spring.in.action.taco.cloud.domain.Taco;
import spring.in.action.taco.cloud.security.User;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository ingredientRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder encoder,
                                        TacoRepository tacoRepository) {
        return args -> {
            Ingredient flourTortilla = ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            Ingredient cornTortilla = ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            Ingredient groundBeef = ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            Ingredient carnitas = ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            Ingredient tomatoes = ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            Ingredient lettuce = ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            Ingredient cheddar = ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            Ingredient jack = ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            Ingredient salsa = ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            Ingredient sourCream = ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Set.of(
                    new IngredientRef(flourTortilla.getId(), flourTortilla.getName(), flourTortilla.getType().name()),
                    new IngredientRef(groundBeef.getId(), groundBeef.getName(), groundBeef.getType().name()),
                    new IngredientRef(carnitas.getId(), carnitas.getName(), carnitas.getType().name()),
                    new IngredientRef(sourCream.getId(), sourCream.getName(), sourCream.getType().name()),
                    new IngredientRef(salsa.getId(), salsa.getName(), salsa.getType().name()),
                    new IngredientRef(cheddar.getId(), cheddar.getName(), cheddar.getType().name())));
            tacoRepository.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Set.of(
                    new IngredientRef(cornTortilla.getId(), cornTortilla.getName(), cornTortilla.getType().name()),
                    new IngredientRef(groundBeef.getId(), groundBeef.getName(), groundBeef.getType().name()),
                    new IngredientRef(cheddar.getId(), cheddar.getName(), cheddar.getType().name()),
                    new IngredientRef(jack.getId(), jack.getName(), jack.getType().name()),
                    new IngredientRef(sourCream.getId(), sourCream.getName(), sourCream.getType().name())));
            tacoRepository.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Set.of(
                    new IngredientRef(flourTortilla.getId(), flourTortilla.getName(), flourTortilla.getType().name()),
                    new IngredientRef(cornTortilla.getId(), cornTortilla.getName(), cornTortilla.getType().name()),
                    new IngredientRef(tomatoes.getId(), tomatoes.getName(), tomatoes.getType().name()),
                    new IngredientRef(lettuce.getId(), lettuce.getName(), lettuce.getType().name()),
                    new IngredientRef(salsa.getId(), salsa.getName(), salsa.getType().name())));
            tacoRepository.save(taco3);

            // "admin", "admin"
            userRepository.save(new User("admin", "$2a$10$jXi/h0Gt948Sqy82R3Pi/OseqaWizLUAdfa1oXFw/JGMxFd9ejvPi",
                    "Admin Admin", "ul. Administratorska 16", "Administratorow Wielki",
                    "administratorowsko-administratorowskie", "64-128", "512256128"));
        };
    }
}
