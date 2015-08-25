package forms;

import domain.Lecturer;

public class LecturerDashboardForm {

	private Lecturer lecturer;
	private Long subjects;

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Long getSubjects() {
		return subjects;
	}

	public void setSubjects(Long subjects) {
		this.subjects = subjects;
	}

}
