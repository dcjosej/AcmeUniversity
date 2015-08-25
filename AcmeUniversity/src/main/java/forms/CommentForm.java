package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CommentForm {

	private int id;
	private int version;
	private int activity;
	private String text;
	private String previousURI;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	@NotNull
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@NotNull
	public String getPreviousURI() {
		return previousURI;
	}

	public void setPreviousURI(String previousURI) {
		this.previousURI = previousURI;
	}

	
}
