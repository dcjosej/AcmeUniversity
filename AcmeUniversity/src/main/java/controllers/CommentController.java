package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.CommentService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNoFileException;
import domain.Activity;
import domain.Comment;
import domain.Lecture;
import domain.Tutorial;
import forms.CommentForm;


@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController{
	@Autowired
	private CommentService commentService;
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam("id") int id){
		ModelAndView result;
		
		try{
			Activity activity = activityService.findOne(id);
			CommentForm commentForm = commentService.createForm(id);
			
			String previousURI = "";
			if(activity instanceof Lecture){
				previousURI = "lecture/details.do?idLecture=" + id;
			}else if(activity instanceof Tutorial){
				previousURI = "tutorial/detail.do?id=" + id;
			}
			commentForm.setPreviousURI(previousURI);
			
			result = new ModelAndView("comment/edit");
			
			result.addObject("commentForm", commentForm);
			result.addObject("previousURI", previousURI);
		}catch(DPNoFileException exc){
			result = new ModelAndView("404");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CommentForm commentForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = new ModelAndView("comment/edit");
			result.addObject(commentForm);
		}else{
			try{
				Comment comment = commentService.reconstruct(commentForm);
				commentService.save(comment);
				
				result = new ModelAndView("redirect:../" + commentForm.getPreviousURI());
			}catch(DPForbiddenException exc){
				result = new ModelAndView("403");
			}catch(DPNoFileException exc){
				result = new ModelAndView("404");
			}catch(Throwable exc){
				result  = new ModelAndView("comment/edit");
				result.addObject(commentForm);
				result.addObject("message", "commit.error");
			}
		}
		
		return result;
	}
}
