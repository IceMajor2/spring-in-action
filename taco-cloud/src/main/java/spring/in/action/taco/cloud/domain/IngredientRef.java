package spring.in.action.taco.cloud.domain;

import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("INGREDIENT_TACO")
@Getter
public class IngredientRef {

    @Column("INGREDIENT")
    private String id;
    private String name;
    private Ingredient.Type type;

    public IngredientRef(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
