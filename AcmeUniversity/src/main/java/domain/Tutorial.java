package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends Activity {
	// Atributes ---------------------------
	private int minStudents;
	private int maxStudents;
	private double pricePerHour;
	private boolean available;

	@Min(1)
	public int getMinStudents() {
		return minStudents;
	}

	public void setMinStudents(int minStudents) {
		this.minStudents = minStudents;
	}

	@Min(1)
	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	@Digits(integer = 4, fraction = 2)
	@Min(0)
	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	// Relationships ---------------------------
	private Tutor tutor;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

}
