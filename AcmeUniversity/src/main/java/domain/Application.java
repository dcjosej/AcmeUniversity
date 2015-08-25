package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "accepted") })
public class Application extends DomainEntity {
	// Atributes -------------------------------
	private boolean accepted;

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	// Relationships ----------------------------
	private Subject subject;
	private Lecturer lecturer;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

}
