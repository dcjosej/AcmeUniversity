package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tutor extends Teacher {
	// Atributes --------------------

	public Tutor() {
		super();
		tutorials = new HashSet<Tutorial>();
	}

	// Relationships ----------------
	private Collection<Tutorial> tutorials;

	@NotNull
	@Valid
	@OneToMany(mappedBy="tutor")
	public Collection<Tutorial> getTutorials() {
		return tutorials;
	}

	public void setTutorials(Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

}
