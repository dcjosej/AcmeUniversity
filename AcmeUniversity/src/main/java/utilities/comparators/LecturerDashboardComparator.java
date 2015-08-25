package utilities.comparators;

import java.util.Comparator;

import forms.LecturerDashboardForm;

public class LecturerDashboardComparator implements Comparator<LecturerDashboardForm>{

	@Override
	public int compare(LecturerDashboardForm o1, LecturerDashboardForm o2) {
		int result = o2.getSubjects().compareTo(o1.getSubjects());
		return result;
	}
	
	
}
