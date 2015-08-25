package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import forms.TutorListForm;

import services.TutorService;
import utilities.dpexceptions.DPForbiddenException;

@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController{

	@Autowired
	private TutorService tutorService;
	
	@RequestMapping("/listTutors")
	public ModelAndView list(){
		ModelAndView result;
		try{
			Collection<TutorListForm> tutors = tutorService.getListTutors();
			result = new ModelAndView("tutor/listTutor");
			result.addObject("tutors", tutors);
		}catch(DPForbiddenException exc){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		return result;
	}
}
