package domain;

import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LectureNote extends DomainEntity {
	// Atributes ------------------------------
	private String name;
	private String description;
	private byte[] file;

	public LectureNote() {
		verifications = new HashSet<Verification>();
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

	@Lob
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	// Relationships ---------------------------
	private Subject subject;
	private Collection<Verification> verifications;
	private Student student;

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
	@OneToMany(mappedBy = "lectureNote")
	public Collection<Verification> getVerifications() {
		return verifications;
	}

	public void setVerifications(Collection<Verification> verifications) {
		this.verifications = verifications;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
