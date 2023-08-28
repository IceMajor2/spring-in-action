package spring.in.action.taco.cloud.data;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.taco.cloud.data.interfaces.TacoOrderRepository;
import spring.in.action.taco.cloud.domain.Ingredient;
import spring.in.action.taco.cloud.domain.IngredientRef;
import spring.in.action.taco.cloud.domain.Taco;
import spring.in.action.taco.cloud.domain.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcTacoOrderRepository implements TacoOrderRepository {

    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcTacoOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        var pscf = new PreparedStatementCreatorFactory("INSERT INTO taco_order " +
                "(delivery_name, delivery_street, delivery_city, delivery_state, "
                + "delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at) " +
                "VALUES (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int orderKey = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, orderKey++, taco);
        }
        return order;
    }

    private Taco saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        var pscf = new PreparedStatementCreatorFactory("INSERT INTO taco " +
                "(name, created_at, taco_order, taco_order_key) " +
                "VALUES (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
        pscf.setReturnGeneratedKeys(true);

        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey
                )
        );
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return taco;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOperations.update("INSERT INTO ingredient_ref " +
                            "(ingredient, taco, taco_key) VALUES (?, ?, ?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
