/* WelcomeController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LecturerService;
import services.VerificationService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Lecturer;
import domain.Verification;

@Controller
@RequestMapping("/verification")
public class VerificationController extends AbstractController {

	@Autowired
	private VerificationService verificationService;
	@Autowired
	private LecturerService lecturerService;

	// Constructors -----------------------------------------------------------
	public VerificationController() {
		super();
	}

	// Index ------------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam("idLectureNote") int idLectureNote) {
		ModelAndView res = null;
		try {

			Verification verification = verificationService
					.create(idLectureNote);
			String previousURI = "lectureNote/details.do?idLectureNote="
					+ verification.getLectureNote().getId();

			res = new ModelAndView("verification/create");
			res.addObject("verification", verification);
			res.addObject("previousURI", previousURI);

		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}

		return res;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("idVerification") int idVerification) {
		ModelAndView res = null;
		try {
			Verification verification = verificationService
					.findOne(idVerification);
			res = createEditModelAndView(verification);
		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Verification verification,
			BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors()) {
			res = createEditModelAndView(verification);
		} else {
			try {
				verificationService.save(verification);
				res = new ModelAndView(
						"redirect:../lectureNote/details.do?idLectureNote="
								+ verification.getLectureNote().getId());
			} catch (DPForbiddenException e) {
				res = new ModelAndView("403");
			} catch (DPNotFoundException e) {
				res = new ModelAndView("404");
			} catch (Throwable oops) {
				res = createEditModelAndView(verification, "commit.error");
			}
		}
		return res;
	}

	@RequestMapping("/details")
	public ModelAndView details(
			@RequestParam("idVerification") int idVerification) {
		ModelAndView res = null;

		try {
			Verification verification = verificationService
					.findOne(idVerification);
			Lecturer principal = lecturerService.findByPrincipal();
			boolean isCreator = verification.getLecturer().equals(principal);

			res = new ModelAndView("verification/details");
			res.addObject("verification", verification);
			res.addObject("isCreator", isCreator);
		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}

		return res;
	}

	private ModelAndView createEditModelAndView(Verification verification) {
		return createEditModelAndView(verification, null);
	}

	private ModelAndView createEditModelAndView(Verification verification,
			String message) {
		ModelAndView res = null;

		String previousURI = "lectureNote/details.do?idLectureNote="
				+ verification.getLectureNote().getId();

		res = new ModelAndView("verification/edit");
		res.addObject("verification", verification);
		res.addObject("previousURI", previousURI);
		return res;
	}
}