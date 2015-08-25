package controllers;

import java.io.OutputStream;
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

import services.LectureNoteService;
import services.LecturerService;
import services.VerificationService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNoFileException;
import utilities.dpexceptions.DPNotFoundException;
import domain.LectureNote;
import domain.Lecturer;
import domain.Verification;
import forms.LectureNoteForm;

@Controller
@RequestMapping("/lectureNote")
public class LectureNoteController extends AbstractController {
	@Autowired
	private LectureNoteService lectureNoteService;
	@Autowired
	private VerificationService verificationService;
	@Autowired
	private LecturerService lecturerService;

	@RequestMapping("/uploadFile")
	public ModelAndView uploadFile(@RequestParam("idSubject") int idSubject) {
		ModelAndView res = null;

		try {
			LectureNoteForm lectureNoteForm = lectureNoteService
					.createForm(idSubject);
			res = createEditModelAndView(lectureNoteForm);
		} catch (Throwable oops) {
			res = new ModelAndView("403");
		}
		return res;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, params = "save")
	public ModelAndView saveFile(@Valid LectureNoteForm lectureNoteForm,
			BindingResult binding) {
		ModelAndView res = null;

		if (binding.hasErrors()) {
			res = createEditModelAndView(lectureNoteForm);
		} else {
			try {
				LectureNote lectureNote = lectureNoteService
						.recontruct(lectureNoteForm);
				lectureNoteService.saveLectureNote(lectureNote);
				res = new ModelAndView(
						"redirect:../subject/details.do?idSubject="
								+ lectureNoteForm.getSubject().getId());
			} catch (DPNoFileException e) {
				res = createEditModelAndView(lectureNoteForm,
						"lectureNote.error.noFile");
			} catch (DPForbiddenException e) {
				res = new ModelAndView("403");
			} catch (Throwable oops) {
				res = new ModelAndView("error");
			}
		}
		return res;
	}

	@RequestMapping("/download")
	public void download(@RequestParam("idLectureNote") int idLectureNote,
			HttpServletResponse response) {
		LectureNote lectureNote = lectureNoteService.findOne(idLectureNote);
		try {
			response.setHeader("Content-Disposition", "attachment; filename="
					+ lectureNote.getName());
			OutputStream out = response.getOutputStream();
			out.write(lectureNote.getFile());
			out.flush();
		} catch (Throwable oops) {

		}
	}

	@RequestMapping("/details")
	public ModelAndView details(@RequestParam("idLectureNote") int idLectureNote) {
		ModelAndView res = null;
		try {
			LectureNote lectureNote = lectureNoteService.findOne(idLectureNote);
			Collection<Verification> verifications = verificationService
					.findByIdLectureNote(idLectureNote);
			Lecturer principal = lecturerService.findByPrincipal();
			if (principal == null) {
				throw new DPForbiddenException();
			}
			Collection<Verification> verificationsPrincipal = verificationService
					.findByPrincipalAndLectureNote(idLectureNote);
			boolean canCreate = (verificationsPrincipal.size() == 0);

			res = new ModelAndView("lectureNote/details");
			res.addObject("lectureNote", lectureNote);
			res.addObject("verifications", verifications);
			res.addObject("canCreate", canCreate);
		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}

		return res;
	}

	private ModelAndView createEditModelAndView(LectureNoteForm lectureNoteForm) {
		return createEditModelAndView(lectureNoteForm, null);
	}

	private ModelAndView createEditModelAndView(
			LectureNoteForm lectureNoteForm, String message) {
		ModelAndView res = new ModelAndView("lectureNote/uploadFile");
		String previousURI = "subject/details.do?idSubject="
				+ lectureNoteForm.getSubject().getId();
		res.addObject("lectureNoteForm", lectureNoteForm);
		res.addObject("previousURI", previousURI);
		res.addObject("message", message);
		return res;
	}
}
