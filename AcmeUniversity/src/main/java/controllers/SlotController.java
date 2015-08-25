package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.SlotService;
import utilities.dpexceptions.DPForbiddenException;
import domain.Slot;
import forms.SlotTimetableForm;

@Controller
@RequestMapping("/slot")
public class SlotController extends AbstractController {
	@Autowired
	private SlotService slotService;
	
	@RequestMapping("timetable")
	public ModelAndView timetable(){
		ModelAndView result;
		try{
			Collection<SlotTimetableForm> monday = slotService.getStudentTimetableDay("Monday");
			Collection<SlotTimetableForm> tuesday = slotService.getStudentTimetableDay("Tuesday");
			Collection<SlotTimetableForm> wednesday = slotService.getStudentTimetableDay("Wednesday");
			Collection<SlotTimetableForm> thursday = slotService.getStudentTimetableDay("Thursday");
			Collection<SlotTimetableForm> friday = slotService.getStudentTimetableDay("Friday");
			Collection<SlotTimetableForm> saturday = slotService.getStudentTimetableDay("Saturday");
			Collection<SlotTimetableForm> sunday = slotService.getStudentTimetableDay("Sunday");
			
			String requestURI = "/slot/timetable.do";
			
			result = new ModelAndView("slot/timetable");
			
			result.addObject("monday", monday);
			result.addObject("tuesday", tuesday);
			result.addObject("wednesday", wednesday);
			result.addObject("thursday", thursday);
			result.addObject("friday", friday);
			result.addObject("saturday", saturday);
			result.addObject("sunday", sunday);
			result.addObject("requestURI", requestURI);
		}catch(DPForbiddenException exc){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		
		return result;
	}
	
}
