package eu.confess.springframework.data.rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import eu.confess.springframework.data.rest.domain.Customer;

public interface CustomerRepository extends
		PagingAndSortingRepository<Customer, Long> {

}
