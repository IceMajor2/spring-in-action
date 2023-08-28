package spring.in.action.taco.cloud.data.interfaces;

import spring.in.action.taco.cloud.domain.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
