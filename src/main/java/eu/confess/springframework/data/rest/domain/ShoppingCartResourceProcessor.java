package eu.confess.springframework.data.rest.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

/**
 * @author Jon Brisbin
 */
public class ShoppingCartResourceProcessor implements ResourceProcessor<Resource<ShoppingCart>> {

	@Autowired
	private EntityLinks entityLinks;

	@Override public Resource<ShoppingCart> process(Resource<ShoppingCart> res) {
		Long customerId = res.getContent().getCustomer();
		Link customerLink = entityLinks.linkToSingleResource(Customer.class, customerId);

		res.add(customerLink);

		return res;
	}

}
