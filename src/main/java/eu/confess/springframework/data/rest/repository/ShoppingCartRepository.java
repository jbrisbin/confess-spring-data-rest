package eu.confess.springframework.data.rest.repository;

import java.util.List;

import eu.confess.springframework.data.rest.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

/**
 * @author Jon Brisbin
 */
@RestResource(path = "carts", rel = "carts")
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

	@RestResource(exported = false)
	public List<ShoppingCart> findByCustomer(@Param("cid") Long customer);

}
