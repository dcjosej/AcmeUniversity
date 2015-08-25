package forms;

import domain.Student;

public class StudentDashboardForm {

	private Student student;
	private Long numLectureNotes;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Long getNumLectureNotes() {
		return numLectureNotes;
	}

	public void setNumLectureNotes(Long numLectureNotes) {
		this.numLectureNotes = numLectureNotes;
	}

}
