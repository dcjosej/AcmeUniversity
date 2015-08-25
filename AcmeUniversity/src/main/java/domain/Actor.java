package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {
	// Atributes -----------------------------
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private String idSocial;

	public Actor() {
		this.messagesReceived = new HashSet<PrivateMessage>();
		this.messagesSended = new HashSet<PrivateMessage>();
	}

	public String getIdSocial() {
		return idSocial;
	}

	public void setIdSocial(String idSocial) {
		this.idSocial = idSocial;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^(\\d{9})?$")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// Relationships --------------------------
	private UserAccount userAccount;
	private Collection<PrivateMessage> messagesSended;
	private Collection<PrivateMessage> messagesReceived;

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<PrivateMessage> getMessagesSended() {
		return messagesSended;
	}

	public void setMessagesSended(Collection<PrivateMessage> messagesSended) {
		this.messagesSended = messagesSended;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "recipient")
	public Collection<PrivateMessage> getMessagesReceived() {
		return messagesReceived;
	}

	public void setMessagesReceived(Collection<PrivateMessage> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

}
