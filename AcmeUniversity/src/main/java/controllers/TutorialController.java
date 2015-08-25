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

import services.CommentService;
import services.SlotService;
import services.SubjectService;
import services.TutorialService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPInvalidRegistrationException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Subject;
import domain.Tutorial;
import forms.SlotForm;
import forms.TutorialForm;

@Controller
@RequestMapping("/tutorial")
public class TutorialController {

	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam("id") int id, @RequestParam(value="message", required= false) String message) {
		ModelAndView result;

		try {
			Tutorial tutorial = tutorialService.findOne(id);
			String requestURI = "tutorial/detail.do?id=" + id;
			boolean canRegister = tutorialService.canRegister(id);
			boolean editable = tutorialService.canEdit(id);
			boolean canComment = commentService.canCommentTutorial(id);

			result = new ModelAndView("tutorial/detail");

			result.addObject("tutorial", tutorial);
			result.addObject("requestURI", requestURI);
			result.addObject("canRegister", canRegister);
			result.addObject("canComment", canComment);
			result.addObject("editable", editable);
			if(message != null){
				result.addObject("message", message);
			}
		} catch (DPNotFoundException e) {
			result = new ModelAndView("404");
		} catch (Throwable e) {
			result = new ModelAndView("error");
		}

		return result;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(@RequestParam("id") int id){
		ModelAndView result;
		try{
			tutorialService.register(id);
			result = new ModelAndView("redirect:detail.do?id="+id);
			result.addObject("message", "tutorial.successfully");
		}catch(DPNotFoundException exc){
			result = new ModelAndView("404");
		}catch(DPForbiddenException e){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(){
		ModelAndView result = createEditModelAndView();
		
		return result;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("idTutorial") int id){
		ModelAndView result;
		
		try{
			TutorialForm tutorialForm = tutorialService.findToEdit(id);
			result = createEditModelAndView(tutorialForm);
		}catch(DPNotFoundException exc){
			result = new ModelAndView("404");
		}catch(DPForbiddenException exc){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		
		return result;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid TutorialForm tutorialForm, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(tutorialForm);
		}else{
			try{
				Tutorial tutorial = tutorialService.reconstruct(tutorialForm);
				Tutorial dbTutorial = tutorialService.save(tutorial);
				result = new ModelAndView("redirect:detail.do?id=" + dbTutorial.getId());
			}catch(DPForbiddenException exc){
				result = new ModelAndView("403");
			}catch(DPInvalidRegistrationException exc){
				result = createEditModelAndView(tutorialForm);
				
				result.addObject("message", exc.getMessage());
			}catch(Throwable exc){
				result = createEditModelAndView(tutorialForm);
				result.addObject("message", "commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST, params ="addSlot")
	public ModelAndView addSlot(TutorialForm tutorialForm){
		tutorialForm.setNumSlots(tutorialForm.getNumSlots() + 1);
		tutorialForm.getSlots().add(new SlotForm());
		ModelAndView result = createEditModelAndView(tutorialForm);
		
		return result;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST, params ="removeSlot")
	public ModelAndView removeSlot(TutorialForm tutorialForm){
		ModelAndView result;
		if(tutorialForm.getNumSlots() > 1){
			tutorialForm.setNumSlots(tutorialForm.getNumSlots() - 1);
			tutorialForm.getSlots().remove(tutorialForm.getSlots().size() -1);
			result = createEditModelAndView(tutorialForm);
		}else{
			result = createEditModelAndView(tutorialForm);
			result.addObject("message", "tutorial.minSlots");
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(){
		ModelAndView result;
		TutorialForm tutorialForm = tutorialService.createForm();
		String previousURI= "";
		Collection<Subject> subjects = subjectService.findAll();
		
		result= new ModelAndView("tutorial/edit");
		
		result.addObject("tutorialForm", tutorialForm);
		result.addObject("previousURI", previousURI);
		result.addObject("subjects", subjects);
		
		return result;
	}
	
	private ModelAndView createEditModelAndView(TutorialForm tutorialForm){
		ModelAndView result = createEditModelAndView();
		result.addObject("tutorialForm", tutorialForm);
		
		return result;
	}
}
