package eu.confess.springframework.data.rest.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONFESS_Customer")
public class Customer extends AbstractEntity {

	@NotNull(message = "not.blank")
	private String name;
	@NotNull(message = "not.blank")
	private Email  email;

	public Customer() {
	}

	public String getName() {
		return name;
	}

	public Customer setName(String name) {
		this.name = name;
		return this;
	}

	public Email getEmail() {
		return email;
	}

	public Customer setEmail(Email email) {
		this.email = email;
		return this;
	}

}
