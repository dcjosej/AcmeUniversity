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

import services.DegreeService;
import services.StudentService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Degree;
import domain.Student;
import forms.DegreeForm;

@Controller
@RequestMapping("/degree")
public class DegreeController extends AbstractController {
	@Autowired
	private DegreeService degreeService;
	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("degree/list");
		String requestURI = "degree/list.do";
		Collection<Degree> degrees = degreeService.findAll();
		Student student = studentService.findByPrincipal();

		res.addObject("student", student);
		res.addObject("degrees", degrees);
		res.addObject("requestURI", requestURI);
		return res;
	}

	@RequestMapping("/listMyDegrees")
	public ModelAndView listMyDegrees() {
		ModelAndView res = new ModelAndView("degree/list");
		String requestURI = "degree/list.do";
		Collection<Degree> degrees = degreeService.findDegreesByPrincipal();
		Student student = studentService.findByPrincipal();

		res.addObject("student", student);
		res.addObject("degrees", degrees);
		res.addObject("requestURI", requestURI);
		return res;
	}

	@RequestMapping("/enrols")
	public ModelAndView enrols(@RequestParam("idDegree") int idDegree) {
		ModelAndView res = null;
		try {
			degreeService.enrols(idDegree);
			res = new ModelAndView("redirect:../degree/listMyDegrees.do");
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (DPForbiddenException e) {
			res = new ModelAndView("403");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}
		return res;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(){
		ModelAndView result;
		
		DegreeForm degreeForm = new DegreeForm();
		String previousURI = "";
		
		result = new ModelAndView("degree/edit");
		
		result.addObject(degreeForm);
		result.addObject(previousURI);
		
		return result;
	}
	
	@RequestMapping(value="edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid DegreeForm degreeForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			String previousURI = "";
			
			result = new ModelAndView("degree/edit");
			
			result.addObject(degreeForm);
			result.addObject(previousURI);
		}else{
			try{
				Degree degree = degreeService.reconstruct(degreeForm);
				degreeService.save(degree);
				
				result=new ModelAndView("redirect:../");
			}catch(Throwable error){
				String previousURI = "";
				
				result = new ModelAndView("degree/edit");
				
				result.addObject(degreeForm);
				result.addObject(previousURI);
				result.addObject("message", "commit.error");
			}
		}
		
		return result;
	}
}
