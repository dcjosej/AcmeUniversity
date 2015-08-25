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

@Entity
@Access(AccessType.PROPERTY)
public class Student extends Actor {
	// Atributes ---------------------

	public Student() {
		super();
		activity = new HashSet<Activity>();
		assessment = new HashSet<Assessment>();
		degrees = new HashSet<Degree>();
		lectureNotes = new HashSet<LectureNote>();
	}

	// Relationships -----------------
	private Collection<Activity> activity;
	private Collection<Assessment> assessment;
	private Collection<Degree> degrees;
	private Collection<LectureNote> lectureNotes;

	@NotNull
	@Valid
	@ManyToMany(mappedBy="students")
	public Collection<Activity> getActivity() {
		return activity;
	}

	public void setActivity(Collection<Activity> activity) {
		this.activity = activity;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "student")
	public Collection<Assessment> getAssessment() {
		return assessment;
	}

	public void setAssessment(Collection<Assessment> assessment) {
		this.assessment = assessment;
	}

	@NotNull
	@Valid
	@ManyToMany(mappedBy="students")
	public Collection<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(Collection<Degree> degrees) {
		this.degrees = degrees;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "student")
	public Collection<LectureNote> getLectureNotes() {
		return lectureNotes;
	}

	public void setLectureNotes(Collection<LectureNote> lectureNotes) {
		this.lectureNotes = lectureNotes;
	}

}
