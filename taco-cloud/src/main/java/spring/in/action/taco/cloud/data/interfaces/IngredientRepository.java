package spring.in.action.taco.cloud.data.interfaces;

import org.springframework.data.repository.CrudRepository;
import spring.in.action.taco.cloud.domain.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
