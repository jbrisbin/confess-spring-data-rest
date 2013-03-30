package eu.confess.springframework.data.rest.domain;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

/**
 * @author Jon Brisbin
 */
@Entity
public class Payment extends AbstractEntity {

	@OneToOne
	private Order  order;
	private String cardNumber;
	private Date   created;

	public Payment() {
	}

	public Order getOrder() {
		return order;
	}

	public Payment setOrder(Order order) {
		this.order = order;
		return this;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public Payment setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	@PrePersist
	private void prePersist() {
		this.created = Calendar.getInstance().getTime();
	}

}
