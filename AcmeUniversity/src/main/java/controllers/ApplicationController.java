package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import utilities.dpexceptions.DPNoFileException;
import domain.Application;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {
	@Autowired
	private ApplicationService applicationService;
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("application/list");
		String requestURI = "application/list.do";
		Collection<Application> applications = applicationService.findPending();

		res.addObject("applications", applications);
		res.addObject("requestURI", requestURI);
		return res;
	}
	
	@RequestMapping("/accept")
	public ModelAndView accept(@RequestParam("idApplication") int id){
		ModelAndView result;
		
		try{
			applicationService.accept(id);
			result = new ModelAndView("redirect:list.do");
		}catch(DPNoFileException exc){
			result = new ModelAndView("404"); 
		}catch(Throwable error){
			result = new ModelAndView("error");
		}
		return result;
	}
	
	@RequestMapping("/decline")
	public ModelAndView decline(@RequestParam("idApplication") int id){
		ModelAndView result;
		
		try{
			applicationService.decline(id);
			result = new ModelAndView("redirect:list.do");
		}catch(DPNoFileException exc){
			result = new ModelAndView("404"); 
		}catch(Throwable error){
			result = new ModelAndView("error");
		}
		return result;
	}
}
