package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "correct") })
public class Verification extends DomainEntity {
	// Atributes ----------------------
	private boolean correct;
	private String description;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Relationships -------------------------
	private LectureNote lectureNote;
	private Lecturer lecturer;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public LectureNote getLectureNote() {
		return lectureNote;
	}

	public void setLectureNote(LectureNote lectureNote) {
		this.lectureNote = lectureNote;
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
