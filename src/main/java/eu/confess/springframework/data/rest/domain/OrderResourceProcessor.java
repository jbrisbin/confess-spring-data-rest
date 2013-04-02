package eu.confess.springframework.data.rest.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

/**
 * @author Jon Brisbin
 */
public class OrderResourceProcessor implements ResourceProcessor<Resource<Order>> {

	@Override public Resource<Order> process(Resource<Order> resource) {
		Link l = resource.getLink("self");
		resource.add(new Link(l.getHref() + "/payment", "order.payment"));
		return resource;
	}

}
