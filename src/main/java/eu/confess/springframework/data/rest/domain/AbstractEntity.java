package eu.confess.springframework.data.rest.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class AbstractEntity {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	public Long getId() {
		return id;
	}

}
