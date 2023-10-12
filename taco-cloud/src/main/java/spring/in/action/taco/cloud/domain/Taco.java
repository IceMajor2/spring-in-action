package spring.in.action.taco.cloud.domain;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("TACO")
public class Taco {

    @Id
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private Set<IngredientRef> ingredients = new HashSet<>();

    void addIngredient(Ingredient ingredient) {
        this.ingredients.add(new IngredientRef(ingredient));
    }

    Set<String> getIngredientIds() {
        return this.ingredients.stream()
                .map(IngredientRef::getId)
                .collect(Collectors.toSet());
    }
}
