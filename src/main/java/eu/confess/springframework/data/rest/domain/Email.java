package eu.confess.springframework.data.rest.domain;

import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonSerialize(using = ToStringSerializer.class)
@Embeddable
public class Email {

	private String email;

	protected Email() {
	}

	public Email(String email) {
		//Assert.notNull(email, "Email cannot be null.");
		//Assert.isTrue(email.contains("@"), "Does not appear to be a valid email.");
		this.email = email;
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return email.equals(obj);
	}

	@Override
	public String toString() {
		return email;
	}

}
