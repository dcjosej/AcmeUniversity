package domain;

import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Teacher extends Actor {
	// Atributes -------------------------
	private String description;
	private Curriculum curriculum;
	private Blob photo;
	
	public Teacher(){
		super();
		setAssessment(new HashSet<Assessment>());
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Valid
	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	@Lob
	@Column(columnDefinition="MEDIUMBLOB")
	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
	//Relationships ----------------------------
	private Collection<Assessment> assessment;

	@Valid
	@NotNull
	@OneToMany(mappedBy="teacher")
	public Collection<Assessment> getAssessment() {
		return assessment;
	}

	public void setAssessment(Collection<Assessment> assessment) {
		this.assessment = assessment;
	}

}
