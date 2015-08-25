/* CustomerController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AssessmentService;
import services.LecturerService;
import services.SubjectService;
import services.TeacherService;
import services.TutorService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Assessment;
import domain.Lecturer;
import domain.Subject;
import domain.Teacher;
import domain.Tutor;
import domain.Tutorial;
import forms.AssessmentForm;
import forms.LecturerRatedForm;
import forms.TeacherForm;
import forms.TutorRatedForm;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends AbstractController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private SubjectService subjectService;

	// Constructors -----------------------------------------------------------

	public TeacherController() {
		super();
	}

	@RequestMapping("/details")
	public ModelAndView details(@RequestParam("idTeacher") int idTeacher) {
		ModelAndView res = null;
		try {
			Teacher teacher = teacherService.findOne(idTeacher);
			Collection<Assessment> assessments = assessmentService
					.findByIdTeacher(idTeacher);

			Tutor tutorPrincipal = tutorService.findByPrincipal();
			Lecturer lecturerPrincipal = lecturerService.findByPrincipal();
			Collection<Tutorial> tutorials = (tutorPrincipal != null ? tutorPrincipal
					.getTutorials() : null);
			Collection<Subject> subjects = (lecturerPrincipal != null ? subjectService
					.getSubjectsByLecturer(lecturerService.findByPrincipal())
					: null);
			boolean canAssess = assessmentService.canAssess(idTeacher);
			boolean canEdit = teacherService.canEdit(teacher);

			res = new ModelAndView("teacher/details");
			res.addObject("teacher", teacher);
			res.addObject("assessments", assessments);
			res.addObject("canAssess", canAssess);
			res.addObject("tutorials", tutorials);
			res.addObject("subjects", subjects);
			res.addObject("canEdit", canEdit);

		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}

		return res;
	}

	@RequestMapping("/myProfile")
	public ModelAndView myProfile() {
		Teacher principal = teacherService.findByPrincipal();
		ModelAndView res = new ModelAndView(
				"redirect:../teacher/details.do?idTeacher=" + principal.getId());
		return res;
	}

	@RequestMapping("/photo")
	public void photo(@RequestParam("idTeacher") int idTeacher,
			HttpServletResponse response) {
		Teacher teacher = null;
		try {
			teacher = teacherService.findOne(idTeacher);
			Blob photo = teacher.getPhoto();
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			out.write(photo.getBytes(1, (int) photo.length()));
			out.flush();
		} catch (DPNotFoundException e) {
		} catch (IOException e) {
		} catch (SQLException e) {
		}
	}

	@RequestMapping("/assess")
	public ModelAndView assess(@RequestParam("idTeacher") int id) {
		ModelAndView result;

		try {
			AssessmentForm assessmentForm = assessmentService.createForm(id);
			String previousURI = "teacher/details.do?idTeacher=" + id;

			result = new ModelAndView("assessment/edit");
			result.addObject(assessmentForm);
			result.addObject(previousURI);
		} catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		} catch (DPForbiddenException exc) {
			result = new ModelAndView("403");
		} catch (Throwable exc) {
			result = new ModelAndView("error");
		}

		return result;
	}

	@RequestMapping(value = "/assess", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAssess(@Valid AssessmentForm assessmentForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			String previousURI = "teacher/details.do?idTeacher="
					+ assessmentForm.getTeacher();
			result = new ModelAndView("assessment/edit");
			result.addObject(previousURI);
			result.addObject(assessmentForm);
		} else {
			try {
				Assessment assessment = assessmentService
						.reconstruct(assessmentForm);
				assessmentService.save(assessment);

				result = new ModelAndView("redirect:details.do?idTeacher="
						+ assessmentForm.getTeacher());
			} catch (DPNotFoundException exc) {
				result = new ModelAndView("404");
			} catch (DPForbiddenException exc) {
				result = new ModelAndView("403");
			} catch (Throwable exc) {
				String previousURI = "teacher/details.do?idTeacher="
						+ assessmentForm.getTeacher();
				result = new ModelAndView("assessment/edit");
				result.addObject(previousURI);
				result.addObject(assessmentForm);
				result.addObject("message", "commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/bestRated")
	public ModelAndView bestRated() {
		ModelAndView result;

		Collection<LecturerRatedForm> lecturers = lecturerService
				.findBestRated();
		Collection<TutorRatedForm> tutors = tutorService.findBestRated();
		String requestURI = "teacher/bestRated.do";

		result = new ModelAndView("teacher/bestRated");
		result.addObject("lecturers", lecturers);
		result.addObject("tutors", tutors);
		result.addObject(requestURI);

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("idTeacher") int idTeacher) {
		ModelAndView res = null;

		try {
			TeacherForm teacherForm = teacherService.createForm(idTeacher);
			res = createEditModelAndView(teacherForm);
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TeacherForm teacherForm,
			BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors()) {
			res = createEditModelAndView(teacherForm);
		} else {
			try {
				Teacher teacher = teacherService.recontruct(teacherForm);
				teacherService.save(teacher);
				res = new ModelAndView("redirect:../teacher/myProfile.do");
			} catch (DPNotFoundException e) {
				res = new ModelAndView("404");
			} catch (DPForbiddenException e) {
				res = new ModelAndView("403");
			} catch (Throwable oops) {
				res = createEditModelAndView(teacherForm, "commit.error");
			}
		}
		return res;
	}

	private ModelAndView createEditModelAndView(TeacherForm teacherForm) {
		return createEditModelAndView(teacherForm, null);
	}

	private ModelAndView createEditModelAndView(TeacherForm teacherForm,
			String message) {
		ModelAndView res = null;
		String previousURI = "teacher/details.do?idTeacher="
				+ teacherForm.getId();

		res = new ModelAndView("teacher/edit");
		res.addObject("teacherForm", teacherForm);
		res.addObject("previousURI", previousURI);
		res.addObject("message", message);
		return res;
	}
}