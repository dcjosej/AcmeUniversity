package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Activity extends DomainEntity {
	// Atributes --------------------

	public Activity() {
		slots = new HashSet<Slot>();
		comments = new HashSet<Comment>();
		students = new HashSet<Student>();
	}

	// Relationships ----------------
	private Subject subject;
	private Collection<Student> students;
	private Collection<Slot> slots;
	private Collection<Comment> comments;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "activity")
	public Collection<Slot> getSlots() {
		return slots;
	}

	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "activity")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
