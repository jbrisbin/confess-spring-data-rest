package eu.confess.springframework.data.rest.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Jon Brisbin
 */
@Document
public class ShoppingCart implements Iterable<ShoppingCart.LineItem> {

	@Id
	private String         id;
	private Long           customer;
	private List<LineItem> lineItems;

	public String getId() {
		return id;
	}

	public Long getCustomer() {
		return customer;
	}

	public ShoppingCart setCustomer(Long customer) {
		this.customer = customer;
		return this;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public ShoppingCart setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
		return this;
	}

	public ShoppingCart add(LineItem lineItem) {
		if(null == lineItems) {
			lineItems = new ArrayList<>();
		}
		lineItems.add(lineItem);
		return this;
	}

	@Override public Iterator<LineItem> iterator() {
		return (null != lineItems
		        ? Collections.unmodifiableList(lineItems).iterator()
		        : Collections.<LineItem>emptyIterator());
	}

	public static class LineItem {
		private String description;
		private Double quantity;
		private Double unitPrice;

		public String getDescription() {
			return description;
		}

		public LineItem setDescription(String description) {
			this.description = description;
			return this;
		}

		public Double getQuantity() {
			return quantity;
		}

		public LineItem setQuantity(Double quantity) {
			this.quantity = quantity;
			return this;
		}

		public Double getUnitPrice() {
			return unitPrice;
		}

		public LineItem setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
			return this;
		}
	}

}
