package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Lecture extends Activity {
	// Atributes -------------------------
	private String group;

	@NotBlank
	@Column(name="_Group")
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Lecture() {
		
	}

	// Relationships ----------------------

}
