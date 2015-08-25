package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Lecturer extends Teacher {
	// Atributes ------------------------

	public Lecturer() {
		super();
		verifications = new HashSet<Verification>();
		applications = new HashSet<Application>();
	}

	// Relationships ---------------------
	private Collection<Verification> verifications;
	private Collection<Application> applications;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "lecturer")
	public Collection<Verification> getVerifications() {
		return verifications;
	}

	public void setVerifications(Collection<Verification> verifications) {
		this.verifications = verifications;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "lecturer")
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

}
