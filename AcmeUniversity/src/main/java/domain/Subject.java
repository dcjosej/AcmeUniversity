package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Subject extends DomainEntity {
	// Atributes ---------------------
	private String name;
	private String description;

	public Subject(){
		this.activities = new HashSet<Activity>();
		this.lectureNotes = new HashSet<LectureNote>();
		this.applications = new HashSet<Application>();
	}
	
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Relationships ------------------
	private Collection<LectureNote> lectureNotes;
	private Collection<Activity> activities;
	private Collection<Application> applications;
	private Degree degree;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "subject")
	public Collection<LectureNote> getLectureNotes() {
		return lectureNotes;
	}

	public void setLectureNotes(Collection<LectureNote> lectureNotes) {
		this.lectureNotes = lectureNotes;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "subject")
	public Collection<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "subject")
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

}
