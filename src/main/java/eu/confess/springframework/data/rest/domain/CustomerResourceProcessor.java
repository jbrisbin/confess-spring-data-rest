package eu.confess.springframework.data.rest.domain;

import static org.springframework.data.rest.repository.support.ResourceMappingUtils.*;

import java.net.URI;
import java.util.List;

import eu.confess.springframework.data.rest.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.config.RepositoryRestConfiguration;
import org.springframework.data.rest.config.ResourceMapping;
import org.springframework.data.rest.repository.BaseUriAwareResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jon Brisbin
 */
public class CustomerResourceProcessor implements ResourceProcessor<Resource<Customer>> {

	@Autowired
	private ShoppingCartRepository      shoppingCarts;
	@Autowired
	private Repositories                repositories;
	@Autowired
	private RepositoryRestConfiguration config;
	private ResourceMapping             repoMapping;

	public void init() {
		repoMapping = getResourceMapping(config,
		                                 repositories.getRepositoryInformationFor(ShoppingCart.class));
	}

	@Override public Resource<Customer> process(Resource<Customer> res) {
		URI baseUri = (res instanceof BaseUriAwareResource
		               ? ((BaseUriAwareResource)res).getBaseUri()
		               : config.getBaseUri());

		List<ShoppingCart> carts = shoppingCarts.findByCustomer(res.getContent().getId());
		for(ShoppingCart cart : carts) {
			String uri = UriComponentsBuilder.fromUri(baseUri)
			                                 .pathSegment(repoMapping.getPath(), cart.getId())
			                                 .build()
			                                 .toUriString();
			String rel = findRel(ShoppingCart.class);
			res.add(new Link(uri, repoMapping.getRel() + "." + rel));
		}

		return res;
	}

}
