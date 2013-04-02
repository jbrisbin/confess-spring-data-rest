package eu.confess.springframework.data.rest.repository;

import eu.confess.springframework.data.rest.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
