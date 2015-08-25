/* LoginController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.GoogleUserService;
import services.LecturerService;
import services.StudentService;
import services.TutorService;
import utilities.dpexceptions.DPInvalidRegistrationException;
import controllers.AbstractController;
import domain.Actor;
import domain.GoogleUser;
import domain.Lecturer;
import domain.Student;
import domain.Tutor;
import forms.RegistrationStudentForm;
import forms.RegistrationTeacherForm;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService service;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private GoogleUserService googleUserService;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	
	// Others -------------------------------------------------------------
	public void authenticate(String username) {
		UserDetails userDetails;
		userDetails = service.loadUserByUsername(username);
		Authentication auth =   new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@Valid @ModelAttribute Credentials credentials,
			BindingResult bindingResult,
			@RequestParam(required = false) boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		return result;
	}
	
	@RequestMapping("/googleLogin")
	public ModelAndView googleLogin(WebRequest request){
		ModelAndView result = null;
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		Google google  = (Google)connection.getApi();
		google = new GoogleTemplate(google.getAccessToken());
		GoogleUserInfo userInfo = google.userOperations().getUserInfo();
		Actor actor = actorService.getActorBySocialId(userInfo.getId());
		if(actor == null){
			GoogleUser googleUser = new GoogleUser();
			googleUser.setEmail(userInfo.getEmail());
			googleUser.setIdGoogle(userInfo.getId());
			googleUser.setName(userInfo.getFirstName());
			googleUser.setProfile(userInfo.getProfilePictureUrl());
			googleUser.setSurname(userInfo.getLastName());
			
			try{
				googleUser = googleUserService.save(googleUser);
				String idSocial = googleUser.getIdGoogle();
				result = new ModelAndView("register/registration");
				result.addObject("idSocial", "" + idSocial);
			}catch(Throwable oops){
				
			}
			
			
		}else{
			authenticate(actor.getUserAccount().getUsername());
			result = new ModelAndView("redirect:/");
		}
		
		return result;
	}

	@RequestMapping("/register/student")
	public ModelAndView studentRegister(@RequestParam(value = "idSocial", required = false) String id) {
		ModelAndView result;
		
		RegistrationStudentForm registrationStudentForm = new RegistrationStudentForm();
		

		GoogleUser googleUser = googleUserService.findOne(id);
		
		if(googleUser != null){
			registrationStudentForm.setName(googleUser.getName());
			registrationStudentForm.setSurname(googleUser.getSurname());
			registrationStudentForm.setEmail(googleUser.getEmail());
			registrationStudentForm.setIdSocial(googleUser.getIdGoogle());
			
			googleUserService.delete(googleUser);
		}
		
		String previousUri = "";

		result = new ModelAndView("register/student");
		result.addObject("registrationStudentForm", registrationStudentForm);
		result.addObject("previousUri", previousUri);

		return result;
	}

	@RequestMapping("/register/tutor")
	public ModelAndView tutorRegister(@RequestParam(value = "idSocial", required = false) String id) {
		ModelAndView result;

		RegistrationTeacherForm registrationTeacherForm = new RegistrationTeacherForm();
		String previousUri = "";
		
		GoogleUser googleUser = googleUserService.findOne(id);
		
		if(googleUser != null){
			registrationTeacherForm.setName(googleUser.getName());
			registrationTeacherForm.setSurname(googleUser.getSurname());
			registrationTeacherForm.setEmail(googleUser.getEmail());
			registrationTeacherForm.setIdSocial(googleUser.getIdGoogle());
			
			googleUserService.delete(googleUser);
		}
		
		result = new ModelAndView("register/tutor");
		result.addObject("registrationTeacherForm", registrationTeacherForm);
		result.addObject("previousUri", previousUri);

		return result;
	}

	@RequestMapping("/register/lecturer")
	public ModelAndView lecturerRegister(@RequestParam(value = "idSocial", required = false) String id) {
		ModelAndView result;

		RegistrationTeacherForm registrationTeacherForm = new RegistrationTeacherForm();
		

		GoogleUser googleUser = googleUserService.findOne(id);
		
		if(googleUser != null){
			registrationTeacherForm.setName(googleUser.getName());
			registrationTeacherForm.setSurname(googleUser.getSurname());
			registrationTeacherForm.setEmail(googleUser.getEmail());
			registrationTeacherForm.setIdSocial(googleUser.getIdGoogle());
			
			googleUserService.delete(googleUser);
		}
		
		
		String previousUri = "";

		result = new ModelAndView("register/lecturer");
		result.addObject("registrationTeacherForm", registrationTeacherForm);
		result.addObject("previousUri", previousUri);

		return result;
	}

	@RequestMapping(value = "/register/student", method = RequestMethod.POST, params = "save")
	public ModelAndView saveStudent(
			@Valid RegistrationStudentForm registrationStudentForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("register/student");
			result.addObject(registrationStudentForm);
			result.addObject("previousUri", "");
		} else {
			try {
				Student student = studentService
						.reconstruct(registrationStudentForm);
				studentService.save(student);
				result = new ModelAndView("redirect:../login.do");
			} catch (DPInvalidRegistrationException error) {
				result = new ModelAndView("register/student");
				result.addObject("registrationStudentForm",
						registrationStudentForm);
				result.addObject("previousUri", "");
				result.addObject("message", error.getMessage());
			} catch (DataIntegrityViolationException exc) {
				result = new ModelAndView("register/student");
				result.addObject("registrationStudentForm",
						registrationStudentForm);
				result.addObject("message", "duplicatedUser");
			} catch (Throwable oops) {
				result = new ModelAndView("register/student");
				result.addObject("registrationStudentForm",
						registrationStudentForm);
				result.addObject("message", "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/register/tutor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTutor(
			@Valid RegistrationTeacherForm registrationTeacherForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("register/tutor");
			result.addObject(tutorService);
			result.addObject("previousUri", "");
		} else {
			try {
				Tutor tutor = tutorService.reconstruct(registrationTeacherForm);
				tutorService.save(tutor);
				result = new ModelAndView("redirect:../login.do");
			} catch (DPInvalidRegistrationException error) {
				result = new ModelAndView("register/tutor");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("previousUri", "");
				result.addObject("message", error.getMessage());
			} catch (DataIntegrityViolationException exc) {
				result = new ModelAndView("register/tutor");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("message", "duplicatedUser");
			} catch (Throwable oops) {
				result = new ModelAndView("register/tutor");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("message", "commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/register/lecturer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLecturer(
			@Valid RegistrationTeacherForm registrationTeacherForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("register/lecturer");
			result.addObject(tutorService);
			result.addObject("previousUri", "");
		} else {
			try {
				Lecturer lecturer = lecturerService
						.reconstruct(registrationTeacherForm);
				lecturerService.save(lecturer);
				result = new ModelAndView("redirect:../login.do");
			} catch (DPInvalidRegistrationException error) {
				result = new ModelAndView("register/lecturer");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("previousUri", "");
				result.addObject("message", error.getMessage());
			} catch (DataIntegrityViolationException exc) {
				result = new ModelAndView("register/lecturer");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("message", "duplicatedUser");
			} catch (Throwable oops) {
				result = new ModelAndView("register/lecturer");
				result.addObject("registrationTeacherForm",
						registrationTeacherForm);
				result.addObject("message", "commit.error");
			}
		}

		return result;
	}

}