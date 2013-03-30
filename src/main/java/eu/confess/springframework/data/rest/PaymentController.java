package eu.confess.springframework.data.rest;

import eu.confess.springframework.data.rest.domain.Order;
import eu.confess.springframework.data.rest.domain.Payment;
import eu.confess.springframework.data.rest.repository.OrderRepository;
import eu.confess.springframework.data.rest.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jon Brisbin
 */
@Controller
@RequestMapping("/order/{orderId}")
public class PaymentController {

	@Autowired
	private OrderRepository   orders;
	@Autowired
	private PaymentRepository payments;
	@Autowired
	private EntityLinks       entityLinks;

	@RequestMapping(value = "/payment", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@SuppressWarnings({"unchecked"})
	public Resource<Payment> getPayment(@PathVariable Long orderId) throws ResourceNotFoundException {
		Order o = orders.findOne(orderId);
		Payment p = payments.findByOrder(o);
		if(null != p) {
			Link selfLink = entityLinks.linkFor(Payment.class).slash(p.getId()).withSelfRel();
			return new Resource<>(p, selfLink);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}

}
