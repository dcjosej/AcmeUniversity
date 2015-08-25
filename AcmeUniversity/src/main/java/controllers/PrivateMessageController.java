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

import services.ActorService;
import services.PrivateMessageService;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Actor;
import domain.PrivateMessage;
import forms.PrivateMessageForm;

@Controller
@RequestMapping("/privateMessage")
public class PrivateMessageController {

	@Autowired
	private PrivateMessageService privateMessageService;
	@Autowired
	private ActorService actorService;
	
	@RequestMapping("/send")
	public ModelAndView send(){
		ModelAndView result;
		
		Collection<Actor> actors = actorService.findAll();
		String requestURI = "privateMessage/send.do";
		
		result = new ModelAndView("privateMessage/actorList");
		result.addObject("actors", actors);
		result.addObject("requestURI", requestURI);
		
		return result;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam("idActor") int id){
		ModelAndView result;
		
		try{
			PrivateMessageForm privateMessageForm = privateMessageService.createMessageForm(id);
			String previousURI = "privateMessage/send.do";
			result = new ModelAndView("privateMessage/edit");
			
			result.addObject("previousURI", previousURI);
			result.addObject(privateMessageForm);
		}catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		}catch (Throwable e) {
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	@RequestMapping("/reply")
	public ModelAndView reply(@RequestParam("idMessage") int id){
		ModelAndView result;
		
		try{
			PrivateMessage message = privateMessageService.findOne(id);
			PrivateMessageForm privateMessageForm = privateMessageService.createMessageForm(message.getSender().getId());
			privateMessageForm.setSubject("RE:" + message.getSubject());
			String previousURI = "privateMessage/send.do";
			result = new ModelAndView("privateMessage/edit");
			
			result.addObject("previousURI", previousURI);
			result.addObject(privateMessageForm);
		}catch (DPNotFoundException exc) {
			result = new ModelAndView("404");
		}catch (Throwable e) {
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PrivateMessageForm privateMessageForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			String previousURI = "privateMessage/send.do";
			result = new ModelAndView("privateMessage/edit");
			
			result.addObject("previousURI", previousURI);
			result.addObject(privateMessageForm);
		}else{
			try{
				PrivateMessage message = privateMessageService.reconstruct(privateMessageForm);
				privateMessageService.save(message);
				result = new ModelAndView("redirect:send.do");
			}catch(DPForbiddenException exc){
				result = new ModelAndView("403");
			}catch(DPNotFoundException exc){
				result = new ModelAndView("404");
			}catch(Throwable exc){
				String previousURI = "privateMessage/send.do";
				result = new ModelAndView("privateMessage/edit");
				
				result.addObject("previousURI", previousURI);
				result.addObject(privateMessageForm);
				
				result.addObject("message", "commit.error");
			}
			
		}
		
		return result;
	}
	
	@RequestMapping("/received")
	public ModelAndView received(){
		ModelAndView result;
		
		try{
			Collection<PrivateMessage> messages = privateMessageService.getReceived();
			String requestURI = "privateMessage/received.do";
			
			result = new ModelAndView("privateMessage/received");
			result.addObject("messages", messages);
			result.addObject("requestURI", requestURI);
		}catch(DPForbiddenException exc){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	@RequestMapping("/details")
	public ModelAndView detail(@RequestParam("idMessage") int id){
		ModelAndView result;
		
		try{
			PrivateMessage privateMessage = privateMessageService.getPrivateMessageOfActor(id);
			
			String previousURI = "privateMessage/received.do";
			
			result = new ModelAndView("privateMessage/detail");
			result.addObject("privateMessage", privateMessage);
			result.addObject("previousURI", previousURI);
		}catch(DPNotFoundException exc){
			result = new ModelAndView("404");
		}catch(DPForbiddenException exc){
			result = new ModelAndView("403");
		}catch(Throwable exc){
			result = new ModelAndView("error");
		}
		
		return result;
	}
	
	
}
