package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Degree extends DomainEntity {
	// Atributes -----------------
	private String name;
	
	public Degree(){
		this.students = new HashSet<Student>();
		this.subjects = new HashSet<Subject>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Relationships ------------------
	private Collection<Subject> subjects;
	private Collection<Student> students;

	@Valid
	@NotNull
	@OneToMany(mappedBy="degree")
	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {
		this.subjects = subjects;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}
	
	

}
