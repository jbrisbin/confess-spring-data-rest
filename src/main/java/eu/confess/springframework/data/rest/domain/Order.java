package eu.confess.springframework.data.rest.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONFESS_Order")
public class Order extends AbstractEntity {

	private OrderType type;
	@ManyToOne
	private Customer customer;

	public Order() {
	}

	public OrderType getType() {
		return type;
	}

	public Order setType(OrderType type) {
		this.type = type;
		return this;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Order setCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

}
