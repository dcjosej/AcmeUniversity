package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;

public class PrivateMessageForm {

	private Actor recipient;
	private String subject;
	private String text;
	
	@Valid
	@NotNull
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	
	@NotNull
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotNull
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
