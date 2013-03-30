package eu.confess.springframework.data.rest.repository;

import eu.confess.springframework.data.rest.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends
		PagingAndSortingRepository<Order, Long> {

}
