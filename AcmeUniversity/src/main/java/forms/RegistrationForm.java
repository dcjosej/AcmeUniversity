package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public abstract class RegistrationForm {

	// Atributes -----------------------------
		private String name;
		private String surname;
		private String email;
		private String phoneNumber;
		private String username;
		private String password;
		private String passwordVerification;
		private boolean terms;
		private String idSocial;
		

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
		@NotBlank
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

		@Size(min = 5, max = 32)
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Size(min = 5, max = 32)
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Size(min = 5, max = 32)
		public String getPasswordVerification() {
			return passwordVerification;
		}

		public void setPasswordVerification(String passwordVerification) {
			this.passwordVerification = passwordVerification;
		}

		public boolean isTerms() {
			return terms;
		}

		public void setTerms(boolean terms) {
			this.terms = terms;
		}
		

		public String getIdSocial() {
			return idSocial;
		}

		public void setIdSocial(String idSocial) {
			this.idSocial = idSocial;
		}
		
}
