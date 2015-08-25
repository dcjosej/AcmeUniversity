package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PrivateMessageRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Actor;
import domain.PrivateMessage;
import forms.PrivateMessageForm;

@Service
@Transactional
public class PrivateMessageService {

	@Autowired
	private PrivateMessageRepository privateMessageRepository;
	@Autowired
	private ActorService actorService;
	
	
	public PrivateMessage findOne(int id) throws DPNotFoundException{
		PrivateMessage message = privateMessageRepository.findOne(id);
		if(message == null){
			throw new DPNotFoundException();
		}
		
		return message;
	}
	
	public PrivateMessageForm createMessageForm(int idActor){
		Actor actor = actorService.findOne(idActor);
		
		PrivateMessageForm result = new PrivateMessageForm();
		
		result.setRecipient(actor);
		
		return result;
	}

	public PrivateMessage reconstruct(PrivateMessageForm privateMessageForm) {

		Actor sender = actorService.findByPrincipal();
		
		if(sender == null){
			throw new DPForbiddenException();
		}
		
		Actor recipient = privateMessageForm.getRecipient();
		if(recipient == null){
			throw new DPForbiddenException();
		}
		
		PrivateMessage message = new PrivateMessage();
		
		message.setText(privateMessageForm.getText());
		message.setSubject(privateMessageForm.getSubject());
		message.setSender(sender);
		message.setRecipient(recipient);
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		date.setTime(calendar.getTimeInMillis()-1);
		message.setMoment(date);
		
		return message;
	}

	public PrivateMessage save(PrivateMessage message) {
		Actor recipient = message.getRecipient();
		Actor sender = message.getSender();
		
		recipient.getMessagesReceived().add(message);
		sender.getMessagesSended().add(message);
		
		actorService.save(recipient);
		actorService.save(sender);
		PrivateMessage result = privateMessageRepository.save(message);
		return result;
	}

	public Collection<PrivateMessage> getReceived() throws DPForbiddenException{
		Actor actor = actorService.findByPrincipal();
		
		if(actor == null){
			throw new DPForbiddenException();
		}
		
		Collection<PrivateMessage> messages = privateMessageRepository.getReceived(actor);
		return messages;
	}

	public PrivateMessage getPrivateMessageOfActor(int id) {
		PrivateMessage message = findOne(id);
		Actor actor = actorService.findByPrincipal();
		
		if(!message.getRecipient().equals(actor) && !message.getSender().equals(actor)){
			throw new DPForbiddenException();
		}
		return message;
	}
}
