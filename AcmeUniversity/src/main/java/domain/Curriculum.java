package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Curriculum {

	// Atributes --------------------------------
	private String personalData;
	private String academicExperience;
	private String other;

	public String getPersonalData() {
		return personalData;
	}

	public void setPersonalData(String personalData) {
		this.personalData = personalData;
	}

	public String getAcademicExperience() {
		return academicExperience;
	}

	public void setAcademicExperience(String academicExperience) {
		this.academicExperience = academicExperience;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
