/* AdministratorController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.LecturerService;
import services.StudentService;
import services.SubjectService;
import services.TutorService;
import forms.LecturerDashboardForm;
import forms.StudentDashboardForm;
import forms.SubjectDashboardForm;
import forms.TutorRatedForm;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends AbstractController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private SubjectService subjectService;
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
		
	@RequestMapping("/dashboard")
	public ModelAndView dashboard(){
		ModelAndView result;
		
		Collection<StudentDashboardForm> students = studentService.findStudentsWithMoreValidatedLN();
		Collection<TutorRatedForm> tutors = tutorService.findBestRated();
		Collection<LecturerDashboardForm> lecturers = lecturerService.findLecturersWithMoreSubjects();
		Collection<SubjectDashboardForm> subjects = subjectService.findSubjectsWithMoreComments();
		
		String requestURI = "administrator/dashboard.do";
		
		result = new ModelAndView("administrator/dashboard");
		
		result.addObject("students", students);
		result.addObject("tutors", tutors);
		result.addObject("lecturers", lecturers);
		result.addObject("subjects", subjects);
		result.addObject("requestURI", requestURI);
		
		return result;
	}
	
}