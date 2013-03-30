package eu.confess.springframework.data.rest.repository;

import eu.confess.springframework.data.rest.domain.Order;
import eu.confess.springframework.data.rest.domain.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Jon Brisbin
 */
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	public Payment findByOrder(Order order);

}
