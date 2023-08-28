package spring.in.action.taco.cloud.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spring.in.action.taco.cloud.domain.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM ingredient", mapRowToIngredient());
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM ingredient WHERE id = ?",
                mapRowToIngredient(),
                id
        ));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return null;
    }

    private RowMapper<Ingredient> mapRowToIngredient() {
        return (rs, rowNum) -> new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }
}
