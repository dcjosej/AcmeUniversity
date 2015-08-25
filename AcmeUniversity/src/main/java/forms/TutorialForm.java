package forms;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Subject;

public class TutorialForm {

	private int id;
	private int version;
	private int minStudents;
	private int maxStudents;
	private double pricePerHour;
	private int numSlots;
	private List<SlotForm> slots;
	private Subject subject;
	
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

	@Min(1)
	public int getNumSlots() {
		return numSlots;
	}

	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}

	@NotNull
	@Valid
	public List<SlotForm> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotForm> slots) {
		this.slots = slots;
	}

	@NotNull
	@Valid
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
	
}
