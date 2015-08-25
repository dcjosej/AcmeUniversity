package forms;

import domain.Tutor;

public class TutorRatedForm {

	private Tutor tutor;
	private long rating;

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

}
