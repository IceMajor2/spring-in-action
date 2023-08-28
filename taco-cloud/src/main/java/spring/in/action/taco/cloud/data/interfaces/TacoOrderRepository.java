package spring.in.action.taco.cloud.data.interfaces;

import spring.in.action.taco.cloud.domain.TacoOrder;

public interface TacoOrderRepository {

    TacoOrder save(TacoOrder order);
}
