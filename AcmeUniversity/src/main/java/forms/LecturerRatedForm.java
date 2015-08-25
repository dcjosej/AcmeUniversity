package forms;

import domain.Lecturer;

public class LecturerRatedForm {

	private Lecturer lecturer;
	private long rating;

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public long getRating() {
		return this.rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

}
