package spring.in.action.taco.cloud.data;

import org.springframework.data.repository.CrudRepository;
import spring.in.action.taco.cloud.domain.TacoOrder;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {

    TacoOrder save(TacoOrder order);
}
