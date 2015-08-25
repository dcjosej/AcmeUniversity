package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AssessmentForm {

	private int id;
	private int version;
	private int teacher;
	private int rating;
	private String text;

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

	public int getTeacher() {
		return teacher;
	}

	public void setTeacher(int teacher) {
		this.teacher = teacher;
	}

	@Min(0)
	@Max(10)
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@NotNull
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
