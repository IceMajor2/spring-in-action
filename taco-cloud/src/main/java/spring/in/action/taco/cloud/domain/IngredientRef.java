package spring.in.action.taco.cloud.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Table("INGREDIENT_TACO")
@Getter
@JsonPropertyOrder({"id", "name", "type"})
public class IngredientRef {

    @JsonProperty("id")
    private String ingredient;
    private String name;
    private String type;

    public IngredientRef(String ingredient, String name, String type) {
        this.ingredient = ingredient;
        this.name = name;
        this.type = type;
    }
}
