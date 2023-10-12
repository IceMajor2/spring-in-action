package spring.in.action.taco.cloud.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import spring.in.action.taco.cloud.domain.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>, CrudRepository<Taco, Long> {
}