package utilities.comparators;

import java.util.Comparator;

import forms.StudentDashboardForm;

public class StudentDashboardComparator implements Comparator<StudentDashboardForm>{


	@Override
	public int compare(StudentDashboardForm o1, StudentDashboardForm o2) {
		int result = o2.getNumLectureNotes().compareTo(o1.getNumLectureNotes());
		return result;
	}
}
