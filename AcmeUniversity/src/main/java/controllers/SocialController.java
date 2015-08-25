package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signup")
public class SocialController extends AbstractController{
	@RequestMapping("/")
	public ModelAndView signup(){
		ModelAndView res = new ModelAndView("redirect:../login/registerStudent");
		return res;
	}
}
