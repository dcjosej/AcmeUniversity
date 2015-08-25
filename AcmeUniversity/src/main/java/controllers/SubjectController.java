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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LectureNoteService;
import services.LectureService;
import services.LecturerService;
import services.StudentService;
import services.SubjectService;
import services.TutorialService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Lecture;
import domain.LectureNote;
import domain.Lecturer;
import domain.Student;
import domain.Subject;
import domain.Tutorial;
import forms.SubjectForm;

@Controller
@RequestMapping("/subject")
public class SubjectController extends AbstractController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private LectureNoteService lectureNoteService;
	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private LecturerService lecturerService;

	public SubjectController() {
		super();
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam("idDegree") int id) {
		ModelAndView result;

		try {
			SubjectForm subjectForm = subjectService.createForm(id);
			String previousURI = "";

			result = new ModelAndView("subject/edit");

			result.addObject("subjectForm", subjectForm);
			result.addObject("previousURI", previousURI);
		} catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		} catch (Throwable exc) {
			result = new ModelAndView("error");
		}

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("idSubject") int id) {
		ModelAndView result;

		try {
			Subject subject = subjectService.findOne(id);
			SubjectForm subjectForm = subjectService.createForm(subject);
			String previousURI = "subject/details.do?idSubject=" + id;

			result = new ModelAndView("subject/edit");

			result.addObject("subjectForm", subjectForm);
			result.addObject("previousURI", previousURI);
		} catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		} catch (DPForbiddenException exc) {
			result = new ModelAndView("403");
		} catch (Throwable exc) {
			result = new ModelAndView("error");
		}

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SubjectForm subjectForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditView(subjectForm);
		} else {
			try {
				Subject subject = subjectService.reconstruct(subjectForm);
				Subject dbSubject = subjectService.save(subject);

				result = new ModelAndView("redirect:details.do?idSubject="
						+ dbSubject.getId());
			} catch (DPForbiddenException exc) {
				result = new ModelAndView("403");
			} catch (DPNotFoundException exc) {
				result = new ModelAndView("404");
			} catch (Throwable exc) {
				result = createEditView(subjectForm);
				result.addObject("message", "commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam("idDegree") int idDegree) {
		ModelAndView res = null;
		Collection<Subject> subjects = subjectService.findByIdDegree(idDegree);
		String requestURI = "subject/list.do?idDegree=" + idDegree;

		res = new ModelAndView("subject/list");
		res.addObject("subjects", subjects);
		res.addObject("requestURI", requestURI);
		return res;
	}

	@RequestMapping("/details")
	public ModelAndView details(@RequestParam("idSubject") int idSubject) {
		ModelAndView res = null;
		Subject subject = subjectService.findOne(idSubject);
		Collection<LectureNote> lectureNotes = lectureNoteService
				.findByIdSubject(idSubject);
		Collection<Tutorial> tutorials = tutorialService
				.findByIdSubject(idSubject);
		Collection<Lecture> lectures = lectureService
				.findByIdSubject(idSubject);
		Collection<Lecturer> lecturers = lecturerService
				.findLecturersOfSubject(subject);
		boolean canRegister = subjectService.canRegister(subject);
		boolean canEdit = subjectService.canEdit(subject);

		Student student = studentService.findByPrincipal();
		String requestURI = "subject/details.do";
		res = new ModelAndView("subject/details");
		res.addObject("subject", subject);
		res.addObject("lectureNotes", lectureNotes);
		res.addObject("tutorials", tutorials);
		res.addObject("lectures", lectures);
		res.addObject("requestURI", requestURI);
		res.addObject("student", student);
		res.addObject("lecturers", lecturers);
		res.addObject("canRegister", canRegister);
		res.addObject("canEdit", canEdit);

		return res;
	}

	@RequestMapping("/register")
	public ModelAndView register(@RequestParam("idSubject") int id) {
		ModelAndView result;
		try {
			subjectService.register(id);
			result = new ModelAndView("redirect:details.do?idSubject=" + id);
		} catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		} catch (DPForbiddenException exc) {
			result = new ModelAndView("403");
		} catch (Throwable exc) {
			result = new ModelAndView("error");
		}

		return result;
	}

	private ModelAndView createEditView(SubjectForm subjectForm) {
		ModelAndView result;

		String previousURI;
		if (subjectForm.getId() == 0) {
			previousURI = "";
		} else {
			previousURI = "subject/details.do?idSubect=" + subjectForm.getId();
		}

		result = new ModelAndView("subject/edit");
		result.addObject("subjectForm", subjectForm);
		result.addObject("previousURI", previousURI);

		return result;
	}
}