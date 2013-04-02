package eu.confess.springframework.data.rest.domain;

import java.util.List;

import eu.confess.springframework.data.rest.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

/**
 * @author Jon Brisbin
 */
public class CustomerResourceProcessor implements ResourceProcessor<Resource<Customer>> {

	@Autowired
	private ShoppingCartRepository shoppingCarts;
	@Autowired
	private EntityLinks            entityLinks;

	@Override public Resource<Customer> process(Resource<Customer> res) {
		List<ShoppingCart> carts = shoppingCarts.findByCustomer(res.getContent().getId());
		for(ShoppingCart cart : carts) {
			Link l = entityLinks.linkToSingleResource(ShoppingCart.class, cart.getId());
			res.add(l);
		}

		return res;
	}

}
