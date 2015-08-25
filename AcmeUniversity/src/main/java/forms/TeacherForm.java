package forms;

import java.sql.Blob;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import domain.Curriculum;

public class TeacherForm {

	private int id;
	private int version;
	private String email;
	private String phoneNumber;
	private String description;
	private Curriculum curriculum;
	private MultipartFile newPhoto;
	private Blob oldPhoto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^(\\d{9})?$")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public MultipartFile getNewPhoto() {
		return newPhoto;
	}

	public void setNewPhoto(MultipartFile newPhoto) {
		this.newPhoto = newPhoto;
	}

	public Blob getOldPhoto() {
		return oldPhoto;
	}

	public void setOldPhoto(Blob oldPhoto) {
		this.oldPhoto = oldPhoto;
	}
}