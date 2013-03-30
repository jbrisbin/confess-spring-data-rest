package eu.confess.springframework.data.rest.domain;

import eu.confess.springframework.data.rest.repository.CustomerRepository;
import eu.confess.springframework.data.rest.repository.OrderRepository;
import eu.confess.springframework.data.rest.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jon Brisbin
 */
public class DataLoader {

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	@Autowired
	private CustomerRepository     customers;
	@Autowired
	private OrderRepository        orders;
	@Autowired
	private ShoppingCartRepository carts;

	public void loadData() {
		Customer c = customers.save(new Customer()
				                            .setName("John Doe")
				                            .setEmail(new Email("john.doe@gmail.com")));
		LOG.info("Saved new Customer " + c);

		Order o = orders.save(new Order()
				                      .setCustomer(c)
				                      .setType(OrderType.ONLINE));
		LOG.info("Saved new Order " + o);

		carts.deleteAll();
		ShoppingCart cart = carts.save(new ShoppingCart()
				                               .setCustomer(1L)
				                               .add(new ShoppingCart.LineItem()
						                                    .setDescription("ACME Widget")
						                                    .setQuantity(100.0)
						                                    .setUnitPrice(10.0)));
		LOG.info("Saved new ShoppingCart " + cart);
	}

}
