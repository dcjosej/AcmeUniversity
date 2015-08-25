/* AdministratorController.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.LectureService;
import services.SlotService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Lecture;
import domain.Slot;

@Controller
@RequestMapping("/lecture")
public class LectureController extends AbstractController {

	@Autowired
	private LectureService lectureService;
	@Autowired
	private SlotService slotService;
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public LectureController() {
		super();
	}

	@RequestMapping("/details")
	public ModelAndView details(@RequestParam("idLecture") int idLecture) {
		ModelAndView res = null;

		String requestURI = "lecture/details.do?idLecture=" + idLecture;
		try {
			Lecture lecture = lectureService.findOne(idLecture);
			Collection<Slot> slots = slotService.findByIdActivity(idLecture);
			boolean canComment = commentService.canCommentLecture(idLecture);
			res = new ModelAndView("lecture/details");
			res.addObject("lecture", lecture);
			res.addObject("slots", slots);
			res.addObject("requestURI", requestURI);
			res.addObject("canComment", canComment);
		} catch (DPNotFoundException e) {
			res = new ModelAndView("404");
		} catch (Throwable oops) {
			res = new ModelAndView("error");
		}
		return res;
	}

	@RequestMapping("/register")
	public ModelAndView register(@RequestParam("idLecture") int idLecture) {
		ModelAndView res = null;
		
		try{
			lectureService.register(idLecture);
			
			res = new ModelAndView("redirect:../lecture/details.do?idLecture=" + idLecture);
		}catch(DPForbiddenException e){
			res = new ModelAndView("403");
		}catch(Throwable oops){
			res = new ModelAndView("error");
		}
		return res;
	}
}